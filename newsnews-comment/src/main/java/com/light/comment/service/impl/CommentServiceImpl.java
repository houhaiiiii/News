package com.light.comment.service.impl;

import com.light.comment.feign.UserFeign;
import com.light.comment.service.CommentService;
import com.light.model.comment.dtos.CommentDto;
import com.light.model.comment.dtos.CommentLikeDto;
import com.light.model.comment.dtos.CommentSaveDto;
import com.light.model.comment.pojos.ApComment;
import com.light.model.comment.pojos.ApCommentLike;
import com.light.model.comment.vo.ApCommentVo;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.pojos.ApUser;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评论业务实现类
 * @author houhai
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserFeign userFeign;

    @Override
    public ResponseResult findByArticleId(CommentDto dto) {
        //1.检查参数
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        if (dto.getSize() == null || dto.getSize() == 0) {
            dto.setSize(20);
        }

        //2.按照文章id过滤，设置分页和排序
        Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()).and("likes").lt(dto.getMinLikes()));
        query.limit(dto.getSize()).with(Sort.by(Sort.Direction.DESC,"likes"));
        List<ApComment> list = mongoTemplate.find(query, ApComment.class);

        //3.数据封装返回
        //3.1 用户未登录 加载数据
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.okResult(list);
        }

        //3.2 用户已登录，加载数据，需要判断当前用户点赞了哪些评论
        //3.2.1 获取评论ID集合 [1,2,3,4,5.6,7,8]
        List<String> idList = list.stream().map(x -> x.getId()).collect(Collectors.toList());

        //3.2.3 根据评论ID集合查询评论点赞信息  当前登录用户点赞了哪些评论
        Query query1 = Query.query(Criteria.where("commentId").in(idList).and("authorId").is(user.getId()));
        List<ApCommentLike> commentLikes = mongoTemplate.find(query1, ApCommentLike.class);

        //3.2.4 如果没有查询到点赞数据
        if (commentLikes == null) {
            return ResponseResult.okResult(list);
        }

        //3.2.4 存在点赞数据  [4,5]
        Set<String> userLikeCommentIds = commentLikes.stream().map(commentLike -> commentLike.getCommentId()).collect(Collectors.toSet());

        //遍历评论列表, 找到被当前登录用户点赞的评论, 添加标记
        List<ApCommentVo> result = list.stream().map(comment -> {
            ApCommentVo apCommentVo = new ApCommentVo();

            BeanUtils.copyProperties(comment, apCommentVo);

            if (userLikeCommentIds.contains(comment.getId())) {
                apCommentVo.setOperation((short) 0);
            }
            return apCommentVo;
        }).collect(Collectors.toList());


        return ResponseResult.okResult(result);
    }

    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        //1.检查参数
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        if (dto.getContent() != null && dto.getContent().length() > 140) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "评论内容不能超过140字");
        }

        //2.判断是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //todo 3.安全过滤,自行实现


        //4.保存评论
        ApUser apUser = userFeign.findUserById(user.getId().longValue());
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "当前登录信息有误");
        }
        ApComment apComment = new ApComment();
        apComment.setAuthorId(apUser.getId());
        apComment.setAuthorName(apUser.getName());
        apComment.setContent(dto.getContent());
        apComment.setEntryId(dto.getArticleId());
        apComment.setCreatedTime(new Date());
        apComment.setUpdatedTime(new Date());
        apComment.setImage(apUser.getImage());
        apComment.setLikes(0);
        apComment.setReply(0);
        apComment.setType((short) 0);
        apComment.setFlag((short) 0);

        mongoTemplate.insert(apComment);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult like(CommentLikeDto dto) {

        //1.检查参数
        if (dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //2.判断是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //3.点赞操作
        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        if (apComment != null && dto.getOperation() == 0) {
            //更新评论的点赞数量
            apComment.setLikes(apComment.getLikes() + 1);
            mongoTemplate.save(apComment);

            //保存 APP评论信息点赞
            ApCommentLike apCommentLike = new ApCommentLike();
            apCommentLike.setAuthorId(user.getId());
            apCommentLike.setCommentId(apComment.getId());
            //            apCommentLike.setOperation(dto.getOperation());
            mongoTemplate.save(apCommentLike);
        } else if (apComment != null && dto.getOperation() == 1) {
            //4.取消点赞
            //更新评论的点赞数量
            apComment.setLikes(apComment.getLikes() < 0 ? 0 : apComment.getLikes() - 1);
            mongoTemplate.save(apComment);
            //更新 APP评论信息点赞
            mongoTemplate.remove(Query.query(Criteria.where("authorId").is(user.getId()).and("commentId").is(apComment.getId())),ApCommentLike.class);

        }

        //5.数据返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("likes",apComment.getLikes());
        return ResponseResult.okResult(resultMap);
    }

}
