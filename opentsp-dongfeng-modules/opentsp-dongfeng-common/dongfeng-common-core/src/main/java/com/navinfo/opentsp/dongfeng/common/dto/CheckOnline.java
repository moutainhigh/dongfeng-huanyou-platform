package com.navinfo.opentsp.dongfeng.common.dto;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;

import java.io.Serializable;

public class CheckOnline extends BaseCommand<CommonResult> implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
