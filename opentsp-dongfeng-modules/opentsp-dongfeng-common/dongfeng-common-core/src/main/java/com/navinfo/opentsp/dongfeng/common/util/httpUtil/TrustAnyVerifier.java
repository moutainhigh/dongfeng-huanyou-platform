package com.navinfo.opentsp.dongfeng.common.util.httpUtil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by zhangy on 2016/4/26.
 */
public class TrustAnyVerifier implements HostnameVerifier
{
    
    public boolean verify(String hostname, SSLSession session)
    {
        System.out.println(">>> " + hostname + " " + session);
        return true;
    }
    
}
