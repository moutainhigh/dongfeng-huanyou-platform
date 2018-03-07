package com.navinfo.opentsp.dongfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by zhangy on 2017/03/20.
 */
@Configuration
@ComponentScan(basePackages = "com.navinfo.opentsp.dongfeng,com.navinfo.opentsp.dongfeng.kafka")
@EnableJpaRepositories(basePackages = {"com.navinfo.opentsp.dongfeng.common.service.impl",
    "com.navinfo.opentsp.dongfeng.kafka.repository"})
@EntityScan(basePackages = {"com.navinfo.opentsp.dongfeng.common.entity", "com.navinfo.opentsp.dongfeng.kafka.entity"})
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
