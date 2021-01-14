package com.light.behavior.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.behavior.mapper.ApUnlikesBehaviorMapper;
import com.light.behavior.service.ApBehaviorEntryService;
import com.light.behavior.service.ApUnlikesBehaviorService;
import com.light.model.behavior.dtos.UnLikesBehaviorDto;
import com.light.model.behavior.pojos.ApBehaviorEntry;
import com.light.model.behavior.pojos.ApUnlikesBehavior;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.pojos.ApUser;
import com.light.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * APP不喜欢行为表 服务实现类
 *
 * @author houhai
 */
@Slf4j
@Service
public class ApUnlikesBehaviorServiceImpl extends ServiceImpl<ApUnlikesBehaviorMapper, ApUnlikesBehavior> implements ApUnlikesBehaviorService {

    @Autowired
    ApBehaviorEntryService apBehaviorEntryService;

    /**
     * 根据行为实体ID和文章ID查询行为是否存在
     *
     * @param entryId
     * @param articleId
     * @return
     */
    @Override
    public ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(Integer entryId, Long articleId) {
        return getOne(Wrappers.<ApUnlikesBehavior>lambdaQuery().eq(ApUnlikesBehavior::getArticleId, articleId)
                .eq(ApUnlikesBehavior::getEntryId, entryId));
    }

    /**
     * 保存不喜欢行为
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult unlike(UnLikesBehaviorDto dto) {
        //1.检查参数
        if (dto == null || dto.getArticleId() == null || (dto.getType() < 0 && dto.getType() > 2)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询行为实体
        ApUser user = AppThreadLocalUtils.getUser();
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryService.findByUserIdOrEquipmentId(user.getId(), dto.getEquipmentId());
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //3.点赞或取消点赞
        ApUnlikesBehavior apUnlikesBehavior = getOne(Wrappers.<ApUnlikesBehavior>lambdaQuery().eq(ApUnlikesBehavior::getArticleId, dto.getArticleId()).eq(ApUnlikesBehavior::getEntryId, apBehaviorEntry.getId()));
        if (apUnlikesBehavior == null && dto.getType() == 0) {
            apUnlikesBehavior = new ApUnlikesBehavior();
            apUnlikesBehavior.setArticleId(dto.getArticleId());
            apUnlikesBehavior.setEntryId(apBehaviorEntry.getId());
            apUnlikesBehavior.setType(Integer.valueOf(dto.getType()));
            apUnlikesBehavior.setCreatedTime(new Date());

            save(apUnlikesBehavior);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        //该用户已经为这个文章点过赞
        apUnlikesBehavior.setType(Integer.valueOf(dto.getType()));
        updateById(apUnlikesBehavior);
        return ResponseResult.okResult("操作成功");
    }

}
