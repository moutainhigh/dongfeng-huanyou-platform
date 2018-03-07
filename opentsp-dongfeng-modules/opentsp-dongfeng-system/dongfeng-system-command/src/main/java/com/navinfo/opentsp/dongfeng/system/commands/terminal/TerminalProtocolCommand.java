package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 终端协议
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-11
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalProtocolCommand extends BaseCommand<HttpCommandResultWithData> {
    private String typeName;//终端型号
    private String producerId;//制造商ID
    private String travelVersion;//行驶记录仪版本
    private String protocolVersion;//协议版本
    private String gpsModule;//GPS模块

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getTravelVersion() {
        return travelVersion;
    }

    public void setTravelVersion(String travelVersion) {
        this.travelVersion = travelVersion;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getGpsModule() {
        return gpsModule;
    }

    public void setGpsModule(String gpsModule) {
        this.gpsModule = gpsModule;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "TerminalProtocolCommand{" +
                "typeName='" + typeName + '\'' +
                ", producerId='" + producerId + '\'' +
                ", travelVersion='" + travelVersion + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", gpsModule='" + gpsModule + '\'' +
                ", " + super.toString() +
                '}';
    }
}