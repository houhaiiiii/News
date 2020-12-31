package com.light.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.media.pojos.WmUser;
import org.springframework.stereotype.Service;

/**
 * 自媒体用户业务层接口
 * @author houhai
 */
public interface WmUserService extends IService<WmUser> {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    public ResponseResult insert(WmUser wmUser);

    /**
     * 按照名称查询用户
     * @param name
     * @return
     */
    public ResponseResult findByName(String name);

}
