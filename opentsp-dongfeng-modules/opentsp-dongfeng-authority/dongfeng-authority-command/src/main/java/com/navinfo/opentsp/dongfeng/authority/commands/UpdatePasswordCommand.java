package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangy on 2017/03/09.用户修改密码
 */
public class UpdatePasswordCommand extends BaseCommand<CommonResult>
{
    
    @NotNull(message = "旧密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "旧密码不能为空白", groups = {GroupCommand.class})
    private String oldPassword;
    
    @NotNull(message = "新密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "新密码不能为空白", groups = {GroupCommand.class})
    private String newPassword;
    
    public String getNewPassword()
    {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
    
    public String getOldPassword()
    {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }
    
    @Override
    public String toString()
    {
        return "UpdatePasswordCommand{" + "oldPassword='" + oldPassword + '\'' + ", newPassword='" + newPassword + '\''
            + '}';
    }
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
