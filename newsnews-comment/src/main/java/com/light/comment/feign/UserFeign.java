package com.light.comment.feign;

import com.light.model.user.pojos.ApUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("newsnews-user")
public interface UserFeign {

    @GetMapping("/api/v1/user/{id}")
    ApUser findUserById(@PathVariable("id") Long id);
}
