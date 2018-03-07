package com.navinfo.opentsp.dongfeng.push.handler;

import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.push.service.IPushWatchedService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushHandler extends NotValidateTokenAbstractHandler<Message, HttpCommandResultWithData>
{
    
    private static Log logger = LogFactory.getLog(PushHandler.class);
    
    @Autowired
    IPushWatchedService pushWatchedService;
    
    public PushHandler()
    {
        super(Message.class, HttpCommandResultWithData.class);
    }
    
    protected PushHandler(Class<Message> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    public HttpCommandResultWithData businessHandle(Message msg)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try
        {
            // 打印log
            logger.info(" PUSH TOKEN IS : " + msg.getToken() + " PUSH MESSAGE IS : " + msg.getData());
            
            // 消息推送
            pushWatchedService.notifyWatchers(msg, result);
        }
        catch (Exception e)
        {
            // 异常信息
            String errorMessage = e.getMessage();
            
            // 打印log
            logger.error(" ERROR EXCEPTION IS: " + errorMessage + " ERROR TOKEN IS : " + msg.getToken()
                + " ERROR MESSAGE IS : " + msg.getData());

            logger.error(e.getMessage(),e);
            
            result.setResultCode(506);
            result.setMessage(errorMessage);
        }
        return result;
    }
}
