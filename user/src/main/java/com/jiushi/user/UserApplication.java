package com.jiushi.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author my deng
 * @since 2022/11/3 17:15
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.jiushi.**.mapper"})
@EnableFeignClients(basePackages = "com.jiushi.*.api.fegin")
@ComponentScan(basePackages = {"com.jiushi.core","com.jiushi.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}