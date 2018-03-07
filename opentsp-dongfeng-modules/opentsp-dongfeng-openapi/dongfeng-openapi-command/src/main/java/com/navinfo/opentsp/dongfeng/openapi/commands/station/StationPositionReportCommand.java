package com.navinfo.opentsp.dongfeng.openapi.commands.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import com.navinfo.opentsp.dongfeng.openapi.dto.station.StationPositionReportInfo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class StationPositionReportCommand extends BaseOpenApiCommand {

    @NotNull(message = "param 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "param 不能为空", groups = {GroupCommand.class})
    @Converter(target = "stationPositionReportInfo")
    private String param;

    private StationPositionReportInfo stationPositionReportInfo;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public StationPositionReportInfo getStationPositionReportInfo() {
        return stationPositionReportInfo;
    }

    public void setStationPositionReportInfo(StationPositionReportInfo stationPositionReportInfo) {
        this.stationPositionReportInfo = stationPositionReportInfo;
    }

    @Override
    public String toString() {
        return "StationPositionReportCommand{" +
                "param=" + param +
                '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

}
