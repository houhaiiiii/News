package com.light.behavior.controller.v1;

import com.light.apis.behavior.ApLikesBehaviorControllerApi;
import com.light.behavior.service.ApLikesBehaviorService;
import com.light.model.behavior.dtos.LikesBehaviorDto;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes_behavior")
public class ApLikesBehaviorController implements ApLikesBehaviorControllerApi {

    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    /**
     * 记录点赞行为
     * @param dto
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return apLikesBehaviorService.like(dto);
    }

}
