package com.light.admin.controller.v1;

import com.light.admin.service.AdChannelService;
import com.light.apis.admin.AdChannelControllerApi;
import com.light.model.admin.dtos.ChannelDto;
import com.light.model.admin.pojos.AdChannel;
import com.light.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 频道列表控制层
 * @author houhai
 */
@RestController
@RequestMapping("/api/v1/channel")
public class AdChannelController implements AdChannelControllerApi {

    @Autowired
    private AdChannelService channelService;

    /**
     * 根据名称分页查询频道列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult findByNameAndPage(@RequestBody ChannelDto dto){
        return channelService.findByNameAndPage(dto);
    }

    /**
     * 新增
     * @param channel
     * @return
     */
    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AdChannel channel) {
        return channelService.insert(channel);
    }

    /**
     * 修改
     * @param adChannel
     * @return
     */
    @Override
    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdChannel adChannel) {
        return channelService.update(adChannel);
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return channelService.deleteById(id);
    }
}