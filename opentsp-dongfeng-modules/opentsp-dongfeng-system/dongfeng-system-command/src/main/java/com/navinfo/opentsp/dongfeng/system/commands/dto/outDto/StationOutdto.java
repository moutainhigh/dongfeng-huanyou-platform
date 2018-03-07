package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;
import java.util.List;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-24
 * @modify
 * @copyright Navi Tsp
 */
public class StationOutdto {
    private BigInteger stationId;
    private StationBasicInfoOutdto basicInfo;
    private List<StationServiceItemOutdto> serviceItem;
    private List<SubStationOutdto> subStation;

    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }

    public StationBasicInfoOutdto getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(StationBasicInfoOutdto basicInfo) {
        this.basicInfo = basicInfo;
    }

    public List<StationServiceItemOutdto> getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(List<StationServiceItemOutdto> serviceItem) {
        this.serviceItem = serviceItem;
    }

    public List<SubStationOutdto> getSubStation() {
        return subStation;
    }

    public void setSubStation(List<SubStationOutdto> subStation) {
        this.subStation = subStation;
    }
}
