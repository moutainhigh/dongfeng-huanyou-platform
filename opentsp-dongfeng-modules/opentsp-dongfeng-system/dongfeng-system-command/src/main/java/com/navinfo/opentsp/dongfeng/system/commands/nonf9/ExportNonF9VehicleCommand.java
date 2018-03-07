package com.navinfo.opentsp.dongfeng.system.commands.nonf9;

/**
 * 导出非F9车辆
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-11-28
 * @modify
 * @copyright Navi Tsp
 */
public class ExportNonF9VehicleCommand extends QueryNonF9VehicleCommand {
    /**
     * 导出列数据
     */
    private String metadata;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
