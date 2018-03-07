package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.ValidateTokenCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * token校验handler
 */
@Component
public class ValidateTokenHandler extends NotValidateTokenAbstractHandler<ValidateTokenCommand, HttpCommandResultWithData>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public ValidateTokenHandler()
    {
        super(ValidateTokenCommand.class, HttpCommandResultWithData.class);
    }
    
    protected ValidateTokenHandler(Class<ValidateTokenCommand> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected HttpCommandResultWithData businessHandle(ValidateTokenCommand command)
    {
        // logger.info("=====  login start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        
        try
        {
            result = authorityService.validateToken(command);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.LOGIN_FAIL);
            logger.error("===== businessHandle Exception =====" , e);
        }
        
        return result;
    }
}
