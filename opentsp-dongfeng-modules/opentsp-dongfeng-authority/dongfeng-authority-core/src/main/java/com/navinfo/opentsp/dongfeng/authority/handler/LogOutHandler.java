package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.LogOutCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登出handler
 */
@Component
public class LogOutHandler extends ValidateTokenAndReSetAbstracHandler<LogOutCommand, CommonResult>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public LogOutHandler()
    {
        super(LogOutCommand.class, CommonResult.class);
    }
    
    protected LogOutHandler(Class<LogOutCommand> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected CommonResult businessHandle(LogOutCommand command)
    {
        // logger.info("=====  login start  =====");
        CommonResult result = new CommonResult();
        
        try
        {
            result = authorityService.logout(command);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.LOGIN_OUT_ERROR);
            logger.error("===== businessHandle Exception =====" , e);
        }
        return result;
    }
}
