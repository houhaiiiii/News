package com.light.wemedia.controller.v1;

import com.light.apis.wemedia.LoginControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmUserDto;
import com.light.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体端登录控制层
 * @author houhai
 */
@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping("/in")
    @Override
    public ResponseResult login(@RequestBody WmUserDto dto) {
        return wmUserService.login(dto);
    }

}
