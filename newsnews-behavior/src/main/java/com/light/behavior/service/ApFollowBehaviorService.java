package com.light.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.behavior.dtos.FollowBehaviorDto;
import com.light.model.behavior.pojos.ApFollowBehavior;
import com.light.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP关注行为表 服务类
 * </p>
 *
 * @author houhai
 */
public interface ApFollowBehaviorService extends IService<ApFollowBehavior> {

    /**
     * 存储关注数据
     * @param dto
     * @return
     */
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto);

}
