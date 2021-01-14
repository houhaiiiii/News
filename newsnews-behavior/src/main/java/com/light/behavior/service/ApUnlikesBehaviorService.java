package com.light.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.behavior.dtos.UnLikesBehaviorDto;
import com.light.model.behavior.pojos.ApUnlikesBehavior;
import com.light.model.common.dtos.ResponseResult;


public interface ApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {

    /**
     * 根据行为实体ID和文章ID查询行为是否存在
     * @param entryId
     * @param articleId
     * @return
     */
    public ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(Integer entryId, Long articleId);

    /**
     * 保存不喜欢行为
     * @param dto
     * @return
     */
    public ResponseResult unlike(UnLikesBehaviorDto dto);
}