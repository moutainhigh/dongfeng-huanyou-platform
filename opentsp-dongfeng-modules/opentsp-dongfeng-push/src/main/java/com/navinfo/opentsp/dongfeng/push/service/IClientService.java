package com.navinfo.opentsp.dongfeng.push.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangyu on 2017/3/30.
 */
public interface IClientService
{
    /**
     * 获取客户端ip
     * 
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request);
    
}
