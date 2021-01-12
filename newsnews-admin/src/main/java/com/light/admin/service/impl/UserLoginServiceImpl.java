package com.light.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.admin.mapper.AdUserMapper;
import com.light.admin.service.UserLoginService;
import com.light.model.admin.dtos.AdUserDto;
import com.light.model.admin.pojos.AdUser;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.utils.common.AppJwtUtil;
import com.light.utils.common.MD5Utils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 运营端用户登录业务实现类
 * @author houhai
 */
@Service
@Transactional
public class UserLoginServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements UserLoginService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseResult login(AdUserDto dto) {
        //1.检查参数
        if (StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword()) ){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "用户名或密码不能为空");
        }

        //2.根据用户名查询数据库
        QueryWrapper<AdUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name",dto.getName());
        AdUser adUser = getOne(wrapper);

        //查询返回结果如果为空则说明没有该用户
        if (adUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"用户不存在");
        }

        //对用户密码进行校验
        String pwd = MD5Utils.encodeWithSalt(dto.getPassword(), adUser.getSalt());

        //登录失败返回失败信息
        if (pwd.equals(adUser.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //登录成功生成token
        adUser.setPassword("");
        adUser.setSalt("");

        //获取jti : token
        String token = AppJwtUtil.getToken(adUser.getId().longValue());
        Claims claims = AppJwtUtil.getClaimsBody(token);
        Object jti = claims.get("jti");

        //将jti保存到redis
        redisTemplate.boundValueOps(jti).set(token,7, TimeUnit.DAYS);
        //将jti返回给用户
        Map<String, Object> map = new HashMap<>();
        map.put("token",jti);
        map.put("user",adUser);

        return ResponseResult.okResult(map);
    }

}
