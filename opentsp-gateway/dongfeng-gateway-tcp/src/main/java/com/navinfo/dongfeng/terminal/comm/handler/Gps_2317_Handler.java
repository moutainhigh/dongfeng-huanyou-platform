package com.navinfo.dongfeng.terminal.comm.handler;

import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2317_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2317_OutRegionToLimitSpeed;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 出区域限速
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-14
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2317_Handler extends NotValidateTokenAbstractHandler<Gps_2317_Cmd, CommonResult>
{
    private static Log logger = LogFactory.getLog(Gps_2317_Handler.class);
    
    private static final int AREA_TYPE_BASE_NUM = 14000;// 防控形状枚举值基础值
    
    @Autowired
    private Gps_2317_OutRegionToLimitSpeed gps_2317_outRegionToLimitSpeed;
    
    public Gps_2317_Handler()
    {
        super(Gps_2317_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2317_Handler(Class<Gps_2317_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 出区域限速
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2317_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2317_outRegionToLimitSpeed.processor(packet);
            logger.warn("指令号:2317 流水号:"+packet.getSerialNumber());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2317_OUT_REGION_LIMIT_SPEED_ERROR);
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
    private Packet getSendPacket(Gps_2317_Cmd cmd) throws UnsupportedEncodingException {
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        byte[] bytes = cmd.getContentForBytes().getBytes("ISO-8859-1");
        packet.setContentForBytes(bytes);
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
