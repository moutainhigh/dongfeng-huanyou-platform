package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * 出区域限速删除
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-14
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2318_Cmd extends BaseCmd<CommonResult>
{
    private Long areaIdentify;// 区域标识

    public Long getAreaIdentify() {
        return areaIdentify;
    }

    public void setAreaIdentify(Long areaIdentify) {
        this.areaIdentify = areaIdentify;
    }

    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
}
