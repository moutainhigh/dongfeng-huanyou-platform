package com.navinfo.opentsp.dongfeng.system.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 实时数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-10-20
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2274_Cmd extends BaseTcpCommand {
    /**
     * 上传周期，为0表示不上传，单位秒
     */
    private int uploadCycle;
    /**
     * 采集周期，单位毫秒
     */
    private int collectCycle;
    /**
     * 采集时长，单位秒，0xFFFF代表一直采集
     */
    private int collectionTime;

    public int getUploadCycle() {
        return uploadCycle;
    }

    public void setUploadCycle(int uploadCycle) {
        this.uploadCycle = uploadCycle;
    }

    public int getCollectCycle() {
        return collectCycle;
    }

    public void setCollectCycle(int collectCycle) {
        this.collectCycle = collectCycle;
    }

    public int getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(int collectionTime) {
        this.collectionTime = collectionTime;
    }
}


