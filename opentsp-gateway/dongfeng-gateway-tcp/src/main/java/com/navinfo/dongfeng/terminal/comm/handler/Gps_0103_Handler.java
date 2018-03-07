package com.navinfo.dongfeng.terminal.comm.handler;

import com.navinfo.dongfeng.terminal.comm.cmd.Gps_0103_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_0103_ServerLogin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 例子
 * 
 * Created by zhangyu on 2017/3/27.
 */
@Component
public class Gps_0103_Handler extends NotValidateTokenAbstractHandler<Gps_0103_Cmd, CommonResult>
{
    @Autowired
    Gps_0103_ServerLogin gps_0103_ServerLogin;
    
    private static Log logger = LogFactory.getLog(Gps_0103_Handler.class);
    
    public Gps_0103_Handler()
    {
        super(Gps_0103_Cmd.class, CommonResult.class);
    }
    
    protected Gps_0103_Handler(Class<Gps_0103_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 下发指令0103
     * 
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_0103_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            
            // 指令下发
            gps_0103_ServerLogin.processor(packet);
            logger.warn("指令号:0103 流水号:"+packet.getSerialNumber());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_0103_ERROR);
            logger.error("===== send Exception =====" , e);
        }
        return result;
    }
    
    /**
     * 入参转换
     * 
     * @param cmd
     * @return
     */
    private Packet getSendPacket(Gps_0103_Cmd cmd)
    {
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(Convert.hexStringToBytes(cmd.getContentForBytes()));
        
        return packet;
    }
}
