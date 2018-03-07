package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangy on 2017/03/09.用户登录
 */
public class LoginCommand extends BaseCommand<HttpCommandResultWithData>
{

    @NotNull(message = "账号名不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号名不能为空白", groups = {GroupCommand.class})
    private String userName;

    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "密码不能为空白", groups = {GroupCommand.class})
    private String password;

    private String verifyCode;

    @NotNull(message = "验证码id不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "验证码id不能为空白", groups = {GroupCommand.class})
    private String verifyCodeId;

    // 是否踢掉其他用户用
    @NotNull(message = "确认登陆标识不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "确认登陆标识不能为空白", groups = {GroupCommand.class})
    private String confirmLoginFlg;

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

    public String getVerifyCode()
    {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode)
    {
        this.verifyCode = verifyCode;
    }

    public String getVerifyCodeId()
    {
        return verifyCodeId;
    }

    public void setVerifyCodeId(String verifyCodeId)
    {
        this.verifyCodeId = verifyCodeId;
    }

    public String getConfirmLoginFlg()
    {
        return confirmLoginFlg;
    }

    public void setConfirmLoginFlg(String confirmLoginFlg)
    {
        this.confirmLoginFlg = confirmLoginFlg;
    }

    @Override
    public String toString()
    {
        return "LoginCommand{" + "userName='" + userName + '\'' + ", password='" + password + '\'' + ", verifyCode='"
                + verifyCode + '\'' + ", verifyCodeId='" + verifyCodeId + '\'' + ", confirmLoginFlg='" + confirmLoginFlg
                + '\'' + '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }

}
