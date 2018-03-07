package com.navinfo.opentsp.dongfeng.system.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 搜索服务站
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class QueryStationCommand extends BaseCommand<HttpCommandResultWithData> {
    private String stationName;//服务站名称
    private String stationCode;//服务站编码
    private String provinceCode;//所属省市
    private String switchStatus;//启用停用标志
    private String tagSwitchStatus;//标签库同步状态
    private String tBossSwitchStatus;//tboss同步状态

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getTagSwitchStatus() {
        return tagSwitchStatus;
    }

    public void setTagSwitchStatus(String tagSwitchStatus) {
        this.tagSwitchStatus = tagSwitchStatus;
    }

    public String gettBossSwitchStatus() {
        return tBossSwitchStatus;
    }

    public void settBossSwitchStatus(String tBossSwitchStatus) {
        this.tBossSwitchStatus = tBossSwitchStatus;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationCommand{" +
                "stationName='" + stationName + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", switchStatus='" + switchStatus + '\'' +
                ", tagSwitchStatus='" + tagSwitchStatus + '\'' +
                ", tBossSwitchStatus='" + tBossSwitchStatus + '\'' +
                '}';
    }
}