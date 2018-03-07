package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;

/**
 * Created by zhangy on 2017/03/09. 用户登出
 */
public class LogOutCommand extends BaseCommand<CommonResult>
{
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
