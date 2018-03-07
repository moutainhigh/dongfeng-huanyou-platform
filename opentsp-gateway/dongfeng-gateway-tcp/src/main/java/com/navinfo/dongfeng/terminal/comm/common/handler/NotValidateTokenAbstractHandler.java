package com.navinfo.dongfeng.terminal.comm.common.handler;

import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import messaging.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 不需要token校验的AbstractHandler
 * 
 * Created by zhangy on 2015/12/31.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public abstract class NotValidateTokenAbstractHandler<C extends Command<?>, CR extends CommonResult> extends
        NotValidateTokenBaseAbstractHandler<C, CR>
{
    protected static final Logger logger = LoggerFactory.getLogger(NotValidateTokenAbstractHandler.class);
    
    protected NotValidateTokenAbstractHandler(Class<C> commandType, Class<CR> resultType)
    {
        super(commandType, resultType);
    }
}
