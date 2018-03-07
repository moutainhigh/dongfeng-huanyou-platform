package com.navinfo.opentsp.dongfeng.report.dto.location;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
public class VehicleLastLocationDto {

    /**
     * 车牌号
     */
    private String plateNum;

    /**
     * 底盘号
     */
    private String chassisNum;

    /**
     * VIN号
     */
    private String vinNum;

    /**
     * 出库日期
     */
    private String outDate;

    /**
     * 经销商编码
     */
    private String dealerCode;

    /**
     * 经销商名称
     */
    private String dealerName;

    /**
     * 末次有效位置维度
     */
    private String lastValidLat = "0.0";

    /**
     * 末次有效位置精度
     */
    private String lastValidLon = "0.0";

    /**
     * 末次有效位置时间
     */
    private String gpsTime;

    /**
     * 标准里程
     */
    private String standardMileage;

    /**
     * 标准油耗
     */
    private String standardFuelCon;


    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getVinNum() {
        return vinNum;
    }

    public void setVinNum(String vinNum) {
        this.vinNum = vinNum;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getLastValidLat() {
        return lastValidLat;
    }

    public void setLastValidLat(String lastValidLat) {
        this.lastValidLat = lastValidLat;
    }

    public String getLastValidLon() {
        return lastValidLon;
    }

    public void setLastValidLon(String lastValidLon) {
        this.lastValidLon = lastValidLon;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getStandardMileage() {
        return standardMileage;
    }

    public void setStandardMileage(String standardMileage) {
        this.standardMileage = standardMileage;
    }

    public String getStandardFuelCon() {
        return standardFuelCon;
    }

    public void setStandardFuelCon(String standardFuelCon) {
        this.standardFuelCon = standardFuelCon;
    }
}
