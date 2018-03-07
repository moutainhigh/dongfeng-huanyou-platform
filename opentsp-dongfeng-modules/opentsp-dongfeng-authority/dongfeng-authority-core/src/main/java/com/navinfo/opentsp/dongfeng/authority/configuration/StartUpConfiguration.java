package com.navinfo.opentsp.dongfeng.authority.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by zhangyu on 2017/5/185.
 */
@Configuration
public class StartUpConfiguration
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Bean
    PropertiesFactoryBean properties()
    {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource resource = new ClassPathResource("url2Command.properties");
        propertiesFactoryBean.setLocation(resource);
        return propertiesFactoryBean;
    }
    
}
