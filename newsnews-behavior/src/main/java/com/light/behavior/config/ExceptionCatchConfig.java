package com.light.behavior.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 集成common模块的通用异常类
 * @author houhai
 */
@Configuration
@ComponentScan("com.light.common.exception")
public class ExceptionCatchConfig {

}
