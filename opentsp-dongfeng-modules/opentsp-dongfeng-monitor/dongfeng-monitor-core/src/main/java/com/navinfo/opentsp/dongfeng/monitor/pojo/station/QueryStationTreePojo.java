package com.navinfo.opentsp.dongfeng.monitor.pojo.station;

import com.navinfo.opentsp.dongfeng.monitor.entity.HyServiceStationEntity;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
public class QueryStationTreePojo extends HyServiceStationEntity {
    private String govName;

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }
}
