package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * 终端升级设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class UpgradeParam extends BaseTerminalParam {
    private String urlAddress;// url地址
    private String dialPointName;// 拨号点名称
    private String userName;// 用户名
    private String userPassword;// 密码
    private String ipAddress;// IP地址
    private Integer tcpPort;// TCP端口
    private Integer udpPort;// UDP端口
    private String madeId;// 制造商ID
    private String hardwareVersion;// 硬件版本
    private String softVersion;// 软件版本
    private Integer linkTimeLimit;// 连接时限

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getDialPointName() {
        return dialPointName;
    }

    public void setDialPointName(String dialPointName) {
        this.dialPointName = dialPointName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    public Integer getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(Integer udpPort) {
        this.udpPort = udpPort;
    }

    public String getMadeId() {
        return madeId;
    }

    public void setMadeId(String madeId) {
        this.madeId = madeId;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public Integer getLinkTimeLimit() {
        return linkTimeLimit;
    }

    public void setLinkTimeLimit(Integer linkTimeLimit) {
        this.linkTimeLimit = linkTimeLimit;
    }
}
