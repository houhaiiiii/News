package com.light.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.article.feign.BehaviorFeign;
import com.light.article.mapper.ApCollectionMapper;
import com.light.article.service.ApCollectionService;
import com.light.model.article.dtos.CollectionBehaviorDto;
import com.light.model.article.pojos.ApCollection;
import com.light.model.behavior.pojos.ApBehaviorEntry;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.pojos.ApUser;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 用户收藏业务层
 * @author houhai
 */
public class ApCollectionServiceImpl extends ServiceImpl<ApCollectionMapper, ApCollection> implements ApCollectionService {

    @Autowired
    private BehaviorFeign behaviorFeign;

    /**
     * 收藏文章
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collect(CollectionBehaviorDto dto) {
        //1.校验参数
        if (dto == null || dto.getArticleId() == null || (dto.getOperation() != 0 && dto.getOperation() != -1 ) ) {
            return  ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //在线程中获得
        ApUser user = AppThreadLocalUtils.getUser();
        Integer userId = null;
        if (user != null) {
             userId = user.getId();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"线程中无用户信息~~~");
        }

        //2.查询行为实体
        ApBehaviorEntry behaviorEntry = behaviorFeign.findByUserIdOrEntryId(userId, dto.getEquipmentId());

        //校验返回的结果
        if (behaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"查不到用户行为实体呀");
        }

        //3.查询收藏数据
        ApCollection apCollection = getOne(Wrappers.<ApCollection>lambdaQuery().eq(ApCollection::getEntryId, behaviorEntry.getId()).eq(ApCollection::getArticleId, dto.getArticleId()));

        //4. 完成收藏和取消收藏
        //没有收藏过该文章 , 保存收藏数据
        if (apCollection == null && dto.getOperation() == 0) {
            apCollection = new ApCollection();
            apCollection.setArticleId(dto.getArticleId());
            apCollection.setCollectionTime(new Date());
            apCollection.setEntryId(behaviorEntry.getId());
            apCollection.setPublishedTime(dto.getPublishedTime());
            apCollection.setType(dto.getType());

            save(apCollection);
            return ResponseResult.okResult("收藏成功");
        }

        //删除收藏数据
        if (dto.getOperation() == 1) {
            remove(Wrappers.<ApCollection>lambdaQuery().eq(ApCollection::getEntryId, behaviorEntry.getId()).eq(ApCollection::getArticleId, dto.getArticleId()));

            return ResponseResult.okResult("取消收藏成功");
        }

        return ResponseResult.okResult("操作成功");

    }

}
