package com.navinfo.opentsp.dongfeng.common.client;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign依赖的接口，模拟http请求，对应定义出服务提供方的出入参，请求方式等即可 Created by zhangyu on 2017/06/07.
 */
// 此处对应eureka中注册的小写名称
@FeignClient("mail")
public interface MailClient
{
    
    /**
     * 发送邮件
     *
     * @param cmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendEmail", consumes = "application/json")
    HttpCommandResultWithData sendMail(@RequestBody SendEmailCommand cmd);
    
}