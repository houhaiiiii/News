package com.light.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.model.article.pojos.ApAuthor;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.dtos.UserRelationDto;
import com.light.model.user.pojos.ApUser;
import com.light.model.user.pojos.ApUserFan;
import com.light.model.user.pojos.ApUserFollow;
import com.light.user.feign.ArticleFeign;
import com.light.user.mapper.ApUserFanMapper;
import com.light.user.mapper.ApUserFollowMapper;
import com.light.user.mapper.ApUserMapper;
import com.light.user.service.ApUserRelationService;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户关注实现类
 * @author houhai
 */
@Service
@Log4j2
@Transactional
public class ApUserRelationServiceImpl implements ApUserRelationService {

    @Autowired
    private ArticleFeign articleFeign;

    @Override
    public ResponseResult follow(UserRelationDto dto) {
        //1.校验参数
        if (dto.getOperation() == null || dto.getOperation() < 0 || dto.getOperation() > 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if (dto.getAuthorId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"作者信息不能为空");
        }

        //2.获取作者
        Integer followId = null;
        ApAuthor apAuthor = articleFeign.selectById(dto.getAuthorId());
        if(apAuthor != null){
            followId = apAuthor.getUserId();
        }
        if(followId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"关注人不存在");
        }else{

            ApUser apUser = AppThreadLocalUtils.getUser();
            if(apUser!= null){

                if(dto.getOperation() == 0){
                    //3.如果当前操作是0 创建数据（app用户关注信息和app的用户粉丝信息）
                    return followByUserId(apUser,followId,dto.getArticleId());
                }else{
                    //4.如果当前操作是1 删除数据（app用户关注信息和app的用户粉丝信息）
                    return followCancelByUserId(apUser,followId);
                }

            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
        }

    }

    @Autowired
    ApUserFollowMapper apUserFollowMapper;

    @Autowired
    ApUserFanMapper apUserFanMapper;

    /**
     * 取消关注的处理逻辑
     * @param apUser
     * @param followId
     * @return
     */
    private ResponseResult followCancelByUserId(ApUser apUser, Integer followId) {
        //先查再删
        ApUserFollow apUserFollow = apUserFollowMapper.selectOne(Wrappers.<ApUserFollow>lambdaQuery().eq(ApUserFollow::getUserId, apUser.getId()).eq(ApUserFollow::getFollowId, followId));
        if (apUserFollow == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"未关注");
        }else {
            ApUserFan apUserFan = apUserFanMapper.selectOne(Wrappers.<ApUserFan>lambdaQuery().eq(ApUserFan::getUserId, followId).eq(ApUserFan::getFansId, apUser.getId()));
            //删除用户粉丝信息
            if (apUserFan != null) {
                apUserFanMapper.delete(Wrappers.<ApUserFan>lambdaQuery().eq(ApUserFan::getUserId, followId).eq(ApUserFan::getFansId, apUser.getId()));
            }
            //删除用户关注信息
            apUserFollowMapper.delete(Wrappers.<ApUserFollow>lambdaQuery().eq(ApUserFollow::getUserId, apUser.getId()).eq(ApUserFollow::getFollowId, followId));
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
    }

    @Autowired
    private ApUserMapper apUserMapper;

    /**
     * 处理关注的操作
     * @param apUser
     * @param followId
     * @param articleId
     * @return
     */
    private ResponseResult followByUserId(ApUser apUser, Integer followId, Long articleId) {
        //判断当前粉丝是否存在
        ApUser followUser = apUserMapper.selectById(followId);
        if (followUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"关注用户不存在");
        }
        ApUserFollow apUserFollow = apUserFollowMapper.selectOne(Wrappers.<ApUserFollow>lambdaQuery().eq(ApUserFollow::getUserId, apUser.getId()).eq(ApUserFollow::getFollowId, followId));
        if(apUserFollow == null){
            //保存数据  ap_user_follow   ap_user_fan
            ApUserFan apUserFan = apUserFanMapper.selectOne(Wrappers.<ApUserFan>lambdaQuery().eq(ApUserFan::getUserId, followId).eq(ApUserFan::getFansId, apUser.getId()));
            //保存app用户粉丝信息
            if(apUserFan == null){
                apUserFan = new ApUserFan();
                apUserFan.setUserId(followId);
                apUserFan.setFansId(apUser.getId().longValue());
                apUserFan.setFansName(followUser.getName());
                apUserFan.setLevel((short)0);
                apUserFan.setIsDisplay(true);
                apUserFan.setIsShieldLetter(false);
                apUserFan.setIsShieldComment(false);
                apUserFan.setCreatedTime(new Date());
                apUserFanMapper.insert(apUserFan);
            }
            //保存app用户关注信息
            apUserFollow = new ApUserFollow();
            apUserFollow.setUserId(apUser.getId());
            apUserFollow.setFollowId(followId);
            apUserFollow.setCreatedTime(new Date());
            apUserFollow.setIsNotice(true);
            apUserFollow.setLevel((short)1);
            apUserFollowMapper.insert(apUserFollow);
            //TODO 记录关注文章的行为
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else{
            //已关注
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"已关注");
        }
    }

}