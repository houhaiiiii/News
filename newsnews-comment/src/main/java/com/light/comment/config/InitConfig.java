package com.light.comment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.light.common.jackson","com.light.common.aliyun","com.light.common.exception"})
public class InitConfig {
}
