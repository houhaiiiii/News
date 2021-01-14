package com.light.behavior.controller.v1;

import com.light.apis.behavior.ApReadBehaviorControllerApi;
import com.light.behavior.service.ApReadBehaviorService;
import com.light.model.behavior.dtos.ReadBehaviorDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阅读行为控制器
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/read_behavior")
public class ApReadBehaviorController implements ApReadBehaviorControllerApi {

    @Autowired
    private ApReadBehaviorService apReadBehaviorService;

    @PostMapping
    @Override
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto dto) {
        return apReadBehaviorService.readBehavior(dto);
    }
}
