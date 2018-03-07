package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2152_Cmd extends BaseCmd<CommonResult>
{
    private String type;// 监控类型
    
    private String phone;// 电话
    
    private String logId;// 日志ID
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getLogId()
    {
        return logId;
    }
    
    public void setLogId(String logId)
    {
        this.logId = logId;
    }
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
}
