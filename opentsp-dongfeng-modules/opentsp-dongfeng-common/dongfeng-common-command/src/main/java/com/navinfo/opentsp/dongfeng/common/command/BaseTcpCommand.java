package com.navinfo.opentsp.dongfeng.common.command;

import java.io.Serializable;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class BaseTcpCommand implements Serializable {

    private static final long serialVersionUID = 491958049962316239L;
    private String token;// token

    private String command;// 消息ID

    private String nodeMsg;// 消息体属性

    private String uniqueMark;// 唯一标识 SIM/CODE

    private String serialNumber = "0000";// 消息流水号

    private String content;// 消息体

    public String contentForBytes;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getNodeMsg() {
        return nodeMsg;
    }

    public void setNodeMsg(String nodeMsg) {
        this.nodeMsg = nodeMsg;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentForBytes() {
        return contentForBytes;
    }

    public void setContentForBytes(String contentForBytes) {
        this.contentForBytes = contentForBytes;
    }
}
