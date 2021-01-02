package com.light.wemedia;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * 用户自媒体引导类
 * @author houhai
 */
@EnableDiscoveryClient
@MapperScan("com.light.wemedia.mapper")
@SpringBootApplication
public class WemediaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaSpringApplication.class);
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
