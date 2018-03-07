package com.navinfo.opentsp.dongfeng.system.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-23
 **/
public class Gps_2267_Cmd extends BaseTcpCommand {
    /**
     * CAN通道
     */
    private Integer passages;
    /**
     * 采集时间间隔，单位毫秒
     */
    private Integer timingInterval;
    /**
     * 上传时间间隔，单位秒
     */
    private Integer uploadInterval;

    public Integer getPassages() {
        return passages;
    }

    public void setPassages(Integer passages) {
        this.passages = passages;
    }

    public Integer getTimingInterval() {
        return timingInterval;
    }

    public void setTimingInterval(Integer timingInterval) {
        this.timingInterval = timingInterval;
    }

    public Integer getUploadInterval() {
        return uploadInterval;
    }

    public void setUploadInterval(Integer uploadInterval) {
        this.uploadInterval = uploadInterval;
    }
}
