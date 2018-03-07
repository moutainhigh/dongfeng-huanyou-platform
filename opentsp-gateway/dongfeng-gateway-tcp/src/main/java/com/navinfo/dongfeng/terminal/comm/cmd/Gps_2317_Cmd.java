package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-13
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2317_Cmd extends BaseCmd<CommonResult>
{
    private static final long serialVersionUID = 3899589571787541975L;
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
