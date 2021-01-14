package com.light.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.model.user.pojos.ApUserFollow;
import com.light.user.mapper.ApUserFollowMapper;
import com.light.user.service.ApUserFollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP用户关注信息表 服务实现类
 * </p>
 *
 * @author itheima
 */
@Slf4j
@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {

    @Override
    public ApUserFollow findByUserIdAndFollowId(Integer userId, Integer followId) {
        return getOne(Wrappers.<ApUserFollow>lambdaQuery()
                .eq(ApUserFollow::getUserId,userId)
                .eq(ApUserFollow::getFollowId,followId));
    }

}
