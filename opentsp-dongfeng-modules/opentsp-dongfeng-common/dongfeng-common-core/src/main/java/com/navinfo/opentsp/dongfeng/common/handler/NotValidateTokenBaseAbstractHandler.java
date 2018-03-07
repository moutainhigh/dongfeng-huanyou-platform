package com.navinfo.opentsp.dongfeng.common.handler;

import com.navinfo.opentsp.common.messaging.Command;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.ValidateUtil;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentspcore.common.handler.AbstractCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 不需要token校验的BaseAbstractHandler
 * 
 * Created by zhangyu on 2016/11/07.
 */
public abstract class NotValidateTokenBaseAbstractHandler<C extends Command<?>, CR extends CommonResult> extends
    AbstractCommandHandler<C, CR>
{
    protected static final Logger logger = LoggerFactory.getLogger(NotValidateTokenBaseAbstractHandler.class);
    
    protected NotValidateTokenBaseAbstractHandler(Class<C> commandType, Class<CR> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    public CR handle(C command)
    {
        String validateMsg = ValidateUtil.validateCommand(command, GroupCommand.class);
        if (validateMsg != null)
        {
            CR result = null;
            try
            {
                result = this.getResultType().newInstance();
                // 入参异常提示
                result.setResultCode(507);
                result.setMessage(validateMsg);
            }
            catch (InstantiationException e)
            {
                logger.error(this.getClass().getName() + "`s handle has error : ", e);
            }
            catch (IllegalAccessException e)
            {
                logger.error(this.getClass().getName() + "`s handle has error : ", e);
            }
            
            return result;
        }
        return businessHandle(command);
    }
    
    protected abstract CR businessHandle(C command);
}
