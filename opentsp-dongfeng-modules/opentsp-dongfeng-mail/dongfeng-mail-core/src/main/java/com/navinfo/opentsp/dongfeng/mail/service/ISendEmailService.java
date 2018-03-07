package com.navinfo.opentsp.dongfeng.mail.service;

import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 * 
 * @author zhangyu
 * @version 1.0
 * @since 2017-03-23
 */
public interface ISendEmailService
{
    
    public boolean sendEmail(SendEmailCommand sendEmailCommand);
    
    /**
     * 创建一封邮件
     * 
     * @param session
     * @param sendMail
     * @param toEmails
     * @param ccEmails
     * @param bccEmails
     * @param subject
     * @param content
     * @return
     * @throws Exception
     */
    public MimeMessage createMimeMessage(Session session, String sendMail, String toEmails, String ccEmails,
        String bccEmails, String subject, String content)
        throws Exception;
    
}
