package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.GetVerifyCodeCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取验证码 handler
 */
@Component
public class GetVerifyCodeHandler extends NotValidateTokenAbstractHandler<GetVerifyCodeCommand, HttpCommandResultWithData>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public GetVerifyCodeHandler()
    {
        super(GetVerifyCodeCommand.class, HttpCommandResultWithData.class);
    }
    
    protected GetVerifyCodeHandler(Class<GetVerifyCodeCommand> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected HttpCommandResultWithData businessHandle(GetVerifyCodeCommand command)
    {
        // logger.info("=====  login start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        
        try
        {
            result = authorityService.getGetVerifyCode(command, result);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.GET_GETVERIFYCODE_ERROR);
            logger.error("===== businessHandle Exception =====" , e);
        }
        
        return result;
    }
}
