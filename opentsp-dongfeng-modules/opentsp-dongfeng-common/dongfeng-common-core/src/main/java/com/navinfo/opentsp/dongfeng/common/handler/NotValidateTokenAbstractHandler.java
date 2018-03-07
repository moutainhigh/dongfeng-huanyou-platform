package com.navinfo.opentsp.dongfeng.common.handler;

import com.navinfo.opentsp.common.messaging.Command;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不需要token校验的AbstractHandler
 * 
 * Created by zhangy on 2015/12/31.
 */
public abstract class NotValidateTokenAbstractHandler<C extends Command<?>, CR extends CommonResult> extends
        NotValidateTokenBaseAbstractHandler<C, CR>
{
    protected static final Logger logger = LoggerFactory.getLogger(NotValidateTokenAbstractHandler.class);
    
    protected NotValidateTokenAbstractHandler(Class<C> commandType, Class<CR> resultType)
    {
        super(commandType, resultType);
    }
}
