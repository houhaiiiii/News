package com.light.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.model.user.pojos.ApUserFollow;

/**
 * <p>
 * APP用户关注信息表 服务类
 * </p>
 *
 * @author itheima
 */
public interface ApUserFollowService extends IService<ApUserFollow> {

    /**
     * 通过用户id和行为id查询用户行为
     * @param userId
     * @param followId
     * @return
     */
    public ApUserFollow findByUserIdAndFollowId(Integer userId, Integer followId);

}
