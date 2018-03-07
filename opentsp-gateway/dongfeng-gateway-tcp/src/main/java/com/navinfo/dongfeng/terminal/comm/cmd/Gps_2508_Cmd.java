package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * 进出区域通知设置删除
 *
 * @author zhangyu
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2508_Cmd extends BaseCmd<CommonResult> {
    private static final long serialVersionUID = -6088138430887189071L;

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}
