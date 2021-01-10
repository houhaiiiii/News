package com.light.user.controller.v1;

import com.light.apis.user.ApUserRealnameControllerApi;
import com.light.common.constans.user.UserConstants;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.AuthDto;
import com.light.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户审核列表控制层
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealnameController implements ApUserRealnameControllerApi {

    @Autowired
    private ApUserRealnameService service;

    @PostMapping("/list")
    @Override
    public PageResponseResult loadListByStatus(@RequestBody AuthDto dto) {
        return service.loadListByStatus(dto);
    }

    @PostMapping("/authPass")
    @Override
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return service.updateStatusById(dto, UserConstants.PASS_AUTH);
    }

    @PostMapping("/authFail")
    @Override
    public ResponseResult authFail(@RequestBody AuthDto dto) {
        return service.updateStatusById(dto,UserConstants.FAIL_AUTH);
    }

}
