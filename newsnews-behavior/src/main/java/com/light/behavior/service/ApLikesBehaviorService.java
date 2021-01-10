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
 * @author itlight
 */
public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {
    /**
     * 存储喜欢数据
     * @param dto
     * @return
     */
    public ResponseResult like(LikesBehaviorDto dto);

}
