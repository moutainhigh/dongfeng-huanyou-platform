package com.navinfo.opentsp.dongfeng.common.dto;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PushAllMessage extends BaseCommand<HttpCommandResultWithData> implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @NotNull(message = "data不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "data不能为空白", groups = {GroupCommand.class})
    // 推送的json数据
    public String data;
    
    public String getData()
    {
        return data;
    }
    
    public void setData(String data)
    {
        this.data = data;
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
}
