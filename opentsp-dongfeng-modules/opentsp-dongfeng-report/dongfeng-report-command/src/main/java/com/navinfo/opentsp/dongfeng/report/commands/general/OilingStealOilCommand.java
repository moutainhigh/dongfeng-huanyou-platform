package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-29
 **/
public class OilingStealOilCommand extends BaseReportCommand implements Serializable {
    private static final long serialVersionUID = 122654480717432255L;

    @NotNull(message = "终端标识 不能为空", groups = {GroupCommand.class})
    private List<Long> vidList;//终端标识
    @NotNull(message = "开始时间 不能为空", groups = {GroupCommand.class})
    private Long startDate;//接口请求时间戳，13位utc时间，精确到秒，不可为空。
    @NotNull(message = "结束时间 不能为空", groups = {GroupCommand.class})
    private Long endDate;
    @NotNull(message = "类型 不能为空", groups = {GroupCommand.class})
    private Integer oilType;

    public List<Long> getVidList() {
        return vidList;
    }

    public void setVidList(List<Long> vidList) {
        this.vidList = vidList;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getOilType() {
        return oilType;
    }

    public void setOilType(Integer oilType) {
        this.oilType = oilType;
    }
}
