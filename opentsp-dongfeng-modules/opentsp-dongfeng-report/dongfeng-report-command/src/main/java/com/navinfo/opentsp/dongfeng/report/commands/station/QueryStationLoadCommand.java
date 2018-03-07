package com.navinfo.opentsp.dongfeng.report.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Service Station Load Command
 * @author wt
 * @date 2017-03-24
 * @modify
 * @version 1.0
 */
public class QueryStationLoadCommand extends BaseReportCommand {

    private Integer addressCode;

    private String name;

    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String statsDate;

    private Integer stationEnable;

    public Integer getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(Integer addressCode) {
        this.addressCode = addressCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatsDate() {
        return statsDate;
    }

    public void setStatsDate(String statsDate) {
        this.statsDate = statsDate;
    }

    public Integer getStationEnable() {
        return stationEnable;
    }

    public void setStationEnable(Integer stationEnable) {
        this.stationEnable = stationEnable;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationLoadCommand{" +
                "addressCode=" + addressCode +
                ", name='" + name + '\'' +
                ", statsDate='" + statsDate + '\'' +
                ", stationEnable=" + stationEnable +
                '}';
    }
}