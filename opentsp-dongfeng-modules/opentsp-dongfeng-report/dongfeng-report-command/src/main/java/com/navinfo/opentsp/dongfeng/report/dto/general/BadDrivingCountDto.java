package com.navinfo.opentsp.dongfeng.report.dto.general;

/**
 * Created by zhangtiantong on 2018/2/28/028.
 */
public class BadDrivingCountDto {

    /**
     * 车辆VIN码
     */
    private String vin;

    /**
     * 急加速
     */
    private int accelerateCount;

    /**
     * 急减速
     */
    private int decelerateCount;

    /**
     * 急转弯
     */
    private int sharpTurnCount;

    /**
     * 制动里程(米)
     */
    private int brakeMileCount;

    /**
     * 超长怠速
     */
    private int longIdlingCount;

    /**
     * 高档起步
     */
    private int highGearStartCount;

    /**
     * 低档高速
     */
    private int lowGearHighSpeedCount;

    /**
     * 空档滑行
     */
    private int neutralCoastingCount;

    /**
     * 熄火滑行
     */
    private int stallCoastingCount;

    /**
     * 冷启动
     */
    private int coldingStartCount;

    /**
     * 夜间行驶
     */
    private int nightDrivingCount;

    /**
     * 全油门
     */
    private int fullAcceleratorCount;

    /**
     * 大油门
     */
    private int largeAcceleratorCount;

    /**
     * 高速行驶
     */
    private int fastSpeedCount;

    /**
     * 怠速空调
     */
    private int idlingAirConditionCount;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getAccelerateCount() {
        return accelerateCount;
    }

    public void setAccelerateCount(int accelerateCount) {
        this.accelerateCount = accelerateCount;
    }

    public int getDecelerateCount() {
        return decelerateCount;
    }

    public void setDecelerateCount(int decelerateCount) {
        this.decelerateCount = decelerateCount;
    }

    public int getSharpTurnCount() {
        return sharpTurnCount;
    }

    public void setSharpTurnCount(int sharpTurnCount) {
        this.sharpTurnCount = sharpTurnCount;
    }

    public int getBrakeMileCount() {
        return brakeMileCount;
    }

    public void setBrakeMileCount(int brakeMileCount) {
        this.brakeMileCount = brakeMileCount;
    }

    public int getLongIdlingCount() {
        return longIdlingCount;
    }

    public void setLongIdlingCount(int longIdlingCount) {
        this.longIdlingCount = longIdlingCount;
    }

    public int getHighGearStartCount() {
        return highGearStartCount;
    }

    public void setHighGearStartCount(int highGearStartCount) {
        this.highGearStartCount = highGearStartCount;
    }

    public int getLowGearHighSpeedCount() {
        return lowGearHighSpeedCount;
    }

    public void setLowGearHighSpeedCount(int lowGearHighSpeedCount) {
        this.lowGearHighSpeedCount = lowGearHighSpeedCount;
    }

    public int getNeutralCoastingCount() {
        return neutralCoastingCount;
    }

    public void setNeutralCoastingCount(int neutralCoastingCount) {
        this.neutralCoastingCount = neutralCoastingCount;
    }

    public int getStallCoastingCount() {
        return stallCoastingCount;
    }

    public void setStallCoastingCount(int stallCoastingCount) {
        this.stallCoastingCount = stallCoastingCount;
    }

    public int getColdingStartCount() {
        return coldingStartCount;
    }

    public void setColdingStartCount(int coldingStartCount) {
        this.coldingStartCount = coldingStartCount;
    }

    public int getNightDrivingCount() {
        return nightDrivingCount;
    }

    public void setNightDrivingCount(int nightDrivingCount) {
        this.nightDrivingCount = nightDrivingCount;
    }

    public int getFullAcceleratorCount() {
        return fullAcceleratorCount;
    }

    public void setFullAcceleratorCount(int fullAcceleratorCount) {
        this.fullAcceleratorCount = fullAcceleratorCount;
    }

    public int getLargeAcceleratorCount() {
        return largeAcceleratorCount;
    }

    public void setLargeAcceleratorCount(int largeAcceleratorCount) {
        this.largeAcceleratorCount = largeAcceleratorCount;
    }

    public int getFastSpeedCount() {
        return fastSpeedCount;
    }

    public void setFastSpeedCount(int fastSpeedCount) {
        this.fastSpeedCount = fastSpeedCount;
    }

    public int getIdlingAirConditionCount() {
        return idlingAirConditionCount;
    }

    public void setIdlingAirConditionCount(int idlingAirConditionCount) {
        this.idlingAirConditionCount = idlingAirConditionCount;
    }

    @Override
    public String toString() {
        return "BadDrivingCountDto{" +
                "vin='" + vin + '\'' +
                ", accelerateCount=" + accelerateCount +
                ", decelerateCount=" + decelerateCount +
                ", sharpTurnCount=" + sharpTurnCount +
                ", brakeMileCount=" + brakeMileCount +
                ", longIdlingCount=" + longIdlingCount +
                ", highGearStartCount=" + highGearStartCount +
                ", lowGearHighSpeedCount=" + lowGearHighSpeedCount +
                ", neutralCoastingCount=" + neutralCoastingCount +
                ", stallCoastingCount=" + stallCoastingCount +
                ", coldingStartCount=" + coldingStartCount +
                ", nightDrivingCount=" + nightDrivingCount +
                ", fullAcceleratorCount=" + fullAcceleratorCount +
                ", largeAcceleratorCount=" + largeAcceleratorCount +
                ", fastSpeedCount=" + fastSpeedCount +
                ", idlingAirConditionCount=" + idlingAirConditionCount +
                '}';
    }
}