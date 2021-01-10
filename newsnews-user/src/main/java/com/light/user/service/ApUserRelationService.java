package com.light.user.service;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.UserRelationDto;

public interface ApUserRelationService {

    /**
     * 用户关注/取消关注
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);
}
