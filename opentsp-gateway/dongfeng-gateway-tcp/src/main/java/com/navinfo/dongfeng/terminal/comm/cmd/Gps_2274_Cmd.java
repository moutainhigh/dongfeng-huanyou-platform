package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * 实时数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-10-20
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2274_Cmd extends BaseCmd<CommonResult> {
    private int uploadCycle;
    private int collectCycle;
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


