package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * Created by zhangyu on 2017/3/27.
 */
public class Gps_0103_Cmd extends BaseCmd<CommonResult>
{
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
}
