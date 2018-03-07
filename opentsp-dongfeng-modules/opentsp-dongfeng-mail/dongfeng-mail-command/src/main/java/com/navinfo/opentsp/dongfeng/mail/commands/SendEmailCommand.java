package com.navinfo.opentsp.dongfeng.mail.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.Pattern;

/**
 * 发送邮件
 * 
 * @author zhangyu
 * @version 1.0
 * @since 2017-03-23
 */
public class SendEmailCommand extends BaseCommand<HttpCommandResultWithData>
{
    private String wm = "1";// 获取邮件信息的下标值
    
    @Pattern(regexp = RegexpUtils.MAPBAR_EMAIL_REGEXP, message = "收件人邮箱格式不正确", groups = {GroupCommand.class})
    private String toEmails;// 收件人邮箱
    
    private String ccEmails;// 抄送人邮箱
    
    private String bccEmails;// 密送人邮箱
    
    private String subject;// 邮件标题
    
    private String content;// 邮件正文
    
    public String getWm()
    {
        return wm;
    }
    
    public void setWm(String wm)
    {
        if (StringUtils.isEmpty(wm))
        {
            wm = "1";
        }
        this.wm = wm;
    }
    
    public String getToEmails()
    {
        return toEmails;
    }
    
    public void setToEmails(String toEmails)
    {
        this.toEmails = toEmails;
    }
    
    public String getCcEmails()
    {
        return ccEmails;
    }
    
    public void setCcEmails(String ccEmails)
    {
        this.ccEmails = ccEmails;
    }
    
    public String getBccEmails()
    {
        return bccEmails;
    }
    
    public void setBccEmails(String bccEmails)
    {
        this.bccEmails = bccEmails;
    }
    
    public String getSubject()
    {
        return subject;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
    @Override
    public String toString()
    {
        return "SendEmailCommand{" + "wm='" + wm + '\'' + ", toEmails=" + toEmails + ", ccEmails=" + ccEmails
            + ", bccEmails=" + bccEmails + ", subject='" + subject + '\'' + ", content='" + content + '\'' + '}';
    }
}
