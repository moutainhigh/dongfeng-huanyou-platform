package com.navinfo.opentsp.dongfeng.common.service;

import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;

/**
 * Created by zhangyu on 2017/7/25.
 */
public interface IRestTemplateService
{
    /**
     * get方式访问http接口
     *
     * @param <T>
     * @param url
     * @param responseType
     * @param uriVariables
     * @return
     */
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables);
    
    /**
     * get方式访问http接口
     *
     * @param <T>
     * @param url
     * @param responseType
     * @param uriVariables
     * @return
     */
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables);
    
    /**
     * get方式访问http接口
     *
     * @param <T>
     * @param url
     * @param responseType
     * @return
     */
    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType);
    
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
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
        Object... uriVariables);
    
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
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
        Map<String, ?> uriVariables);
    
    /**
     * post方式访问http接口
     *
     * @param <T>
     * @param url
     * @param request
     * @param responseType
     * @return
     */
    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType);
}
