package com.navinfo.opentsp.dongfeng.push.service.impl;

import com.navinfo.opentsp.dongfeng.push.service.IClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangyu on 2017/3/30.
 */

@Service(value = "clientService")
public class ClientServiceImpl implements IClientService
{
    private static Log logger = LogFactory.getLog(ClientServiceImpl.class);
    
    /**
     * 获取客户端ip
     * 
     * @param request
     * @return
     */
    @Override
    public String getClientIp(HttpServletRequest request)
    {
        // 获取nginx透传的真实ip
        String ip = request.getHeader("X-real-ip");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        logger.debug("client ip is :" + ip);
        return ip;
    }
}
