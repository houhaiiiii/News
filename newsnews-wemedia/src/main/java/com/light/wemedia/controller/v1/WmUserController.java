package com.light.wemedia.controller.v1;

import com.light.apis.user.WmUserControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.media.pojos.WmUser;
import com.light.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户自媒体控制层
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/user")
public class WmUserController implements WmUserControllerApi {

    @Autowired
    private WmUserService service;

    @PostMapping("/save")
    @Override
    public ResponseResult save(WmUser wmUser) {

        return service.insert(wmUser);
    }

    @GetMapping("/findByName/{name}")
    @Override
    public ResponseResult findByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

}
