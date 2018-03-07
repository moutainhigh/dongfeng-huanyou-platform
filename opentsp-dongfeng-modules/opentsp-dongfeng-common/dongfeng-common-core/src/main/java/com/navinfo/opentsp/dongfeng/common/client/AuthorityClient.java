package com.navinfo.opentsp.dongfeng.common.client;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.authority.commands.ValidateTokenCommand;
import com.navinfo.opentsp.dongfeng.authority.commands.ValidateUserCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign依赖的接口，模拟http请求，对应定义出服务提供方的出入参，请求方式等即可 Created by zhangyu on 2017/06/07.
 */
// 此处对应eureka中注册的小写名称
@FeignClient("authority")
public interface AuthorityClient
{
    
    /**
     * token校验
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/validateToken", consumes = "application/json")
    HttpCommandResultWithData validateToken(@RequestBody ValidateTokenCommand cmd);
    
    /**
     * 用户校验
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/validateUser", consumes = "application/json")
    CommonResult validateUser(@RequestBody ValidateUserCommand cmd);
    
    /**
     * 第三方接口用户权限校验
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/openApiLogin", consumes = "application/json")
    CommonResult openApilogin(@RequestBody OpenApiLoginCommand cmd);
    
}
