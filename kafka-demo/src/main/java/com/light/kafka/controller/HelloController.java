package com.light.kafka.controller;

import com.alibaba.fastjson.JSON;
import com.light.kafka.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("/hello")
    public String hello(){
        //第一个参数：topics
        //第二个参数：消息内容

        //对象传递参数
        User user = new User();
        user.setAge(18);
        user.setUsername("zhangsan");

        kafkaTemplate.send("kafka-hello", JSON.toJSONString(user));
        return "ok";
    }

}
