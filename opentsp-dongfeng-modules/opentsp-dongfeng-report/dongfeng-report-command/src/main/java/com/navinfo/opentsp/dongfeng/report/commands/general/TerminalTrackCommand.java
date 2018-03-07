package com.navinfo.opentsp.dongfeng.report.commands.general;


import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;

/**
 * Created by Administrator on 2017/8/28.
 */
public class TerminalTrackCommand extends BaseReportCommand {
    //    @Pattern(regexp = "\\d*$", message = "only wirte long")
//    @NotNull(message="vid is not null")
    Long vid;
//    @NotNull(message="beginDate is not null")
    Long beginDate;
//    @NotNull(message="endDate is not null")
    Long endDate;
    //是否用故障码筛选
    boolean isFilterBreakdown;
    //是否抽稀
    boolean isAlgorithm;
    //故障码
    Long breakdownCode = 0L;

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public boolean getIsFilterBreakdown() {
        return isFilterBreakdown;
    }

    public void setIsFilterBreakdown(boolean isFilterBreakdown) {
        this.isFilterBreakdown = isFilterBreakdown;
    }

    public boolean isAlgorithm() {
        return isAlgorithm;
    }

    public void setIsAlgorithm(boolean isAlgorithm) {
        this.isAlgorithm = isAlgorithm;
    }

    public Long getBreakdownCode() {
        return breakdownCode;
    }

    public void setBreakdownCode(Long breakdownCode) {
        this.breakdownCode = breakdownCode;
    }

    /**
     * *******************
     * Default constructor
     *
     */
    public TerminalTrackCommand() {
    }

    @Override
    public String toString() {
        return "TerminalTrackCommand{" +
                "vid=" + vid +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", isFilterBreakdown=" + isFilterBreakdown +
                ", isAlgorithm=" + isAlgorithm +
                ", breakdownCode=" + breakdownCode +
                '}';
    }
}
