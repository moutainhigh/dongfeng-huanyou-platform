package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
public class DealerVehicleDetailPojo {
    // 底盘号
    private String chassisNum;
    // 车牌号
    private String plateNum;
    // 车辆类型（数据字典）
    private String carType;
    // 发动机类型
    private String engineType;
    //累计里程(千米)
    private String countMilleage;
    //发动机累计运行(秒)
    private String cumulativeRunningTime;
    // DMS销售时间
    private BigInteger dmsSaleDate;
    // 入网时间
    private BigInteger accessDate;
    // 末次位置时间
    private Long lastLocationDate;
    // 末次位置-经度
    private Double lastLon;
    // 末次位置-纬度
    private Double lastLat;
    //离库位置

    //防控状态
    private Integer lockStauts;

    private BigInteger beidouMachineID;
    private BigInteger beidouCommunicationId;
    private BigInteger fkControllerID;
    private BigInteger fkCommunicationId;

    //方向
    private Integer direction;
    //在线状态
    private Integer carStatus;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCountMilleage() {
        return countMilleage;
    }

    public void setCountMilleage(String countMilleage) {
        this.countMilleage = countMilleage;
    }

    public String getCumulativeRunningTime() {
        return cumulativeRunningTime;
    }

    public void setCumulativeRunningTime(String cumulativeRunningTime) {
        this.cumulativeRunningTime = cumulativeRunningTime;
    }

    public BigInteger getDmsSaleDate() {
        return dmsSaleDate;
    }

    public void setDmsSaleDate(BigInteger dmsSaleDate) {
        this.dmsSaleDate = dmsSaleDate;
    }

    public BigInteger getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(BigInteger accessDate) {
        this.accessDate = accessDate;
    }

    public Long getLastLocationDate() {
        return lastLocationDate;
    }

    public void setLastLocationDate(Long lastLocationDate) {
        this.lastLocationDate = lastLocationDate;
    }

    public Double getLastLon() {
        return lastLon;
    }

    public void setLastLon(Double lastLon) {
        this.lastLon = lastLon;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    public Integer getLockStauts() {
        return lockStauts;
    }

    public void setLockStauts(Integer lockStauts) {
        this.lockStauts = lockStauts;
    }

    public BigInteger getBeidouMachineID() {
        return beidouMachineID;
    }

    public void setBeidouMachineID(BigInteger beidouMachineID) {
        this.beidouMachineID = beidouMachineID;
    }

    public BigInteger getBeidouCommunicationId() {
        return beidouCommunicationId;
    }

    public void setBeidouCommunicationId(BigInteger beidouCommunicationId) {
        this.beidouCommunicationId = beidouCommunicationId;
    }

    public BigInteger getFkControllerID() {
        return fkControllerID;
    }

    public void setFkControllerID(BigInteger fkControllerID) {
        this.fkControllerID = fkControllerID;
    }

    public BigInteger getFkCommunicationId() {
        return fkCommunicationId;
    }

    public void setFkCommunicationId(BigInteger fkCommunicationId) {
        this.fkCommunicationId = fkCommunicationId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "DealerVehicleDetailPojo{" +
                "chassisNum='" + chassisNum + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", carType='" + carType + '\'' +
                ", engineType='" + engineType + '\'' +
                ", countMilleage='" + countMilleage + '\'' +
                ", cumulativeRunningTime='" + cumulativeRunningTime + '\'' +
                ", dmsSaleDate=" + dmsSaleDate +
                ", accessDate=" + accessDate +
                ", lastLocationDate=" + lastLocationDate +
                ", lastLon=" + lastLon +
                ", lastLat=" + lastLat +
                ", lockStauts=" + lockStauts +
                ", beidouMachineID=" + beidouMachineID +
                ", beidouCommunicationId=" + beidouCommunicationId +
                ", fkControllerID=" + fkControllerID +
                ", fkCommunicationId=" + fkCommunicationId +
                ", direction=" + direction +
                ", carStatus=" + carStatus +
                '}';
    }
}
