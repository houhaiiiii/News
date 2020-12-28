package com.light.admin.controller.v1;

import com.light.admin.service.UserLoginService;
import com.light.apis.admin.LoginControllerApi;
import com.light.model.admin.dtos.AdUserDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制层
 * @author houhai
 */
@RequestMapping("/login")
@RestController
public class LoginController implements LoginControllerApi {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/in")
    @Override
    public ResponseResult login(AdUserDto dto) {
        return userLoginService.login(dto);
    }

}
