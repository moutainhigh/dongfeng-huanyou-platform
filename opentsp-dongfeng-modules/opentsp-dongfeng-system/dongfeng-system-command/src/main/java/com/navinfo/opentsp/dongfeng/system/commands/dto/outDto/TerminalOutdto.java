package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalOutdto {
    private BigInteger id;//终端自增id
    private String terminalId; //终端ID
    private String simNo;//sim卡
    private BigInteger communicationId;//终端通讯号
    private Integer type;//终端类型
    private String typeName;//终端类型名称
    private String model;//终端型号
    private String modelName;//终端型号名称
    private Integer protocol;//终端协议
    private String protocolName;//终端协议名称
    private BigInteger reAgent;//所属经销商
    private String reAgentName;//所属经销商名称
    private BigInteger vehicleId;//车辆ID
    private String chassisNo;//所属车辆
    private String carNum;//车牌号
    private String devLabelId;//设备标签ID
    private String remark;//备注
    private String camera;//摄像头
    private Integer mic;//mic
    private BigInteger carOwnerId;//车主ID
    private String carOwnerName;//车主名称
    private String engineType;//发动机类型
    private Integer initEngineType;//终端设置，终端初始化，发动机类型
    private String createTime;//创建时间
    private String createUser;//创建人


    private BigInteger autoCommunicationId;

    private BigInteger orgTeamId;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public BigInteger getReAgent() {
        return reAgent;
    }

    public void setReAgent(BigInteger reAgent) {
        this.reAgent = reAgent;
    }

    public String getReAgentName() {
        return reAgentName;
    }

    public void setReAgentName(String reAgentName) {
        this.reAgentName = reAgentName;
    }

    public BigInteger getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(BigInteger vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getDevLabelId() {
        return devLabelId;
    }

    public void setDevLabelId(String devLabelId) {
        this.devLabelId = devLabelId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public Integer getMic() {
        return mic;
    }

    public void setMic(Integer mic) {
        this.mic = mic;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
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

    public Integer getInitEngineType() {
        return initEngineType;
    }

    public void setInitEngineType(Integer initEngineType) {
        this.initEngineType = initEngineType;
    }

    public BigInteger getOrgTeamId() {
        return orgTeamId;
    }

    public void setOrgTeamId(BigInteger orgTeamId) {
        this.orgTeamId = orgTeamId;
    }

    public BigInteger getAutoCommunicationId() {
        return autoCommunicationId;
    }

    public void setAutoCommunicationId(BigInteger autoCommunicationId) {
        this.autoCommunicationId = autoCommunicationId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
