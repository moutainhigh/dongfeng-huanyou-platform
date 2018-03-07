package com.navinfo.opentsp.dongfeng.monitor.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 出区域限速删除
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-14
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2318_Cmd extends BaseTcpCommand {
    private Long areaIdentify;//区域标识

    public Long getAreaIdentify() {
        return areaIdentify;
    }

    public void setAreaIdentify(Long areaIdentify) {
        this.areaIdentify = areaIdentify;
    }
}
