package com.navinfo.dongfeng.terminal.comm.model;

import com.googlecode.protobuf.format.JsonFormat;
import com.lc.core.protocol.common.LCLocationData;

/**
 * Created by Administrator on 2016/12/8.
 */
public class LocationDataMq {
    String cid;// 通信号
    String locationDataJson;// 位置数据json

    public LocationDataMq(String cid, LCLocationData.LocationData po)
    {
        this.cid = cid;
        this.locationDataJson = JsonFormat.printToString(po);
    }
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocationDataJson() {
        return locationDataJson;
    }

    public void setLocationDataJson(String locationDataJson) {
        this.locationDataJson = locationDataJson;
    }
}
