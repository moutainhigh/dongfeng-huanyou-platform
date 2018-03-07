package com.navinfo.opentsp.dongfeng.push.handler;

import com.navinfo.opentsp.dongfeng.common.dto.PushAllMessage;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.push.service.IPushWatchedService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushAllHandler extends NotValidateTokenAbstractHandler<PushAllMessage, HttpCommandResultWithData>
{
    
    private static Log logger = LogFactory.getLog(PushAllHandler.class);
    
    @Autowired
    IPushWatchedService pushWatchedService;
    
    public PushAllHandler()
    {
        super(PushAllMessage.class, HttpCommandResultWithData.class);
    }
    
    protected PushAllHandler(Class<PushAllMessage> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    public HttpCommandResultWithData businessHandle(PushAllMessage msg)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try
        {
            // 消息推送(广播)
            pushWatchedService.notifyAllWatchers(msg, result);
        }
        catch (Exception e)
        {
            // 异常信息
            String errorPushAllMessage = e.getMessage();
            
            // 打印log
            logger.error(" ERROR EXCEPTION IS: " + errorPushAllMessage + " ERROR TOKEN IS : " + msg.getToken()
                + " ERROR PushAllMessage IS : " + msg.getData());
            logger.error(e.getMessage(),e);
            result.setResultCode(506);
            result.setMessage(errorPushAllMessage);
        }
        return result;
    }
}
