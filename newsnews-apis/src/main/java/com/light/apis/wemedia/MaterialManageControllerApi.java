package com.light.apis.wemedia;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.wemedia.dtos.WmMaterialDto;

/**
 * 素材列表前端控制器父接口
 * @author houhai
 */
public interface MaterialManageControllerApi {

    /**
     * 素材列表
     * @param dto
     * @return
     */
    ResponseResult findList(WmMaterialDto dto);

}
