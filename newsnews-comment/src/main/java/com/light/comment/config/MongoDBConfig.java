package com.light.comment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * mongoDB配置类引入
 * @author houhai
 */
@Configuration
@ComponentScan("com.light.common.mongo")
public class MongoDBConfig {
}
