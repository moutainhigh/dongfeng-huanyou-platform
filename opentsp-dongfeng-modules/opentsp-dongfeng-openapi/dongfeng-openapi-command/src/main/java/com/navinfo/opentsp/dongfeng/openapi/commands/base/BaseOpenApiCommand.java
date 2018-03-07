package com.navinfo.opentsp.dongfeng.openapi.commands.base;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * open api cmd基类
 * 
 * @author zhangyu
 *
 */
@SuppressWarnings("rawtypes")
public class BaseOpenApiCommand extends BaseCommand<HttpCommandResultWithData>
{
    
    @NotNull(message = "账号名不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号名不能为空白", groups = {GroupCommand.class})
    public String userName;
    
    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "密码不能为空白", groups = {GroupCommand.class})
    public String password;
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Override
    public String toString()
    {
        return "GetCarParamCommand{" + "userName='" + userName + '\'' + ", password='" + password + '\'' + '}';
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
}
