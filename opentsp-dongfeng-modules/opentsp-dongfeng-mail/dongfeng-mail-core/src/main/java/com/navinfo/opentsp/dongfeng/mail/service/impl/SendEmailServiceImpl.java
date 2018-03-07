package com.navinfo.opentsp.dongfeng.mail.service.impl;

import com.alibaba.fastjson.JSON;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.httpUtil.HttpUtil;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.mail.service.ISendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 发送邮件
 * 
 * @author zhangyu
 * @version 1.0
 * @since 2017-03-23
 */
@Service(value = "sendEmailService")
public class SendEmailServiceImpl implements ISendEmailService
{
    
    protected static final Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);
    
    @Value("${email.infos}")
    private String mailInfos;
    
    @Value("${smtp.email.servers}")
    private String smtpServers;
    
    @Value("${smtp.email.ports}")
    private String smtpPorts;
    
    public boolean sendEmail(SendEmailCommand sendEmailCommand)
    {
        logger.info("=====sendEmail Service start=====");
        try
        {
            // 用于多发件人选择
            int iIndex = Integer.parseInt(sendEmailCommand.getWm()) - 1;
            // 发件人邮箱地址
            String myEmailAccount = mailInfos.split(";")[iIndex].split(",")[0];
            // 发件人邮箱密码
            String myEmailPassword = mailInfos.split(";")[iIndex].split(",")[1];
            // smtp端口
            String smtpPort = smtpPorts.split(";")[iIndex];
            // smtp发送服务器地址
            String smtpServer = smtpServers.split(";")[iIndex];
            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            // 参数配置
            Properties props = new Properties();
            // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.transport.protocol", "smtp");
            // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.host", smtpServer);
            // 需要请求认证
            props.setProperty("mail.smtp.auth", "true");
            
            // ssl加密配置
            // props.setProperty("mail.smtp.port", smtpPort);
            // props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // props.setProperty("mail.smtp.socketFactory.fallback", "false");
            // props.setProperty("mail.smtp.socketFactory.port", smtpPort);
            
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getDefaultInstance(props);
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
            
            // 3. 创建一封邮件
            MimeMessage message =
                createMimeMessage(session,
                    myEmailAccount,
                    sendEmailCommand.getToEmails(),
                    sendEmailCommand.getCcEmails(),
                    sendEmailCommand.getBccEmails(),
                    sendEmailCommand.getSubject(),
                    sendEmailCommand.getContent());
            
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            
            transport.connect(myEmailAccount, myEmailPassword);
            
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            
            // 7. 关闭连接
            transport.close();
            logger.info("=====sendEmail Service end=====");
        }
        catch (Exception e)
        {
            logger.error("=====sendEmail Service error=====");
            logger.error(e.getMessage(),e);
            return false;
        }
        return true;
    }
    
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
        throws Exception
    {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "", "UTF-8"));
        
        // 3-1. To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmails, "", "UTF-8"));
        
        // 3-2. cc: 抄送人
        
        if (StringUtil.isNotEmpty(ccEmails))
        {
            
            message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(ccEmails, "", "UTF-8"));
        }
        
        // 3-3. bcc: 密送人
        if (StringUtil.isNotEmpty(bccEmails))
        {
            
            message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(bccEmails, "", "UTF-8"));
        }
        
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        
        // 6. 设置发件时间
        message.setSentDate(new Date());
        
        // 7. 保存设置
        message.saveChanges();
        
        return message;
    }
    
    public static void main(String[] args)
    {
        SendEmailCommand command = new SendEmailCommand();
//        command.setToEmails("zhangyu2@mapbar.com");
        command.setToEmails("yangml@mapbar.com");
        // List<String> ccEmails = new ArrayList<>();
        // ccEmails.add("jiangcm@mapbar.com");
        // command.setCcEmails(ccEmails);
        // List<String> bccEmails = new ArrayList<>();
        // bccEmails.add("sunbo@mapbar.com");
        // command.setBccEmails(bccEmails);
        command.setSubject("测试邮件");
        command.setContent("您好，这是一封测试邮件");
        command.setWm("1");
        String jsonStr = JSON.toJSONString(command);
        try
        {
            String resultJson = HttpUtil.postJson("http://localhost:5555/dongfeng/mail/sendEmail", jsonStr, "");
            System.out.println(JSON.parseObject(resultJson).getIntValue("resultCode"));
        }
        catch (IOException e)
        {
            logger.error(e.getMessage(),e);
        }
    }
}
