package com.navinfo.dongfeng.terminal.comm.handler;

import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2502_Cmd;
import com.navinfo.dongfeng.terminal.comm.cmd.base.BaseCmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2502_OvertimeParkingInArea;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 区域滞留超时
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-20
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2502_Handler extends NotValidateTokenAbstractHandler<Gps_2502_Cmd, CommonResult>
{
    private static Log logger = LogFactory.getLog(Gps_2502_Handler.class);
    
    @Autowired
    private Gps_2502_OvertimeParkingInArea gps_2502_overtimeParkingInArea;
    
    public Gps_2502_Handler()
    {
        super(Gps_2502_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2502_Handler(Class<Gps_2502_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 区域滞留超时
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2502_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2502_overtimeParkingInArea.processor(packet);
            logger.warn("指令号:2502 流水号:"+packet.getSerialNumber());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2502_OVERTIME_PARKING_IN_AREA_ERROR);
            logger.error("===== send Exception =====" + result.getMessage(), e);
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
