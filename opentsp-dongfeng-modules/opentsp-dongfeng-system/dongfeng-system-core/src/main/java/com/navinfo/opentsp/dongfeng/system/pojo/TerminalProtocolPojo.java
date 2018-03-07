package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-11
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalProtocolPojo {
    private BigInteger typeId; //类型ID
    private String typeName;//终端型号
    private BigInteger producerId;//制造商ID
    private Integer travelVersion;//行驶记录仪版本
    private Integer protocolVersion;//协议版本
    private Integer gpsModule;//GPS模块

    public BigInteger getTypeId() {
        return typeId;
    }

    public void setTypeId(BigInteger typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigInteger getProducerId() {
        return producerId;
    }

    public void setProducerId(BigInteger producerId) {
        this.producerId = producerId;
    }

    public Integer getTravelVersion() {
        return travelVersion;
    }

    public void setTravelVersion(Integer travelVersion) {
        this.travelVersion = travelVersion;
    }

    public Integer getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Integer getGpsModule() {
        return gpsModule;
    }

    public void setGpsModule(Integer gpsModule) {
        this.gpsModule = gpsModule;
    }
}
