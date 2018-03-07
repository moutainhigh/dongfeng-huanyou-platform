package com.navinfo.opentsp.dongfeng.common.handler;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 校验token并且不重置token存活时间的AbstractHandler
 * 
 * Created by zhangy on 2015/12/31.
 */
public abstract class ValidateTokenAndNoReSetAbstracHandler<C extends BaseCommand<?>, CR extends CommonResult> extends
        ValidateTokenAndNoReSetBaseAbstractHandler<C, CR>
{
    protected static final Logger logger = LoggerFactory.getLogger(ValidateTokenAndNoReSetAbstracHandler.class);
    
    protected ValidateTokenAndNoReSetAbstracHandler(Class<C> commandType, Class<CR> resultType)
    {
        super(commandType, resultType);
    }
}
