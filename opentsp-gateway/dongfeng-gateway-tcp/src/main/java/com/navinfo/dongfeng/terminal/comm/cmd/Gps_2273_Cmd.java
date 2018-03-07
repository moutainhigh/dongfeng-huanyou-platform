package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * Can数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2273_Cmd extends BaseCmd<CommonResult> {
    private static final long serialVersionUID = -1330169582751149100L;

    private int engineType;

    private int vehType;


    public int getEngineType() {
        return engineType;
    }

    public void setEngineType(int engineType) {
        this.engineType = engineType;
    }

    public int getVehType() {
        return vehType;
    }

    public void setVehType(int vehType) {
        this.vehType = vehType;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}
