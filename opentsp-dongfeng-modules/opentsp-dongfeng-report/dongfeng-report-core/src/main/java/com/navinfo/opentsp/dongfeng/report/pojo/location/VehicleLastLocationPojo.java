package com.navinfo.opentsp.dongfeng.report.pojo.location;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
public class VehicleLastLocationPojo implements Comparable {

    private BigInteger carId;

    private String plateNum;

    private String chassisNum;

    private String vinNum;

    private Date outDate;

    private String dealerCode;

    private String dealerName;
    /**
     * 终端通信号
     */
    private BigInteger commId;

    private int lastValidLat;

    private int lastValidLon;

    private long gpsTime;

    private float standardMileage;

    private float standardFuelCon;

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

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

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
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

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public int getLastValidLat() {
        return lastValidLat;
    }

    public void setLastValidLat(int lastValidLat) {
        this.lastValidLat = lastValidLat;
    }

    public int getLastValidLon() {
        return lastValidLon;
    }

    public void setLastValidLon(int lastValidLon) {
        this.lastValidLon = lastValidLon;
    }

    public long getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(long gpsTime) {
        this.gpsTime = gpsTime;
    }

    public float getStandardMileage() {
        return standardMileage;
    }

    public void setStandardMileage(float standardMileage) {
        this.standardMileage = standardMileage;
    }

    public float getStandardFuelCon() {
        return standardFuelCon;
    }

    public void setStandardFuelCon(float standardFuelCon) {
        this.standardFuelCon = standardFuelCon;
    }

    @Override
    public String toString() {
        return "VehicleLastLocationPojo{" +
                "carId=" + carId +
                ", plateNum='" + plateNum + '\'' +
                ", chassisNum='" + chassisNum + '\'' +
                ", vinNum='" + vinNum + '\'' +
                ", outDate=" + outDate +
                ", dealerCode='" + dealerCode + '\'' +
                ", dealerName='" + dealerName + '\'' +
                ", commId=" + commId +
                ", lastValidLat=" + lastValidLat +
                ", lastValidLon=" + lastValidLon +
                ", gpsTime=" + gpsTime +
                ", standardMileage=" + standardMileage +
                ", standardFuelCon=" + standardFuelCon +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        VehicleLastLocationPojo vehicle = (VehicleLastLocationPojo) o;
        return (int) (this.getCarId().longValue() - vehicle.getCarId().longValue());
    }
}
