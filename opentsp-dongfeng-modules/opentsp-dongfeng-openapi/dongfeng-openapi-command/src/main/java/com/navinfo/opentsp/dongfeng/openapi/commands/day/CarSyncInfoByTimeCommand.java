package com.navinfo.opentsp.dongfeng.openapi.commands.day;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@SuppressWarnings("rawtypes")
public class CarSyncInfoByTimeCommand extends BaseOpenApiCommand
{
    
    @NotNull(message = "查询天数不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询天数不能为空白", groups = {GroupCommand.class})
    private String day;
    
    public String getDay()
    {
        return day;
    }
    
    public void setDay(String day)
    {
        this.day = day;
    }
    
    @Override
    public String toString()
    {
        return "CarSyncInfoByTimeCommand{" + "day='" + day + '\'' + '}';
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
}