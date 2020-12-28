package com.light.password.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.*;

/**
 * 生成令牌
 *  - 设置过期时间
 *  - 携带自定义信息
 *
 *  解析令牌获取数据
 */

public class JwtTest {

    /**
     * 加密
     */
    @Test
    public void creatJwt(){

        //创建设置过期时间对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,30);

        //自定义数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","xiaoli");
        map.put("age","18");

        String token = Jwts.builder().setId(UUID.randomUUID().toString())
                .setSubject("system login")  //令牌主题
                .setIssuer("lead-news")      //设置令牌签发者信息
                .setIssuedAt(new Date())     //设置令牌签发时间
                .signWith(SignatureAlgorithm.HS256, "123456") //设置签名算法
                .setExpiration(calendar.getTime())  //设置令牌过期时间为30秒
                .addClaims(map)             //设置自定义载荷数据
                //.setClaims(map)
                .compact();

        System.out.println(token);

        //结果：eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0N2ZkMGRmZC0wZmJhLTQ4NzEtYWEyMS1mN2I4MjFkMDRmNmEiLCJzdWIiOiJzeXN0ZW0gbG9naW4iLCJpc3MiOiJsZWFkLW5ld3MiLCJpYXQiOjE2MDkxNDA3NzZ9.5-MM7OC2fNxZp-aYumgHkZuoqceAXI8BRykN3U2oxDU

    }


    /**
     * 解析
     */
    @Test
    public void parseJwt(){

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0N2ZkMGRmZC0wZmJhLTQ4NzEtYWEyMS1mN2I4MjFkMDRmNmEiLCJzdWIiOiJzeXN0ZW0gbG9naW4iLCJpc3MiOiJsZWFkLW5ld3MiLCJpYXQiOjE2MDkxNDA3NzZ9.5-MM7OC2fNxZp-aYumgHkZuoqceAXI8BRykN3U2oxDU";

        Jws<Claims> jws = Jwts.parser().setSigningKey("123456").parseClaimsJws(token);
        Claims body = jws.getBody();
        System.out.println(body);

        /*
        {jti=47fd0dfd-0fba-4871-aa21-f7b821d04f6a, sub=system login, iss=lead-news, iat=1609140776}
         */

    }

}
