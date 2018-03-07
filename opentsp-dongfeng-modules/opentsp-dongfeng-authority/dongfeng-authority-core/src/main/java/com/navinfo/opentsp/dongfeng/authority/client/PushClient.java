package com.navinfo.opentsp.dongfeng.authority.client;

import com.navinfo.opentsp.dongfeng.common.dto.CheckOnline;
import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign依赖的接口，模拟http请求，对应定义出服务提供方的出入参，请求方式等即可 Created by zhangyu on 2017/06/07.
 */
// 此处对应eureka中注册的小写名称
@FeignClient("push")
public interface PushClient
{
    
    /**
     * 校验用户是否在线
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/checkOnline", consumes = "application/json")
    CommonResult checkOnline(@RequestBody CheckOnline cmd);
    
    /**
     * 消息推送
     * 
     * @param msg
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/messagePush", consumes = "application/json")
    CommonResult messagePush(@RequestBody Message msg);
}
