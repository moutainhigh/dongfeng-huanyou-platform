package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.UpdatePasswordCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 修改密码handler
 */
@Component
public class UpdatePasswordHandler extends ValidateTokenAndReSetAbstracHandler<UpdatePasswordCommand, CommonResult>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public UpdatePasswordHandler()
    {
        super(UpdatePasswordCommand.class, CommonResult.class);
    }
    
    protected UpdatePasswordHandler(Class<UpdatePasswordCommand> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected CommonResult businessHandle(UpdatePasswordCommand command)
    {
        // logger.info("=====  login start  =====");
        CommonResult result = new CommonResult();
        
        try
        {
            result = authorityService.updatePassword(command);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.LOGIN_FAIL);
            logger.error("===== businessHandle Exception =====" , e);
        }
        
        return result;
    }
}
