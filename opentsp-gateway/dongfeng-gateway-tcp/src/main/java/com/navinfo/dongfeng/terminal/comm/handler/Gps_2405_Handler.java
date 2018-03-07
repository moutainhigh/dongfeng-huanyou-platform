package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.remote.LCWirelessUpdate;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2405_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2405_wirelessCommunicateUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 位置数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2405_Handler extends NotValidateTokenAbstractHandler<Gps_2405_Cmd, CommonResult>
{
    @Autowired
    private Gps_2405_wirelessCommunicateUpdate gps_2405_wirelessCommunicateUpdate;
    
    public Gps_2405_Handler()
    {
        super(Gps_2405_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2405_Handler(Class<Gps_2405_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 下发位置数据采集汇报设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2405_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2405_wirelessCommunicateUpdate.processor(packet);
            logger.warn("指令号:2405 流水号:"+packet.getSerialNumber());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2450_TERMINAL_UPGRADE_ERROR);
            logger.error("===== send Exception =====" + result.getMessage() , e);
        }
        return result;
    }
    
    /**
     * 入参转换
     *
     * @param cmd
     * @return
     */
    private Packet getSendPacket(Gps_2405_Cmd cmd)
    {
        LCWirelessUpdate.WirelessUpdate.Builder builder = LCWirelessUpdate.WirelessUpdate.newBuilder();
        builder.setUrlAddress(cmd.getUrlAddress());// url地址UrlAddress
        builder.setDialName(cmd.getDialName());// 拨号点名称DialPointName
        builder.setUsername(cmd.getUsername());// 拨号用户名UserName
        builder.setPassword(cmd.getPassword());// 拨号密码UserPassword
        builder.setServerIp(cmd.getServerIp());// 服务器地址，ip或域名IpAddress
        builder.setTcpPort(cmd.getTcpPort());// tcp端口TcpPort
        builder.setUdpPort(cmd.getUdpPort());// udp端口UdpPort
        builder.setProductId(cmd.getProductId());// 制造商ID MadeId
        builder.setHardwareVersion(cmd.getHardwareVersion());// 硬件版本HardwareVersion
        builder.setFirmwareVersion(cmd.getFirmwareVersion());// 固件版本（软件版本）SoftVersion
        builder.setConnectTime(cmd.getConnectTime());// 连接到指定服务器时限，单位秒
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(builder.build().toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
