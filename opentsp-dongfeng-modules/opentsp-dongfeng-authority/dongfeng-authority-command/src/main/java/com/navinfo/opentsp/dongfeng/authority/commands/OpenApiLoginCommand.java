package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangy on 2017/03/09.第三方用户登录
 */
public class OpenApiLoginCommand extends BaseCommand<CommonResult>
{
    
    @NotNull(message = "账号名不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号名不能为空白", groups = {GroupCommand.class})
    private String userName;
    
    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "密码不能为空白", groups = {GroupCommand.class})
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OpenApiLoginCommand{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
