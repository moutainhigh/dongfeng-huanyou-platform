package com.navinfo.opentsp.dongfeng.push.handler;

import com.navinfo.opentsp.dongfeng.common.dto.CheckOnline;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.push.service.IPushWatchedService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 校验用户是否在线
 */
@RestController
public class CheckOnlineHandler extends NotValidateTokenAbstractHandler<CheckOnline, CommonResult>
{
    
    private static Log logger = LogFactory.getLog(CheckOnlineHandler.class);
    
    @Autowired
    IPushWatchedService pushWatchedService;
    
    public CheckOnlineHandler()
    {
        super(CheckOnline.class, CommonResult.class);
    }
    
    protected CheckOnlineHandler(Class<CheckOnline> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    public CommonResult businessHandle(CheckOnline cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 打印log
            logger.info(" CheckOnlineHandler TOKEN IS : " + cmd.getToken());
            
            // 消息推送
            pushWatchedService.checkOnline(cmd, result);
        }
        catch (Exception e)
        {
            // 异常信息
            String errorMessage = e.getMessage();
            
            // 打印log
            logger.error(" ERROR EXCEPTION IS: " + errorMessage + " ERROR TOKEN IS : " + cmd.getToken());
            logger.error(e.getMessage(),e);
            result.setResultCode(506);
            result.setMessage(errorMessage);
        }
        return result;
    }
}
