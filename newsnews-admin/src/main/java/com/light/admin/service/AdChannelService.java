package com.light.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.admin.dtos.ChannelDto;
import com.light.model.admin.pojos.AdChannel;
import com.light.model.common.dtos.ResponseResult;

/**
 * 频道列表业务层接口
 * @author houhai
 */
public interface AdChannelService extends IService<AdChannel> {

    /**
     * 根据名称分页查询频道列表
     * @param dto
     * @return
     */
    public ResponseResult findByNameAndPage(ChannelDto dto);

    /**
     * 新增
     * @param channel
     * @return
     */
    public ResponseResult insert(AdChannel channel);

    /**
     * 修改
     * @param adChannel
     * @return
     */
    public ResponseResult update(AdChannel adChannel);

    /**
     * 删除
     * @param id
     * @return
     */
    public ResponseResult deleteById(Integer id);
}