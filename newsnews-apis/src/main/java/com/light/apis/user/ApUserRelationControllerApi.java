package com.light.apis.user;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.UserRelationDto;

public interface ApUserRelationControllerApi {

    /**
     * 关注或取消关注
     * @param dto
     * @return
     */
    ResponseResult follow(UserRelationDto dto);
}
