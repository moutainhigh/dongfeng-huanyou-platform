package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2151_Cmd extends BaseCmd<CommonResult>
{
    private static final long serialVersionUID = -2461632089443764231L;
    
    private Integer type;// 显示类型
    
    private String content;// 消息内容

    private String communicationIds;// 通讯号
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    @Override
    public String getContent()
    {
        return content;
    }
    
    @Override
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCommunicationIds() {
        return communicationIds;
    }

    public void setCommunicationIds(String communicationIds) {
        this.communicationIds = communicationIds;
    }

    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
}
