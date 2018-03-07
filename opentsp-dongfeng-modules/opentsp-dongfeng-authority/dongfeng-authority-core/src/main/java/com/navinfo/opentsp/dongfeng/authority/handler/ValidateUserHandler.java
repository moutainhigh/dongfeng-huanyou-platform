package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.ValidateUserCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户校验handler
 */
@Component
public class ValidateUserHandler extends NotValidateTokenAbstractHandler<ValidateUserCommand, CommonResult>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public ValidateUserHandler()
    {
        super(ValidateUserCommand.class, CommonResult.class);
    }
    
    protected ValidateUserHandler(Class<ValidateUserCommand> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected CommonResult businessHandle(ValidateUserCommand command)
    {
        // logger.info("=====  login start  =====");
        CommonResult result = new CommonResult();
        
        try
        {
            result = authorityService.validateUser(command);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.LOGIN_FAIL);
            logger.error("===== businessHandle Exception =====" , e);
        }
        
        return result;
    }
}
