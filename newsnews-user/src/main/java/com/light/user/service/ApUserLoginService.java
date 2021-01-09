package com.light.user.service;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.LoginDto;

public interface ApUserLoginService {

    /**
     * app端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
