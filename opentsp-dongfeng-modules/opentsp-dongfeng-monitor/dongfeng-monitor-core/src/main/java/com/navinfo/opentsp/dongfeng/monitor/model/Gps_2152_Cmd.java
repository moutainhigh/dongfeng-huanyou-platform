package com.navinfo.opentsp.dongfeng.monitor.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-29
 * @modify
 * @copyright Navi Tsp
 */

public class Gps_2152_Cmd extends BaseTcpCommand {

    private String type;// 监控类型

    private String phone;// 电话

    private String logId;// 日志ID

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}