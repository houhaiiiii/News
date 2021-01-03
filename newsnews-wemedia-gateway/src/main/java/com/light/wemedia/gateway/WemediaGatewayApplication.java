package com.light.wemedia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 自媒体端网关引导启动类
 * @author houhai
 */
@EnableDiscoveryClient  //开启注册中心
@SpringBootApplication
public class WemediaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaGatewayApplication.class,args);
    }

}
