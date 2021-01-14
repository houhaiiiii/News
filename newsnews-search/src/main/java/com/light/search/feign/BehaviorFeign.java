package com.light.search.feign;

import com.light.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 行为feign接口
 * @author houhai
 */
@FeignClient("newsnews-behavior")
public interface BehaviorFeign {

    @GetMapping("/api/v1/behavior_entry/one")
    public ApBehaviorEntry findByUserIdOrEntryId(@RequestParam("userId") Integer userId, @RequestParam("equipmentId") Integer equipmentId);

}
