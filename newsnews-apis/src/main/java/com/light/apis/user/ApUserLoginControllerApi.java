package com.light.apis.user;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.LoginDto;

/**
 * APP端用户登录控制层父接口
 * @author houhai
 */
public interface ApUserLoginControllerApi {

    /**
     * app端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

}
