package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 全国车次导出
 *
 * @wenya
 * @create 2017-04-05 15:25
 **/
public class ExportCarCountCommand extends BaseReportCommand {
    //省市编码 0：全国
    @NotNull(message = "省市编码不能为空", groups = {GroupCommand.class})
    private String cityCode;
    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String time;//查询时间 yyyy年MM月
    //市名称
    private String cityName;
    //省名称
    private String provinceName;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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

    @Override
    public String toString() {
        return "ExportCarCountCommand{" +
                "cityCode='" + cityCode + '\'' +
                ", time='" + time + '\'' +
                ", cityName='" + cityName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
