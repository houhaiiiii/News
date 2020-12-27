package com.light.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.admin.dtos.SensitiveDto;
import com.light.model.admin.pojos.AdSensitive;
import com.light.model.common.dtos.ResponseResult;

/**
 * 敏感信息业务层接口
 * @author houhai
 */
public interface AdSensitiveService extends IService<AdSensitive> {

    /**
     * 根据名称分页查询敏感词信息
     * @param dto
     * @return
     */
    public ResponseResult list(SensitiveDto dto);

    /**
     * 新增
     * @param adSensitive
     * @return
     */
    public ResponseResult insert(AdSensitive adSensitive);

    /**
     * 修改
     * @param adSensitive
     * @return
     */
    public ResponseResult update(AdSensitive adSensitive);

    /**
     * 删除
     * @param id
     * @return
     */
    public ResponseResult deleteById(Integer id);

}
