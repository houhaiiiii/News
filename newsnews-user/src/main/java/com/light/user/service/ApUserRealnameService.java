package com.light.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.common.dtos.PageResponseResult;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.user.dtos.AuthDto;
import com.light.model.user.pojos.ApUserRealname;

/**
 * 用户认证列表业务层接口
 * @author houhai
 */
public interface ApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 根据状态查询需要认证相关的用户信息
     * @param dto
     * @return
     */
    public PageResponseResult loadListByStatus(AuthDto dto);

    /**
     * 根据状态进行审核
     * @param dto
     * @param status
     * @return
     */
    public ResponseResult updateStatusById(AuthDto dto, Short status);

}
