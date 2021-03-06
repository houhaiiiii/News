package com.light.wemedia.controller.v1;

import com.light.apis.wemedia.WmUserControllerApi;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.pojos.WmUser;
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

    /**
     * 根据id查询自媒体用户
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    @Override
    public WmUser findWmUserById(@PathVariable("id") Integer id){
        return service.getById(id);
    };

}
