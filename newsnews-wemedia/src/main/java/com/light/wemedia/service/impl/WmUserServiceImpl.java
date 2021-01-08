package com.light.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import com.light.model.wemedia.dtos.WmUserDto;
import com.light.model.wemedia.pojos.WmUser;
import com.light.utils.common.AppJwtUtil;
import com.light.wemedia.mapper.WmUserMapper;
import com.light.wemedia.service.WmUserService;
import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自媒体用户业务实现类
 * @author houhai
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增
     * @param wmUser
     * @return
     */
    @Override
    public ResponseResult insert(WmUser wmUser) {
        //校验参数
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //测试分布式事务
        //int i = 10/0;

        //保存自媒体用户
        save(wmUser);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 通过名称查询自媒体用户
     * @param name
     * @return
     */
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

    /**
     * 登录
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(WmUserDto dto) {

        //校验参数
        if (StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"用户名或密码错误");
        }

        //查询数据库中的用户信息
        List<WmUser> list = list(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName,dto.getName()));
        if (list != null || list.size()==1) {

            WmUser wmUser = list.get(0);

            //比对密码
            String pwd = DigestUtils.md5DigestAsHex((dto.getPassword() + wmUser.getSalt()).getBytes());

            if (wmUser.getPassword().equals(pwd)){
                //返回数据jwt
                Map<String, Object> map = new HashMap<>();

                //获取jti
                String token = AppJwtUtil.getToken(wmUser.getId().longValue());
                Claims claims = AppJwtUtil.getClaimsBody(token);
                Object jti = claims.get("jti");

                //将jti保存到redis
                redisTemplate.boundValueOps(jti).set(token,7, TimeUnit.DAYS);

                //添加token到集合
                //map.put("token", AppJwtUtil.getToken(wmUser.getId().longValue()));
                map.put("token", jti);
                wmUser.setPassword("");
                wmUser.setSalt("");
                map.put("user",wmUser);

                return ResponseResult.okResult(map);
            }else {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR,"用户不存在");
        }

    }

}
