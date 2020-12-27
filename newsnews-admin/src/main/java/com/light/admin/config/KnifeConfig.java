package com.light.admin.config;

import com.light.common.knife4j.Swagger2Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.light.common.knife4j")
//@Import(Swagger2Configuration.class)
public class KnifeConfig {

}
