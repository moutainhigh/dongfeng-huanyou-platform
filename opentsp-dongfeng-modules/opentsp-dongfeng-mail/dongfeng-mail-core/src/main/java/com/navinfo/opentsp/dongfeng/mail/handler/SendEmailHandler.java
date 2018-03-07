package com.navinfo.opentsp.dongfeng.mail.handler;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.mail.service.ISendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送邮件
 * 
 * @author zhangyu
 * @version 1.0
 * @since 2017-03-23
 */
@Component
public class SendEmailHandler extends NotValidateTokenAbstractHandler<SendEmailCommand, HttpCommandResultWithData>
{
    
    protected static final Logger logger = LoggerFactory.getLogger(SendEmailHandler.class);
    
    @Autowired
    private ISendEmailService sendEmailService;
    
    public SendEmailHandler()
    {
        super(SendEmailCommand.class, HttpCommandResultWithData.class);
    }
    
    protected SendEmailHandler(Class<SendEmailCommand> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    public HttpCommandResultWithData businessHandle(SendEmailCommand command)
    {
        logger.info(" ===== SendEmailHandler start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        if (sendEmailService.sendEmail(command))
        {
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage("邮件发送成功");
        }
        else
        {
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage("邮件发送失败");
        }
        logger.info(" ===== SendEmailHandler end==========");
        return result;
    }
    
}
