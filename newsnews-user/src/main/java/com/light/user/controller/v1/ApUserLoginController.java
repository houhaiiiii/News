package com.light.user.controller.v1;

import com.light.apis.user.ApUserLoginControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.LoginDto;
import com.light.user.service.ApUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app用户登录控制层实现类
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/login")
public class ApUserLoginController implements ApUserLoginControllerApi {

    @Autowired
    private ApUserLoginService apUserLoginService;

    @PostMapping("/login_auth")
    @Override
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserLoginService.login(dto);
    }

}
