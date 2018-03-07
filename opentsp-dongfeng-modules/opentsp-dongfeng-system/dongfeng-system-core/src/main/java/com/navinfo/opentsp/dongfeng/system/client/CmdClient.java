package com.navinfo.opentsp.dongfeng.system.client;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.model.*;
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
     * 2253下发指令
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2253", consumes = "application/json")
    CommonResult call_2253(@RequestBody Gps_2253_Cmd cmd);
    
    /**
     * 2273下发指令
     * 
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2273", consumes = "application/json")
    CommonResult call_2273(@RequestBody Gps_2273_Cmd cmd);
    
    /**
     * 2270下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2270", consumes = "application/json")
    CommonResult call_2270(@RequestBody Gps_2270_Cmd cmd);
    
    /**
     * 2405下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2405", consumes = "application/json")
    CommonResult call_2405(@RequestBody Gps_2405_Cmd cmd);
    
    /**
     * 2502下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2502", consumes = "application/json")
    CommonResult call_2502(@RequestBody BaseTcpCommand cmd);
    
    /**
     * 2500下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2500", consumes = "application/json")
    CommonResult call_2500(@RequestBody BaseTcpCommand cmd);
    
    /**
     * 2505下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2505", consumes = "application/json")
    CommonResult call_2505(@RequestBody BaseTcpCommand cmd);

    /**
     * 2507下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2507", consumes = "application/json")
    CommonResult call_2507(@RequestBody BaseTcpCommand cmd);


    /**
     * 2508下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2508", consumes = "application/json")
    CommonResult call_2508(@RequestBody BaseTcpCommand cmd);

    /**
     * 2274下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2274", consumes = "application/json")
    CommonResult call_2274(@RequestBody BaseTcpCommand cmd);

    /**
     * 2275下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2275", consumes = "application/json")
    CommonResult call_2275(@RequestBody BaseTcpCommand cmd);

    /**
     * 2273下发指令
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/send/2267", consumes = "application/json")
    CommonResult call_2267(@RequestBody Gps_2267_Cmd cmd);
}
