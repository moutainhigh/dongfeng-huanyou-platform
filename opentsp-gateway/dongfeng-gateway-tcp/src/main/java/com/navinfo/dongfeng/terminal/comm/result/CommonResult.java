package com.navinfo.dongfeng.terminal.comm.result;

import messaging.Command;

/**
 * 自定义通用返回结果类,重新实现Result,可自定义code
 *
 * @author zhangy
 * @date 2016-03-02
 * @modify
 * @copyright Navi Tsp
 */
public class CommonResult implements  Command.Result {
    
    private int resultCode;
    
    private String message;
    
    public CommonResult()
    {
        // 默认成功
        this.resultCode = ReturnCode.OK.code();
        this.message = ReturnCode.OK.message();
    }
    
    public CommonResult(Integer resultCode, String message)
    {
        this.resultCode = resultCode.intValue();
        this.message = message;
    }
    
    public int getResultCode()
    {
        return this.resultCode;
    }
    
    public void setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
    }
    
    public <T extends CommonResult> T fillResult(InterfaceResultCode result)
    {
        this.setResultCode(result.code());
        this.setMessage(result.message());
        return (T)this;
    }
    
    public String getMessage()
    {
        return this.message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String toString()
    {
        return "CommonResult{resultCode=" + this.resultCode + ", message=\'" + this.message + '\'' + '}';
    }
}