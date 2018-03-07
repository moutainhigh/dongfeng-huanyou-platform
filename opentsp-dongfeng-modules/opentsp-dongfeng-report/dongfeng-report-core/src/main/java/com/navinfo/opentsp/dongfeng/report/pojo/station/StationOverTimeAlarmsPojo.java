package com.navinfo.opentsp.dongfeng.report.pojo.station;


import java.math.BigInteger;

public class StationOverTimeAlarmsPojo {
    private BigInteger stationId; //服务站ID---获取位置数据使用

    private BigInteger carId;
    private String chassisNum;
    private String plateNumber;
    private BigInteger beidouMachineID;
    private BigInteger beidouCommunicationId;
    private BigInteger fkControllerID;
    private BigInteger fkCommunicationId;
    private String dealer;
    private String client;
    private String contacts;
    private String carModel;
    private String engineModel;
    private String engineType;
    private String stationName;
    private String stationAddress;
    private Long inboundTime ;
    private Long lastAlarmTime ;
    private String parkingTimes;
    private String parkingThreshold ;
    private Byte   isOvertime;
    private String parkId;  //滞留Id:忽略滞留超时使用

    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public Long getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Long inboundTime) {
        this.inboundTime = inboundTime;
    }

    public Long getLastAlarmTime() {
        return lastAlarmTime;
    }

    public void setLastAlarmTime(Long lastAlarmTime) {
        this.lastAlarmTime = lastAlarmTime;
    }

    public String getParkingTimes() {
        return parkingTimes;
    }

    public void setParkingTimes(String parkingTimes) {
        this.parkingTimes = parkingTimes;
    }

    public String getParkingThreshold() {
        return parkingThreshold;
    }

    public void setParkingThreshold(String parkingThreshold) {
        this.parkingThreshold = parkingThreshold;
    }

    public Byte getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(Byte isOvertime) {
        this.isOvertime = isOvertime;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    @Override
    public String toString() {
        return "StationOverTimeAlarmsPojo{" +
                "stationId=" + stationId +
                ", carId=" + carId +
                ", chassisNum='" + chassisNum + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", beidouMachineID=" + beidouMachineID +
                ", beidouCommunicationId=" + beidouCommunicationId +
                ", fkControllerID=" + fkControllerID +
                ", fkCommunicationId=" + fkCommunicationId +
                ", dealer='" + dealer + '\'' +
                ", client='" + client + '\'' +
                ", contacts='" + contacts + '\'' +
                ", carModel='" + carModel + '\'' +
                ", engineModel='" + engineModel + '\'' +
                ", engineType='" + engineType + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationAddress='" + stationAddress + '\'' +
                ", inboundTime=" + inboundTime +
                ", lastAlarmTime=" + lastAlarmTime +
                ", parkingTimes='" + parkingTimes + '\'' +
                ", parkingThreshold='" + parkingThreshold + '\'' +
                ", isOvertime=" + isOvertime +
                ", parkId='" + parkId + '\'' +
                '}';
    }
}
