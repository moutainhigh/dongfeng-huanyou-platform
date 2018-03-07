package com.navinfo.opentsp.dongfeng.openapi.commands.param;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
public class GetCarParamCommand extends BaseOpenApiCommand
{
	@NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询时间不能为空白", groups = {GroupCommand.class})
	private String minute;
	
    public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	@Override
    public String toString()
    {
        return "GetCarParamCommand{}";
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
}
