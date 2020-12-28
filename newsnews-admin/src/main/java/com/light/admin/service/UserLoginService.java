package com.light.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.admin.dtos.AdUserDto;
import com.light.model.admin.pojos.AdUser;
import com.light.model.common.dtos.ResponseResult;

public interface UserLoginService extends IService<AdUser> {


    /**
     * 用户登录功能
     * @param dto
     * @return
     */
    public ResponseResult login(AdUserDto dto);

}
