package com.navinfo.opentsp.dongfeng.system.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * Can数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2273_Cmd extends BaseTcpCommand {
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
}
