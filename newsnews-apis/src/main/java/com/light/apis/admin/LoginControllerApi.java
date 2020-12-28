package com.light.apis.admin;

import com.light.model.admin.dtos.AdUserDto;
import com.light.model.admin.pojos.AdUser;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * admin登录控制层父接口
 * @author houhai
 */
public interface LoginControllerApi {

    /**
     * admin登录功能
     * @param dto
     * @return
     */
    public ResponseResult login(@RequestBody AdUserDto dto);

}
