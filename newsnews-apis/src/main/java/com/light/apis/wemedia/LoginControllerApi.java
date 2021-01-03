package com.light.apis.wemedia;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmUserDto;

public interface LoginControllerApi {

    /**
     * 自媒体登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmUserDto dto);
}
