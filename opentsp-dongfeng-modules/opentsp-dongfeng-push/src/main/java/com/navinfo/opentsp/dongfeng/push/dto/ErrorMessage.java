package com.navinfo.opentsp.dongfeng.push.dto;

import com.navinfo.opentsp.dongfeng.common.dto.Message;

public class ErrorMessage extends Message
{
    public String errorMessage;
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
