package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.math.BigInteger;

/**
 * Created by ZHANGTIANTONG on 2018/3/5/005.
 */
public class CarTerminalDto {

    /**
     * 车辆ID
     */
    private BigInteger carId;

    /**
     * 车辆车架号
     */
    private String structureNum;

    /**
     * 发动机类型编码
     */
    private String engineCode;

    /**
     * 发动机类型名称
     */
    private String engineName;

    /**
     * 通讯号
     */
    private BigInteger communicationId;

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    @Override
    public String toString() {
        return "CarTerminalDto{" +
                "carId=" + carId +
                ", structureNum='" + structureNum + '\'' +
                ", engineCode='" + engineCode + '\'' +
                ", engineName='" + engineName + '\'' +
                ", communicationId=" + communicationId +
                '}';
    }
}