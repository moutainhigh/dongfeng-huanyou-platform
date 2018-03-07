package com.navinfo.opentsp.dongfeng.openapi.dto.bigdata;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/29
 * @modify
 * @copyright
 */
public class VehicleHistoryCountDto {
    /**
     * 历史累计生产(下线)
     */
    private long counts;
    /**
     * 当年累计生产(下线)
     */
    private long yearCounts;
    /**
     * 当月累计生产(下线)
     */
    private long monthCounts;
    /**
     * 当日下线
     */
    private long dayOffLineCounts;
    /**
     * 当日入库
     */
    private long dayStorageCounts;

    public long getCounts() {
        return counts;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }

    public long getYearCounts() {
        return yearCounts;
    }

    public void setYearCounts(long yearCounts) {
        this.yearCounts = yearCounts;
    }

    public long getMonthCounts() {
        return monthCounts;
    }

    public void setMonthCounts(long monthCounts) {
        this.monthCounts = monthCounts;
    }

    public long getDayOffLineCounts() {
        return dayOffLineCounts;
    }

    public void setDayOffLineCounts(long dayOffLineCounts) {
        this.dayOffLineCounts = dayOffLineCounts;
    }

    public long getDayStorageCounts() {
        return dayStorageCounts;
    }

    public void setDayStorageCounts(long dayStorageCounts) {
        this.dayStorageCounts = dayStorageCounts;
    }
}
