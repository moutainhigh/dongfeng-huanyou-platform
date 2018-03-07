package com.navinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Administrator on 2016/11/29.
 */
@Configuration
@ComponentScan(basePackages = "com.navinfo.dongfeng.terminal.comm")
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableScheduling
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
