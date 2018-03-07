package com.navinfo.opentsp.dongfeng.push.dto;

import com.navinfo.opentsp.dongfeng.common.dto.BaseDto;

/**
 * Created by zhangyu on 2017/3/30.
 */
public class ClientDto extends BaseDto
{
    
    private String clientIp;
    
    public String getClientIp()
    {
        return clientIp;
    }
    
    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }
    
}
