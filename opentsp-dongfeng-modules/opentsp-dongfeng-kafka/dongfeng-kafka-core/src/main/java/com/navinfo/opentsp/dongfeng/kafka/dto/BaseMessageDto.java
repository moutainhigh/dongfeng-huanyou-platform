package com.navinfo.opentsp.dongfeng.kafka.dto;

import java.io.Serializable;

/**
 * 消息推送基类
 * 
 * Created by zhangyu on 2017/3/29.
 */
public class BaseMessageDto implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private String messageType;
    
    public String getMessageType()
    {
        return messageType;
    }
    
    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }
}
