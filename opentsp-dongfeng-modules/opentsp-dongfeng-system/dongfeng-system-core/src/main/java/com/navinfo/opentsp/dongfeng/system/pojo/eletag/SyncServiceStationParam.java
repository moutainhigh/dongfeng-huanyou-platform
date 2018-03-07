package com.navinfo.opentsp.dongfeng.system.pojo.eletag;

import java.util.List;

/**
 * 服务站同步到标签库参数
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-06
 **/
public class SyncServiceStationParam {
    private List<SyncStationPojo> stationList;
    private List<SyncSecStationPojo> secStationList;

    public List<SyncStationPojo> getStationList() {
        return stationList;
    }

    public void setStationList(List<SyncStationPojo> stationList) {
        this.stationList = stationList;
    }

    public List<SyncSecStationPojo> getSecStationList() {
        return secStationList;
    }

    public void setSecStationList(List<SyncSecStationPojo> secStationList) {
        this.secStationList = secStationList;
    }
}
