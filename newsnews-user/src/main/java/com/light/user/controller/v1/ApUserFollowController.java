package com.light.user.controller.v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.apis.user.ApUserFollowControllerApi;
import com.light.model.user.pojos.ApUserFollow;
import com.light.user.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关注信息控制器
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/user_follow")
public class ApUserFollowController implements ApUserFollowControllerApi {

    @Autowired
    private ApUserFollowService apUserFollowService;

    @GetMapping("/one")
    @Override
    public ApUserFollow findByUserIdAndFollowId(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId) {
        return apUserFollowService.findByUserIdAndFollowId(userId,followId);
    }
}
