package com.light.apis.admin;

import com.light.model.admin.dtos.SensitiveDto;
import com.light.model.admin.pojos.AdSensitive;
import com.light.model.common.dtos.ResponseResult;

/**
 * 敏感词控制层接口
 * @author houhai
 */
public interface AdSensitiveControllerApi {

    /**
     * 根据名称分页查询敏感词
     * @param dto
     * @return
     */
    public ResponseResult list(SensitiveDto dto);

    /**
     * 新增
     * @param dto
     * @return
     */
    public ResponseResult save(AdSensitive dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    public ResponseResult update(AdSensitive dto);

    /**
     * 通过id删除敏感词信息
     * @param id
     * @return
     */
    public ResponseResult deleteById(Integer id);

}
