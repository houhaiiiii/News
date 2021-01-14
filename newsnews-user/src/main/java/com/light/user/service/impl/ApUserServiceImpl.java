package com.light.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.model.user.pojos.ApUser;
import com.light.user.mapper.ApUserMapper;
import com.light.user.service.ApUserService;
import org.springframework.stereotype.Service;

@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

}
