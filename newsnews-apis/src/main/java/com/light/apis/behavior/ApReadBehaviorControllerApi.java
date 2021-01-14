package com.light.apis.behavior;

import com.light.model.behavior.dtos.ReadBehaviorDto;
import com.light.model.common.dtos.ResponseResult;

/**
 * APP端用户阅读行为控制层父接口
 * @author houhai
 */
public interface ApReadBehaviorControllerApi {

    /**
     * 保存或更新阅读行为
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);

}
