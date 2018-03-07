package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * 终端初始化参数设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class InitParam extends BaseTerminalParam {
    private Integer engineType;// 发动机类型
    private Integer vehType;// 适用车辆类型

    public Integer getEngineType() {
        return engineType;
    }

    public void setEngineType(Integer engineType) {
        this.engineType = engineType;
    }

    public Integer getVehType() {
        return vehType;
    }

    public void setVehType(Integer vehType) {
        this.vehType = vehType;
    }
}
