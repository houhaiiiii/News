package com.light.apis.behavior;

import com.light.model.behavior.dtos.LikesBehaviorDto;
import com.light.model.common.dtos.ResponseResult;

public interface ApLikesBehaviorControllerApi {


    /**
     * 保存点赞行为
     * @param dto
     * @return
     */
    ResponseResult like(LikesBehaviorDto dto);

}
