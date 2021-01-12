package com.light.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.user.dtos.LoginDto;
import com.light.model.user.pojos.ApUser;
import com.light.user.mapper.ApUserMapper;
import com.light.user.service.ApUserLoginService;
import com.light.utils.common.AppJwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录业务实现类
 *
 * @author houhai
 */
@Service
public class ApUserLoginServiceImpl implements ApUserLoginService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseResult login(LoginDto dto) {
        //1.校验参数
        if (dto.getEquipmentId() == null && (StringUtils.isEmpty(dto.getPhone()) && StringUtils.isEmpty(dto.getPassword()))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.手机号+密码登录
        if (!StringUtils.isEmpty(dto.getPassword()) && !StringUtils.isEmpty(dto.getPassword())) {
            //用户登录
            ApUser dbUser = apUserMapper.selectOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (dbUser != null) {

                //解析
                String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + dbUser.getSalt()).getBytes());

                if (dbUser.getPassword().equals(pswd)) {
                    Map<String, Object> map = new HashMap<>();
                    dbUser.setPassword("");
                    dbUser.setSalt("");

                    //解析从过滤器传来的token
                    String token = AppJwtUtil.getToken(dbUser.getId().longValue());
                    String jti = AppJwtUtil.getClaimsBody(token).get("jti", String.class);
                    //把JTI的token存到redis
                    redisTemplate.boundValueOps(jti).set(token,7,TimeUnit.DAYS);

                    //将token设置到请求头
                    map.put("token", jti);
                    map.put("user", dbUser);

                    return ResponseResult.okResult(map);

                } else {
                    return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
                }
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
            }
        } else {
            //3.设备登录
            if (dto.getEquipmentId() == null) {
                //设备ID为空直接返回错误信息
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }

            //获得jti
            String token = AppJwtUtil.getToken(0L);
            String jti = AppJwtUtil.getClaimsBody(token).get("jti", String.class);
            //把JTI的token存到redis
            redisTemplate.boundValueOps(jti).set(token,7,TimeUnit.DAYS);

            Map<String, Object> map = new HashMap<>();
            //再把jti放到map集合返回给前端
            map.put("token", jti);

            return ResponseResult.okResult(map);
        }

    }
}
