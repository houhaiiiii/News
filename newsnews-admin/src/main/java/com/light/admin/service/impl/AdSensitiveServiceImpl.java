package com.light.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.admin.mapper.AdSensitiveMapper;
import com.light.admin.service.AdSensitiveService;
import com.light.model.admin.dtos.SensitiveDto;
import com.light.model.admin.pojos.AdSensitive;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 敏感词信息业务实现类
 * @author houhai
 */
@Service
public class AdSensitiveServiceImpl extends ServiceImpl<AdSensitiveMapper, AdSensitive> implements AdSensitiveService {

    /**
     * 根据名称分页查询敏感词
     * @param dto
     * @return
     */
    @Override
    public ResponseResult list(SensitiveDto dto){
        //传进来的名称是空则则直接返回错误提示
        if (dto == null || "".equals(dto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        dto.checkParam();

        //根据名称模糊分页
        Page page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<AdSensitive> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getName())){
            wrapper.like(AdSensitive::getCreatedTime,dto.getName());
        }
        IPage iPage = page(page, wrapper);

        //结果返回
        ResponseResult result = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        result.setData(iPage.getRecords());

        return result;
    };

    /**
     * 新增
     * @param adSensitive
     * @return
     */
    @Override
    public ResponseResult insert(AdSensitive adSensitive){
        //判断传入的参数是否正常,传入的为空直接返回错误信息
        if (adSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }

        //保存当前的时间戳
        adSensitive.setCreatedTime(new Date());

        //保存数据
        save(adSensitive);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    };

    /**
     * 修改
     * @param adSensitive
     * @return
     */
    @Override
    public ResponseResult update(AdSensitive adSensitive){

        //判空
        if (adSensitive == null || adSensitive.getId() == null) {
            //返回错误提示
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        updateById(adSensitive);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    };

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id){

        //判断传入参数是否为空
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //判断敏感词是否存在
        AdSensitive adSensitive = getById(id);
        if (adSensitive == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //删除操作
        removeById(id);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    };

}
