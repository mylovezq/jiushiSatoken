package com.jiushi.pay;

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
@MapperScan(basePackages = {"com.jiushi.**.dao"})
@EnableFeignClients(basePackages = "com.jiushi.*.api.fegin")
@ComponentScan(basePackages = {"com.jiushi.core","com.jiushi.pay"})
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class,args);
    }
}