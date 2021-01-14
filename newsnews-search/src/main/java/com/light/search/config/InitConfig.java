package com.light.search.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.light.common.jackson","com.light.common.aliyun","com.light.common.exception","com.light.common.threadpool"})
public class InitConfig {
}