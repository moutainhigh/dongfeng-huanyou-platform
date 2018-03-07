package com.navinfo.opentsp.dongfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangy on 2015/12/31.
 */
@Configuration
@ComponentScan(basePackages = "com.navinfo.opentsp.dongfeng,com.navinfo.opentsp.dongfeng.authority")
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        System.out.println("启动完毕。。。。。。");
    }
}
