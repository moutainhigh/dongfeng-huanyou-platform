package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.OpenApiLoginCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登入handler
 */
@Component
public class OpenApiLoginHandler extends NotValidateTokenAbstractHandler<OpenApiLoginCommand, CommonResult>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public OpenApiLoginHandler()
    {
        super(OpenApiLoginCommand.class, CommonResult.class);
    }
    
    protected OpenApiLoginHandler(Class<OpenApiLoginCommand> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected CommonResult businessHandle(OpenApiLoginCommand command)
    {
        // logger.info("=====  login start  =====");
        CommonResult result = new CommonResult();
        
        try
        {
            result = authorityService.openApilogin(command);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.LOGIN_FAIL);
            logger.error("===== businessHandle Exception =====" , e);
        }
        
        return result;
    }
}
