package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.math.BigInteger;

/**
 * Created by zhangtiantong on 2018/3/2/002.
 */
public class BadDrivingMongoDBDto {

    /**
     * 通讯号
     */
    private BigInteger tId;

    /**
     * 日期
     */
    private String day;

    /**
     * gps时间
     */
    private long gpsTime;

    /**
     * 纬度
     */
    private long latitude;

    /**
     * 经度
     */
    private long longitude;

    /**
     * 方向
     */
    private int direction;

    /**
     * 急加速
     */
    private int accelerate;

    /**
     * 急减速
     */
    private int decelerate;

    /**
     * 急转弯
     */
    private int sharpTurn;

    /**
     * 制动里程(米)
     */
    private int brakeMile;

    /**
     * 超长怠速
     */
    private int longIdling;

    /**
     * 高档起步
     */
    private int highGearStart;

    /**
     * 低档高速
     */
    private int lowGearHighSpeed;

    /**
     * 空档滑行
     */
    private int neutralCoasting;

    /**
     * 熄火滑行
     */
    private int stallCoasting;

    /**
     * 冷启动
     */
    private int coldingStart;

    /**
     * 夜间行驶
     */
    private int nightDriving;

    /**
     * 全油门
     */
    private int fullAccelerator;

    /**
     * 大油门
     */
    private int largeAccelerator;

    /**
     * 高速行驶
     */
    private int fastSpeed;

    /**
     * 怠速空调
     */
    private int idlingAirCondition;

    public BigInteger gettId() {
        return tId;
    }

    public void settId(BigInteger tId) {
        this.tId = tId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(long gpsTime) {
        this.gpsTime = gpsTime;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getAccelerate() {
        return accelerate;
    }

    public void setAccelerate(int accelerate) {
        this.accelerate = accelerate;
    }

    public int getDecelerate() {
        return decelerate;
    }

    public void setDecelerate(int decelerate) {
        this.decelerate = decelerate;
    }

    public int getSharpTurn() {
        return sharpTurn;
    }

    public void setSharpTurn(int sharpTurn) {
        this.sharpTurn = sharpTurn;
    }

    public int getBrakeMile() {
        return brakeMile;
    }

    public void setBrakeMile(int brakeMile) {
        this.brakeMile = brakeMile;
    }

    public int getLongIdling() {
        return longIdling;
    }

    public void setLongIdling(int longIdling) {
        this.longIdling = longIdling;
    }

    public int getHighGearStart() {
        return highGearStart;
    }

    public void setHighGearStart(int highGearStart) {
        this.highGearStart = highGearStart;
    }

    public int getLowGearHighSpeed() {
        return lowGearHighSpeed;
    }

    public void setLowGearHighSpeed(int lowGearHighSpeed) {
        this.lowGearHighSpeed = lowGearHighSpeed;
    }

    public int getNeutralCoasting() {
        return neutralCoasting;
    }

    public void setNeutralCoasting(int neutralCoasting) {
        this.neutralCoasting = neutralCoasting;
    }

    public int getStallCoasting() {
        return stallCoasting;
    }

    public void setStallCoasting(int stallCoasting) {
        this.stallCoasting = stallCoasting;
    }

    public int getColdingStart() {
        return coldingStart;
    }

    public void setColdingStart(int coldingStart) {
        this.coldingStart = coldingStart;
    }

    public int getNightDriving() {
        return nightDriving;
    }

    public void setNightDriving(int nightDriving) {
        this.nightDriving = nightDriving;
    }

    public int getFullAccelerator() {
        return fullAccelerator;
    }

    public void setFullAccelerator(int fullAccelerator) {
        this.fullAccelerator = fullAccelerator;
    }

    public int getLargeAccelerator() {
        return largeAccelerator;
    }

    public void setLargeAccelerator(int largeAccelerator) {
        this.largeAccelerator = largeAccelerator;
    }

    public int getFastSpeed() {
        return fastSpeed;
    }

    public void setFastSpeed(int fastSpeed) {
        this.fastSpeed = fastSpeed;
    }

    public int getIdlingAirCondition() {
        return idlingAirCondition;
    }

    public void setIdlingAirCondition(int idlingAirCondition) {
        this.idlingAirCondition = idlingAirCondition;
    }

    @Override
    public String toString() {
        return "BadDrivingMongoDBDto{" +
                "tId=" + tId +
                ", day='" + day + '\'' +
                ", gpsTime=" + gpsTime +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", direction=" + direction +
                ", accelerate=" + accelerate +
                ", decelerate=" + decelerate +
                ", sharpTurn=" + sharpTurn +
                ", brakeMile=" + brakeMile +
                ", longIdling=" + longIdling +
                ", highGearStart=" + highGearStart +
                ", lowGearHighSpeed=" + lowGearHighSpeed +
                ", neutralCoasting=" + neutralCoasting +
                ", stallCoasting=" + stallCoasting +
                ", coldingStart=" + coldingStart +
                ", nightDriving=" + nightDriving +
                ", fullAccelerator=" + fullAccelerator +
                ", largeAccelerator=" + largeAccelerator +
                ", fastSpeed=" + fastSpeed +
                ", idlingAirCondition=" + idlingAirCondition +
                '}';
    }
}