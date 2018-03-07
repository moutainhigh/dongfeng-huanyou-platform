package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.setting.LCOutRegionToLimitSpeedDel;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2318_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2318_OutRegionToLimitSpeedDel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除出区域限速
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-14
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2318_Handler extends NotValidateTokenAbstractHandler<Gps_2318_Cmd, CommonResult>
{
    private static Log logger = LogFactory.getLog(Gps_2318_Handler.class);
    
    @Autowired
    private Gps_2318_OutRegionToLimitSpeedDel gps_2318_outRegionToLimitSpeedDel;
    
    public Gps_2318_Handler()
    {
        super(Gps_2318_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2318_Handler(Class<Gps_2318_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 删除出区域限速
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2318_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2318_outRegionToLimitSpeedDel.processor(packet);
            logger.warn("指令号:2318 流水号:"+packet.getSerialNumber());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2318_DEL_OUT_REGION_LIMIT_SPEED_ERROR);
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
    private Packet getSendPacket(Gps_2318_Cmd cmd)
    {
        
        LCOutRegionToLimitSpeedDel.OutRegionToLimitSpeedDel.Builder builder =
            LCOutRegionToLimitSpeedDel.OutRegionToLimitSpeedDel.newBuilder();
        List<Long> list = new ArrayList<Long>();
        list.add(cmd.getAreaIdentify());
        builder.addAllAreaIdentifys(list);
        // 填充通讯包
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(builder.build().toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
