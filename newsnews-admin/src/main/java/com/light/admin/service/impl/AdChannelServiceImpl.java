package com.light.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.admin.mapper.AdChannelMapper;
import com.light.admin.service.AdChannelService;
import com.light.model.admin.dtos.ChannelDto;
import com.light.model.admin.pojos.AdChannel;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 频道列表业务层实现类
 * @author houhai
 */
@Service
public class AdChannelServiceImpl extends ServiceImpl<AdChannelMapper, AdChannel> implements AdChannelService {


    /**
     * 根据名称分页查询频道列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findByNameAndPage(ChannelDto dto) {

        //1.参数检测
        if(dto==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();

        //2.安装名称模糊分页查询
        Page page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<AdChannel> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(dto.getName())){
            lambdaQueryWrapper.like(AdChannel::getName,dto.getName());
        }
        IPage result = page(page, lambdaQueryWrapper);

        //3.结果封装
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)result.getTotal());
        responseResult.setData(result.getRecords());
        return responseResult;
    }

    /**
     * 频道新增
     * @param adChannel
     * @return
     */
    @Override
    public ResponseResult insert(AdChannel adChannel) {
        //检查参数
        if(null == adChannel){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //补充创建时间
        adChannel.setCreatedTime(new Date());

        //保存
        boolean flag = save(adChannel);

        if (!flag) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 修改
     * @param adChannel
     * @return
     */
    @Override
    public ResponseResult update(AdChannel adChannel) {

        //1.检查参数
        if(null == adChannel || adChannel.getId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.修改
        updateById(adChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id) {
        //1.检查参数
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.判断当前频道是否存在 和 是否有效
        AdChannel adChannel = getById(id); //通过id获得频道信息

        if(adChannel==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //如果当前状态为有效则不能删除
        if(adChannel.getStatus()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"频道有效不能删除");
        }

//        int i = 10/0;

        //3.删除频道
        removeById(id);
        
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}