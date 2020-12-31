package com.light.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.media.pojos.WmUser;
import com.light.wemedia.mapper.WmUserMapper;
import com.light.wemedia.service.WmUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 自媒体用户业务实现类
 * @author houhai
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Override
    public ResponseResult insert(WmUser wmUser) {
        //校验参数
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //保存自媒体用户
        save(wmUser);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult findByName(String name) {

        //校验参数
        if (StringUtils.isEmpty(name)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //调用MyBatisPlus的自带方法查询自媒体用户
        WmUser wmUser = getOne(new QueryWrapper<WmUser>().eq("name", name));

        return ResponseResult.okResult(wmUser);
    }
}
