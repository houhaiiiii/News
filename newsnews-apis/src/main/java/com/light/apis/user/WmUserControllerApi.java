package com.light.apis.user;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.pojos.WmUser;

/**
 * 自媒体用户控制层API接口
 * @author houhai
 */
public interface WmUserControllerApi {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    public ResponseResult save(WmUser wmUser);

    /**
     * 按照名称查询用户
     * @param name
     * @return
     */
    public ResponseResult findByName(String name);

}
