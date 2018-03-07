package com.navinfo.opentsp.dongfeng.kafka.entity;

/**
 * Created by cj on 2017/9/30.
 */
public class VehicleOilExistEntity {

    private long vid;
    private long gpsDate;
    private long longitude;
    private long latitude;
    private long gpsSpeed;
    private long mileage;
    private long oilPercentage; // 百分比，已被乘以100
    private long maxVolume;     // 单位：升
    private long oilValue;      // 单位：升，已被乘以100

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public long getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(long gpsDate) {
        this.gpsDate = gpsDate;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(long gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public long getOilPercentage() {
        return oilPercentage;
    }

    public void setOilPercentage(long oilPercentage) {
        this.oilPercentage = oilPercentage;
    }

    public long getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(long maxVolume) {
        this.maxVolume = maxVolume;
    }

    public long getOilValue() {
        return oilValue;
    }

    public void setOilValue(long oilValue) {
        this.oilValue = oilValue;
    }


    @Override
    public String toString() {
        return "VehicleOilExistEntity{" +
                "vid=" + vid +
                ", gpsDate=" + gpsDate +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", gpsSpeed=" + gpsSpeed +
                ", mileage=" + mileage +
                ", oilPercentage=" + oilPercentage +
                ", maxVolume=" + maxVolume +
                ", oilValue=" + oilValue +
                '}';
    }
}
