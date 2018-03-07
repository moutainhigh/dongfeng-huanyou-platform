package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.LockCarCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.ILockCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 远程锁车
 * 
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/30
 */
@Component
public class LockCarHandler extends ValidateTokenAndReSetAbstracHandler<LockCarCommand, CommonResult>
{
    
    @Autowired
    private ILockCarService lockCarService;
    
    public LockCarHandler()
    {
        super(LockCarCommand.class, CommonResult.class);
    }
    
    protected LockCarHandler(Class<LockCarCommand> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected CommonResult businessHandle(LockCarCommand command)
    {
        CommonResult result = new CommonResult();
        try
        {
            result = lockCarService.lockCar(command);
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error("error", e);
        }
        return result;
    }
}
