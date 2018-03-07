package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangy on 2017/05/09. 获取验证码
 */
public class GetVerifyCodeCommand extends BaseCommand<HttpCommandResultWithData>
{
    
    @NotNull(message = "验证码id不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "验证码id不能空白", groups = {GroupCommand.class})
    String verifyCodeId;// 浏览器唯一标识（类似sessionId）
    
    public String getVerifyCodeId()
    {
        return verifyCodeId;
    }
    
    public void setVerifyCodeId(String verifyCodeId)
    {
        this.verifyCodeId = verifyCodeId;
    }
    
    @Override
    public String toString()
    {
        return "GetVerifyCodeCommand{" + "verifyCodeId='" + verifyCodeId + '\'' + '}';
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
}
