package com.light.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.behavior.dtos.LikesBehaviorDto;
import com.light.model.behavior.pojos.ApLikesBehavior;
import com.light.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP点赞行为表 服务类
 * </p>
 *
 * @author houhai
 */
public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {
    /**
     * 存储喜欢数据
     * @param dto
     * @return
     */
    public ResponseResult like(LikesBehaviorDto dto);

    /**
     * 根据行为实体id和文章id查询点赞行为
     * @param articleId
     * @param entryId
     * @param type
     * @return
     */
    public ApLikesBehavior findLikeByArticleIdAndEntryId(Long articleId,Integer entryId,Short type);

}
