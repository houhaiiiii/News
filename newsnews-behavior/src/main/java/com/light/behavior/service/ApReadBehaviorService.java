package com.light.behavior.service;

import com.light.model.behavior.dtos.ReadBehaviorDto;
import com.light.model.common.dtos.ResponseResult;

/**
 * 阅读行为业务层接口
 * @author houhai
 */
public interface ApReadBehaviorService {

    /**
     * 保存阅读行为
     * @param dto
     * @return
     */
    ResponseResult readBehavior(ReadBehaviorDto dto);

}
