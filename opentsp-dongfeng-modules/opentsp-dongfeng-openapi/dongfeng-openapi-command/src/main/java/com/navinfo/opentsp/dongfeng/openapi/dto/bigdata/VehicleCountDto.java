package com.navinfo.opentsp.dongfeng.openapi.dto.bigdata;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/29
 * @modify
 * @copyright
 */
public class VehicleCountDto {
    /**
     * 车辆总数
     */
    private long counts;
    /**
     * 实时行驶在线车辆总数
     */
    private long onLineCounts;
    /**
     * 当日累计行驶里程数
     */
    private long mileages;
    /**
     * 当日累计上报位置数
     */
    private long locations;

    public long getCounts() {
        return counts;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }

    public long getOnLineCounts() {
        return onLineCounts;
    }

    public void setOnLineCounts(long onLineCounts) {
        this.onLineCounts = onLineCounts;
    }

    public long getMileages() {
        return mileages;
    }

    public void setMileages(long mileages) {
        this.mileages = mileages;
    }

    public long getLocations() {
        return locations;
    }

    public void setLocations(long locations) {
        this.locations = locations;
    }
}
