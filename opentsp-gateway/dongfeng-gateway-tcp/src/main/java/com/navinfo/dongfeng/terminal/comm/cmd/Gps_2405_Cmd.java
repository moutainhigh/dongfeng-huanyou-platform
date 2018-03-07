package com.navinfo.dongfeng.terminal.comm.cmd;

import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;

/**
 * 终端升级设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2405_Cmd extends BaseCmd<CommonResult>
{
    private static final long serialVersionUID = -6088138430887189071L;
    
    private int bitField0;
    
    private String urlAddress;
    
    private String dialName;
    
    private String username;
    
    private String password;
    
    private String serverIp;
    
    private int tcpPort;
    
    private int udpPort;
    
    private String productId;
    
    private String hardwareVersion;
    
    private String firmwareVersion;
    
    private boolean isAlwaysConnecting;
    
    private int connectTime;
    
    public int getBitField0()
    {
        return bitField0;
    }
    
    public void setBitField0(int bitField0)
    {
        this.bitField0 = bitField0;
    }
    
    public String getUrlAddress()
    {
        return urlAddress;
    }
    
    public void setUrlAddress(String urlAddress)
    {
        this.urlAddress = urlAddress;
    }
    
    public String getDialName()
    {
        return dialName;
    }
    
    public void setDialName(String dialName)
    {
        this.dialName = dialName;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getServerIp()
    {
        return serverIp;
    }
    
    public void setServerIp(String serverIp)
    {
        this.serverIp = serverIp;
    }
    
    public int getTcpPort()
    {
        return tcpPort;
    }
    
    public void setTcpPort(int tcpPort)
    {
        this.tcpPort = tcpPort;
    }
    
    public int getUdpPort()
    {
        return udpPort;
    }
    
    public void setUdpPort(int udpPort)
    {
        this.udpPort = udpPort;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getHardwareVersion()
    {
        return hardwareVersion;
    }
    
    public void setHardwareVersion(String hardwareVersion)
    {
        this.hardwareVersion = hardwareVersion;
    }
    
    public String getFirmwareVersion()
    {
        return firmwareVersion;
    }
    
    public void setFirmwareVersion(String firmwareVersion)
    {
        this.firmwareVersion = firmwareVersion;
    }
    
    public boolean isAlwaysConnecting()
    {
        return isAlwaysConnecting;
    }
    
    public void setAlwaysConnecting(boolean alwaysConnecting)
    {
        isAlwaysConnecting = alwaysConnecting;
    }
    
    public int getConnectTime()
    {
        return connectTime;
    }
    
    public void setConnectTime(int connectTime)
    {
        this.connectTime = connectTime;
    }
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
}
