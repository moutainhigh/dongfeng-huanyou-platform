package com.navinfo.opentsp.dongfeng.monitor.client;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.monitor.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign依赖的接口，模拟http请求，对应定义出服务提供方的出入参，请求方式等即可 Created by zhangyu on 2017/06/07.
 */
// 此处对应eureka中注册的小写名称
@FeignClient("cmd")
public interface CmdClient
{
    
    /**
     * 2170下发指令
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2170", consumes = "application/json")
    CommonResult call_2170(@RequestBody Gps_2170_Cmd cmd);
    
    /**
     * 2271下发指令
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2271", consumes = "application/json")
    CommonResult call_2271(@RequestBody Gps_2271_Cmd cmd);
    
    /**
     * 2152下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2152", consumes = "application/json")
    CommonResult call_2152(@RequestBody Gps_2152_Cmd cmd);
    
    /**
     * 2317下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2317", consumes = "application/json")
    CommonResult call_2317(@RequestBody Gps_2317_Cmd cmd);
    
    /**
     * 2318下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2318", consumes = "application/json")
    CommonResult call_2318(@RequestBody Gps_2318_Cmd cmd);
    
    /**
     * 2151下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2151", consumes = "application/json")
    CommonResult call_2151(@RequestBody Gps_2151_Cmd cmd);
}
