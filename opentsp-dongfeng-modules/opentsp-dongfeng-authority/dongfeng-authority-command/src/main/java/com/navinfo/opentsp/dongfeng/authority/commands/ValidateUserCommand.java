package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangy on 2017/03/09. 用户校验
 */
public class ValidateUserCommand extends BaseCommand<CommonResult>
{
    
    @NotNull(message = "用户id不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "用户id不能为空白", groups = {GroupCommand.class})
    String userId;
    
    public String getUserId()
    {
        
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
