package com.light.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.article.feign.BehaviorFeign;
import com.light.article.feign.UserFeign;
import com.light.article.mapper.ApArticleConfigMapper;
import com.light.article.mapper.ApArticleContentMapper;
import com.light.article.mapper.ApCollectionMapper;
import com.light.article.mapper.AuthorMapper;
import com.light.article.service.ArticleInfoService;
import com.light.model.article.dtos.ArticleInfoDto;
import com.light.model.article.pojos.ApArticleConfig;
import com.light.model.article.pojos.ApArticleContent;
import com.light.model.article.pojos.ApAuthor;
import com.light.model.article.pojos.ApCollection;
import com.light.model.behavior.pojos.ApBehaviorEntry;
import com.light.model.behavior.pojos.ApLikesBehavior;
import com.light.model.behavior.pojos.ApUnlikesBehavior;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.pojos.ApUser;
import com.light.model.user.pojos.ApUserFollow;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 文章详情展示业务层实现类
 * @author houhai
 */
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private BehaviorFeign behaviorFeign;

    @Autowired
    private ApCollectionMapper apCollectionMapper;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AuthorMapper authorMapper;

    /**
     * 加载文章详情
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleInfo(ArticleInfoDto dto) {

        HashMap<String, Object> resultMap = new HashMap<>();

        //1.校验参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询文章的配置
        ApArticleConfig apArticleConfig = apArticleConfigMapper.selectOne(Wrappers.<ApArticleConfig>lambdaQuery().eq(ApArticleConfig::getArticleId,dto.getArticleId()));
        if (apArticleConfig == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //3.查询文章的内容
        if (!apArticleConfig.getIsDelete() && !apArticleConfig.getIsDown()) {
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, dto.getArticleId()));
            resultMap.put("content",apArticleContent);
        }
        resultMap.put("config",apArticleConfig);

        //4.结果返回
        return ResponseResult.okResult(resultMap);
    }

    /**
     * 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢等
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {

        //1.检查参数是否正确
        if (dto == null) {
            //为空就返回错误提示
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询行为实体
        //从线程中获取到ApUser对象
        ApUser user = AppThreadLocalUtils.getUser();
        //通过feign调用行为微服务的查找行为实体
        ApBehaviorEntry apBehaviorEntry = behaviorFeign.findByUserIdOrEntryId(user.getId(), dto.getEquipmentId());
        //判断是否有行为实体
        if (apBehaviorEntry == null) {
            //没有就返回错误信息
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //定义抽出的变量
        boolean isUnlike=false,isLike = false,isCollection = false,isFollow = false;

        //3.查询不喜欢行为
        //通过行为feign对象调用行为微服务查询不喜欢行为的实体
        ApUnlikesBehavior apUnlikesBehavior = behaviorFeign.findUnLikeByArticleIdAndEntryId(apBehaviorEntry.getId(), dto.getArticleId());
        //校验并且检查行为类型
        if (apUnlikesBehavior != null && apUnlikesBehavior.getType() == ApUnlikesBehavior.Type.UNLIKE.getCode()) {
            isUnlike = true;
        }

        //4.查询点赞行为
        ApLikesBehavior apLikesBehavior = behaviorFeign.findLikeByArticleIdAndEntryId(apBehaviorEntry.getId(), dto.getArticleId(), ApLikesBehavior.Type.ARTICLE.getCode());
        //校验返回的结果
        if (apLikesBehavior != null && apLikesBehavior.getOperation() == ApLikesBehavior.Operation.LIKE.getCode()) {
            isLike = true;
        }

        //5.查询收藏行为
        ApCollection apCollection = apCollectionMapper.selectOne(Wrappers.<ApCollection>lambdaQuery().eq(ApCollection::getEntryId, apBehaviorEntry.getId())
                .eq(ApCollection::getArticleId, dto.getArticleId()).eq(ApCollection::getType, ApCollection.Type.ARTICLE.getCode()));
        //校验返回的结果
        if(apCollection != null){
            isCollection = true;
        }

        //6.查询是否关注
        ApAuthor apAuthor = authorMapper.selectById(dto.getAuthorId());
        if(apAuthor != null){
            ApUserFollow apUserFollow = userFeign.findByUserIdAndFollowId(user.getId(), apAuthor.getUserId());
            if(apUserFollow != null){
                isFollow = true;
            }
        }

        //7.结果返回
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isfollow",isFollow);
        resultMap.put("islike",isLike);
        resultMap.put("isunlike",isUnlike);
        resultMap.put("iscollection",isCollection);
        return ResponseResult.okResult(resultMap);

    }

}
