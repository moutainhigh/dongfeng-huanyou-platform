package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.GetUserInfoByTokenCommand;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 通过token获取用户信息
 */
@Component
public class GetUserInfoByTokenHandler extends ValidateTokenAndReSetAbstracHandler<GetUserInfoByTokenCommand, HttpCommandResultWithData>
{
    
    @Resource
    private IAuthorityService authorityService;
    
    public GetUserInfoByTokenHandler()
    {
        super(GetUserInfoByTokenCommand.class, HttpCommandResultWithData.class);
    }
    
    protected GetUserInfoByTokenHandler(Class<GetUserInfoByTokenCommand> commandType,
        Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected HttpCommandResultWithData businessHandle(GetUserInfoByTokenCommand command)
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        
        try
        {
            result = authorityService.getUserInfoByToken(command, result);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.GET_USER_INFO_ERROR);
            logger.error("===== businessHandle Exception =====" , e);
        }
        return result;
    }
}
