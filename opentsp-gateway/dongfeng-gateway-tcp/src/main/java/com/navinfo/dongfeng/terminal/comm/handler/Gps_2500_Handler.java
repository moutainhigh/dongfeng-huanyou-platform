package com.navinfo.dongfeng.terminal.comm.handler;

import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2500_Cmd;
import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2500_MessageBroadcastInArea;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 区域信息播报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-20
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2500_Handler extends NotValidateTokenAbstractHandler<Gps_2500_Cmd, CommonResult>
{
    private static Log logger = LogFactory.getLog(Gps_2500_Handler.class);
    
    @Autowired
    private Gps_2500_MessageBroadcastInArea gps_2500_messageBroadcastInArea;
    
    public Gps_2500_Handler()
    {
        super(Gps_2500_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2500_Handler(Class<Gps_2500_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 区域信息播报设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2500_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2500_messageBroadcastInArea.processor(packet);
            logger.warn("指令号:2500 流水号:"+packet.getSerialNumber());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2500_MESSAGE_BROADCAST_IN_AREA_ERROR);
            logger.error("区域信息播报设置异常", e);
        }
        return result;
    }
    
    /**
     * 入参转换
     *
     * @param cmd
     * @return
     */
    private Packet getSendPacket(BaseCmd cmd) throws UnsupportedEncodingException {
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        byte[] bytes = cmd.getContentForBytes().getBytes("ISO-8859-1");
        packet.setContentForBytes(bytes);
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
