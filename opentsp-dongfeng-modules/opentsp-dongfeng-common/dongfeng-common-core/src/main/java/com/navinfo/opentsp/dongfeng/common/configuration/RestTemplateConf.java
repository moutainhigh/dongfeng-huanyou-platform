package com.navinfo.opentsp.dongfeng.common.configuration;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.util.PropertiesUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * RestTemplate 注入
 *
 * Created by zhangyu on 2017/7/25.
 */
@Configuration
public class RestTemplateConf
{
    @Bean
    RestTemplate restTemplate()
    {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(StringUtil.toInteger(PropertiesUtil.getPropertiesByKey(Constants.READ_TIMEOUT)));
        requestFactory.setConnectTimeout(StringUtil.toInteger(PropertiesUtil.getPropertiesByKey(Constants.CONNECT_TIMEOUT)));
        
        // 添加转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }

}
