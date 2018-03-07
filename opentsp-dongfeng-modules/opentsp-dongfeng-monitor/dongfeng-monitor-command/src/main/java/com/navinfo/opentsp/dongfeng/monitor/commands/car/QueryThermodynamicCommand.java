package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 地图显示热力图
 *
 * @wenya
 * @create 2017-04-12 11:37
 **/
public class QueryThermodynamicCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "地图中心点纬度不能为空", groups = {GroupCommand.class})
    private String heatMaplatitude;
    @NotNull(message = "地图半径不能为空", groups = {GroupCommand.class})
    private String heatMapradius ;
    @NotNull(message = "地图中心点经度不能为空", groups = {GroupCommand.class})
    private String heatMaplongitude ;
    @NotNull(message = "时间不能为空", groups = {GroupCommand.class})
    private String time;

    public String getHeatMaplatitude() {
        return heatMaplatitude;
    }

    public void setHeatMaplatitude(String heatMaplatitude) {
        this.heatMaplatitude = heatMaplatitude;
    }

    public String getHeatMaplongitude() {
        return heatMaplongitude;
    }

    public void setHeatMaplongitude(String heatMaplongitude) {
        this.heatMaplongitude = heatMaplongitude;
    }

    public String getHeatMapradius() {
        return heatMapradius;
    }

    public void setHeatMapradius(String heatMapradius) {
        this.heatMapradius = heatMapradius;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
