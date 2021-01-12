package com.light.user.controller.v1;

import com.light.apis.user.ApUserRelationControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.UserRelationDto;
import com.light.user.service.ApUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关注行为
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/user")
public class ApUserRelationController implements ApUserRelationControllerApi {

    @Autowired
    private ApUserRelationService apUserRelationService;

    @PostMapping("/user_follow")
    @Override
    public ResponseResult follow(@RequestBody UserRelationDto dto) {
        ResponseResult responseResult = apUserRelationService.follow(dto);
        return responseResult;
    }

}
