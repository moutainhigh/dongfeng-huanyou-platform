package com.navinfo.opentsp.dongfeng.report.dto.station;


public class QueryStationOverTimeAlarmsDto implements Comparable<QueryStationOverTimeAlarmsDto>{
    private String chassisNum;
    private String plateNumber;
    private String beidouMachineID;
    private String fkControllerID;
    private String dealer;
    private String client;
    private String carModel;
    private String engineModel;
    private String engineType;
    private String stationName;
    private String stationAddress;
    private String contacts;
    private String inboundTime ;
    private String lastAlarmTime ;
    private String parkingTimes;
    private String parkingThreshold ;
    private String isOvertime;
    private String parkId;  //滞留Id:忽略滞留超时使用

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

    public String getBeidouMachineID() {
        return beidouMachineID;
    }

    public void setBeidouMachineID(String beidouMachineID) {
        this.beidouMachineID = beidouMachineID;
    }

    public String getFkControllerID() {
        return fkControllerID;
    }

    public void setFkControllerID(String fkControllerID) {
        this.fkControllerID = fkControllerID;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(String inboundTime) {
        this.inboundTime = inboundTime;
    }

    public String getLastAlarmTime() {
        return lastAlarmTime;
    }

    public void setLastAlarmTime(String lastAlarmTime) {
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

    public String getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(String isOvertime) {
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
        return "QueryStationOverTimeAlarmsDto{" +
                "chassisNum='" + chassisNum + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", beidouMachineID='" + beidouMachineID + '\'' +
                ", fkControllerID='" + fkControllerID + '\'' +
                ", dealer='" + dealer + '\'' +
                ", client='" + client + '\'' +
                ", carModel='" + carModel + '\'' +
                ", engineModel='" + engineModel + '\'' +
                ", engineType='" + engineType + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationAddress='" + stationAddress + '\'' +
                ", contacts='" + contacts + '\'' +
                ", inboundTime='" + inboundTime + '\'' +
                ", lastAlarmTime='" + lastAlarmTime + '\'' +
                ", parkingTimes='" + parkingTimes + '\'' +
                ", parkingThreshold='" + parkingThreshold + '\'' +
                ", isOvertime='" + isOvertime + '\'' +
                ", parkId='" + parkId + '\'' +
                '}';
    }

    @Override
    public int compareTo(QueryStationOverTimeAlarmsDto obj) {
        return this.getChassisNum().compareTo(obj.getChassisNum());
    }


}
