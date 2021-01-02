package com.light.user.feign;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.pojos.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 作者服务远程接口
 * @author houhai
 */
@FeignClient("newsnews-wemedia")
public interface WemediaFeign {

    @PostMapping("/api/v1/user/save")
    public ResponseResult save(@RequestBody WmUser wmUser);

    @GetMapping("/api/v1/user/findByName/{name}")
    public WmUser findByName(@PathVariable("name") String name);

}
