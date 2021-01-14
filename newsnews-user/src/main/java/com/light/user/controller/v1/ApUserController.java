package com.light.user.controller.v1;

import com.light.apis.user.ApUserControllerApi;
import com.light.model.user.pojos.ApUser;
import com.light.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApUserController implements ApUserControllerApi {

    @Autowired
    private ApUserService apUserService;

    @GetMapping("/{id}")
    @Override
    public ApUser findUserById(@PathVariable("id") Integer id) {
        return apUserService.getById(id);
    }
}
