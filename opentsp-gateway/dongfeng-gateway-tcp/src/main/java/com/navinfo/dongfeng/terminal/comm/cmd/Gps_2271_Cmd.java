package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * 8103终端设置指令
 *
 * @wenya
 * @create 2017-03-29 14:47
 **/
public class Gps_2271_Cmd extends BaseCmd<CommonResult>
{
    private String deviceId;
    
    private String baseCode;
    
    private String logId;// 日志id
    
    private Integer statue;// 在线状态
    
    public Integer getStatue()
    {
        return statue;
    }
    
    public void setStatue(Integer statue)
    {
        this.statue = statue;
    }
    
    public String getLogId()
    {
        return logId;
    }
    
    public void setLogId(String logId)
    {
        this.logId = logId;
    }
    
    public String getDeviceId()
    {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }
    
    public String getBaseCode()
    {
        return baseCode;
    }
    
    public void setBaseCode(String baseCode)
    {
        this.baseCode = baseCode;
    }
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
}
