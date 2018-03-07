package com.navinfo.opentsp.dongfeng.kafka.pojo;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 8103终端设置指令
 *
 * @wenya
 * @create 2017-03-29 14:47
 **/
public class Gps_2271_Cmd extends BaseTcpCommand {
    private String deviceId;
    private String baseCode;
    private String logId;//日志id
    private Integer statue;//在线状态

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }
}
