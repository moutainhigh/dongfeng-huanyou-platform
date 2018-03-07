package com.navinfo.opentsp.dongfeng.common.handler;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 校验token并且重置token存活时间的AbstractHandler
 * 
 * Created by zhangy on 2015/12/31.
 */
public abstract class ValidateTokenAndReSetAbstracHandler<C extends BaseCommand<?>, CR extends CommonResult> extends ValidateTokenAndReSetBaseAbstractHandler<C, CR>
{
    protected static final Logger logger = LoggerFactory.getLogger(ValidateTokenAndReSetAbstracHandler.class);
    
    protected ValidateTokenAndReSetAbstracHandler(Class<C> commandType, Class<CR> resultType)
    {
        super(commandType, resultType);
    }
}
