package com.navinfo.opentsp.dongfeng.report.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Service Station OverTimeAlarms Command
 * @author wt
 * @date 2017-03-28
 * @modify
 * @version 1.0
 */
public class QueryStationOverTimeAlarmsCommand extends BaseReportCommand {
    private String chassisNum;
    private String models;
    private String client;
    private String plateNumber;
    private Long fkControllerID;
    private String dealer;
    private Integer addressCode;
    private String engineType;
    private String stationName;
    private String alarmTime;
    private Long beidouMachineID;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getFkControllerID() {
        return fkControllerID;
    }

    public void setFkControllerID(Long fkControllerID) {
        this.fkControllerID = fkControllerID;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public Integer getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(Integer addressCode) {
        this.addressCode = addressCode;
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

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Long getBeidouMachineID() {
        return beidouMachineID;
    }

    public void setBeidouMachineID(Long beidouMachineID) {
        this.beidouMachineID = beidouMachineID;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationOverTimeAlarmsCommand{" +
                "chassisNum='" + chassisNum + '\'' +
                ", models='" + models + '\'' +
                ", client='" + client + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", fkControllerID=" + fkControllerID +
                ", dealer='" + dealer + '\'' +
                ", addressCode=" + addressCode +
                ", engineType='" + engineType + '\'' +
                ", stationName='" + stationName + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", beidouMachineID=" + beidouMachineID +
                '}';
    }
}