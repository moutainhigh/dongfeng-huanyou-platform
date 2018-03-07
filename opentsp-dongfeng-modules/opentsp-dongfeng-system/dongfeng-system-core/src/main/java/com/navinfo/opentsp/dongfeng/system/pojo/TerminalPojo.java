package com.navinfo.opentsp.dongfeng.system.pojo;

import com.navinfo.opentsp.dongfeng.system.entity.HyTerminalEntity;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalPojo extends HyTerminalEntity {
    private static final long serialVersionUID = -5927970227057430151L;
    private String chassisNum;//所属车辆
    private String protocol;//终端协议类型
    private BigInteger carId;//车辆ID
    private String carNum;//车牌号
    private BigInteger communicationId; //车辆有效的终端通讯号
    private String modelName;//型号名称
    private String typeName;//终端类型名称
    private BigInteger carOwnerId;//车主ID
    private String carOwnerName;//车主名称
    private String teamName;//所属经销商名称
    private String engineType;//发动机类型
    private String createTime;//创建时间
    private String createUserName;//创建人


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigInteger getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(BigInteger carOwnerId) {
        this.carOwnerId = carOwnerId;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
