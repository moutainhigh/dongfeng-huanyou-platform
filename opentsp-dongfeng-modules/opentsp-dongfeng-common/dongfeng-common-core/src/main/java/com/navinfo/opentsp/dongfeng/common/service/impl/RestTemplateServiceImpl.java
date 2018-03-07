package com.navinfo.opentsp.dongfeng.common.service.impl;

import com.navinfo.opentsp.dongfeng.common.service.IRestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * Created by zhangyu on 2017/7/25.
 */

@Service(value = "restTemplateService")
public class RestTemplateServiceImpl implements IRestTemplateService
{
    
    protected static final Logger logger = LoggerFactory.getLogger(RestTemplateServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * get方式访问http接口
     *
     * @param <T>
     * @param url
     * @param responseType
     * @param uriVariables
     * @return
     */
    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
    {
        logger.info("调用第三方接口 : url: {}, responseType: {}, uriVariables: {}",
            url,
            responseType.toString(),
            uriVariables.toString());
        return restTemplate.getForEntity(url, responseType, uriVariables);
    }
    
    /**
     * get方式访问http接口
     *
     * @param url
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
    {
        logger.info("调用第三方接口 : url: {}, responseType: {}, uriVariables: {}",
            url,
            responseType.toString(),
            uriVariables.toString());
        return restTemplate.getForEntity(url, responseType, uriVariables);
    }
    
    /**
     * get方式访问http接口
     *
     * @param <T>
     * @param url
     * @param responseType
     * @return
     */
    @Override
    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType)
    {
        logger.info("调用第三方接口 : url: {}, responseType: {}", url, responseType.toString());
        return restTemplate.getForEntity(url, responseType);
    }
    
    /**
     * post方式访问http接口
     *
     * @return
     * @param <T>
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     */
    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
            Object... uriVariables)
    {
        logger.info("调用第三方接口 : url: {}, request: {}, responseType: {}, uriVariables: {}",
            url,
            request.toString(),
            responseType.toString(),
            uriVariables.toString());
        return restTemplate.postForEntity(url, request, responseType, uriVariables);
    }
    
    /**
     * post方式访问http接口
     *
     * @param <T>
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @return
     */
    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
            Map<String, ?> uriVariables)
    {
        logger.info("调用第三方接口 : url: {}, request: {}, responseType: {}, uriVariables: {}",
            url,
            request.toString(),
            responseType.toString(),
            uriVariables.toString());
        return restTemplate.postForEntity(url, request, responseType, uriVariables);
    }
    
    /**
     * post方式访问http接口
     *
     * @param <T>
     * @param url
     * @param request
     * @param responseType
     * @return
     */
    @Override
    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType)
    {
        logger.info("调用第三方接口 : url: {}, request: {}, responseType: {}",
            url,
            request.toString(),
            responseType.toString());
        return restTemplate.postForEntity(url, request, responseType);
    }
}
