package com.navinfo.opentsp.dongfeng.rpws.common.utils.douglas;

import java.io.Serializable;

/**
 * Created by zhangyu on 2017/3/22.
 */
public class WebServiceIp implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // ip地址
    String serviceIp;
    
    // 鉴权码
    String Identifies;
    
    public String getServiceIp()
    {
        return serviceIp;
    }
    
    public void setServiceIp(String serviceIp)
    {
        this.serviceIp = serviceIp;
    }
    
    public String getIdentifies()
    {
        return Identifies;
    }
    
    public void setIdentifies(String identifies)
    {
        Identifies = identifies;
    }
    
    @Override
    public String toString()
    {
        return "WebServiceIp{" + "serviceIp='" + serviceIp + '\'' + ", Identifies='" + Identifies + '\'' + '}';
    }
}
