package com.navinfo.opentsp.dongfeng.system.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.ParameterType;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationAuditIndto;

import java.util.ArrayList;


public class UpdateStationAuditStatusCommand extends BaseCommand<CommonResult> {

    @Converter(target = "stationAuditBean", type = ParameterType.LIST)
    protected String stationAuditInfo;

    protected ArrayList<StationAuditIndto> stationAuditBean;

    public String getStationAuditInfo() {
        return stationAuditInfo;
    }

    public void setStationAuditInfo(String stationAuditInfo) {
        this.stationAuditInfo = stationAuditInfo;
    }

    public ArrayList<StationAuditIndto> getStationAuditBean() {
        return stationAuditBean;
    }

    public void setStationAuditBean(ArrayList<StationAuditIndto> stationAuditBean) {
        this.stationAuditBean = stationAuditBean;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}