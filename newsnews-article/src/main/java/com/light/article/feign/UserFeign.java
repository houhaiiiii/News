package com.light.article.feign;

import com.light.model.user.pojos.ApUserFollow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务调用feign接口
 *
 * @author houhai
 */
@FeignClient("newsnews-user")
public interface UserFeign {

    @GetMapping("/api/v1/user_follow/one")
    ApUserFollow findByUserIdAndFollowId(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId);

}
