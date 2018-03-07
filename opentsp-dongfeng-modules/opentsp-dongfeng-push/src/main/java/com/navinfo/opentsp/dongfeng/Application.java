package com.navinfo.opentsp.dongfeng;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.navinfo.opentsp.dongfeng,com.navinfo.opentsp.dongfeng.push")
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class Application implements ApplicationContextAware
{
    
    private static ApplicationContext context;
    
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        System.out.println("启动完毕。。。。。。");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        context = applicationContext;
    }
    
    public static ApplicationContext getApplicationContext()
    {
        return context;
    }
    
}
