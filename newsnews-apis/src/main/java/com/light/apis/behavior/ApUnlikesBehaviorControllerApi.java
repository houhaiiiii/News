package com.light.apis.behavior;

import com.light.model.behavior.dtos.UnLikesBehaviorDto;
import com.light.model.behavior.pojos.ApUnlikesBehavior;
import com.light.model.common.dtos.ResponseResult;

public interface ApUnlikesBehaviorControllerApi {

    /**
     * 根据行为实体id和文章id查询不喜欢行为
     * @param entryId
     * @param articleId
     * @return
     */
    public ApUnlikesBehavior findUnLikeByArticleIdAndEntryId(Integer entryId,Long articleId);


    /**
     * 保存不喜欢行为
     * @param dto
     * @return
     */
    public ResponseResult unlike(UnLikesBehaviorDto dto);

}