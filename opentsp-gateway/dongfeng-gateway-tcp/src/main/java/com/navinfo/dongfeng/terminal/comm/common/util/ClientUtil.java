package com.navinfo.dongfeng.terminal.comm.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端相关工具类
 * 
 * Created by zhangyu on 2017/5/24.
 */
public class ClientUtil
{
    
    private static Log logger = LogFactory.getLog(ClientUtil.class);
    
    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request)
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
