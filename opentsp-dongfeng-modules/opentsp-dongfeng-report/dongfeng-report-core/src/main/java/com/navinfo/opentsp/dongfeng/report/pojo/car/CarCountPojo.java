package com.navinfo.opentsp.dongfeng.report.pojo.car;

import java.math.BigInteger;

/**
 * @wenya
 * 车次导出时需要的车辆信息
 * @create 2017-04-05 16:22
 **/
public class CarCountPojo {
    private String chassicNum;//底盘号
    private String carNum;//车牌号
    private String carType;//车辆类型
    private String engineType;//发动机类型
    private String structureNum;// 车型结构号
    private BigInteger communicationId;//通信号

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getChassicNum() {
        return chassicNum;
    }

    public void setChassicNum(String chassicNum) {
        this.chassicNum = chassicNum;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }
}
