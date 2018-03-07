package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.common.LCListenerStatus;
import com.lc.core.protocol.terminal.monitor.LCCallListener;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2152_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.model.cache.TamperData;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.services.IRedisService;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2152_CallListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2152_Handler extends NotValidateTokenAbstractHandler<Gps_2152_Cmd, CommonResult>
{
    @Autowired
    Gps_2152_CallListener gps_2152_callListener;
    
    @Autowired
    private IRedisService redisService;
    
/*    @Value("${redis.cmd.liveTime}")
    private long liveTime;*/
    
    private static Log logger = LogFactory.getLog(Gps_2152_Handler.class);
    
    public Gps_2152_Handler()
    {
        super(Gps_2152_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2152_Handler(Class<Gps_2152_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 下发指令2152（例子）
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2152_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 获取序列号
            String seriaNum = IDFactory.builderID(IDFactory.IDType.SerialNumber).toString();
            TamperData data = new TamperData();
            data.setCommanId("2152");
            data.setLogId(cmd.getLogId());
            data.setToken(cmd.getToken());
            // 保存日志
            redisService.saveObjectToJson(CacheKeyConstants.MSG_KEY_PREFIX  + "_" + seriaNum + "_"
                + 2152, data);
            // 赋值序列号
            cmd.setSerialNumber(seriaNum);
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2152_callListener.processor(packet);
            logger.warn("指令号:2152,流水号:"+packet.getSerialNumber()+",监控类型:"+cmd.getType()+",电话:"+cmd.getPhone()+",日志ID:"+cmd.getLogId());
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2152_ERROR);
            logger.error("===== send Exception =====", e);
        }
        return result;
    }
    
    /**
     * 入参转换
     *
     * @param cmd
     * @return
     */
    private Packet getSendPacket(Gps_2152_Cmd cmd)
    {
        LCCallListener.CallListener.Builder builder = LCCallListener.CallListener.newBuilder();
        builder.setPhoneNumber(cmd.getPhone());
        String content = "";
        if (cmd.getType().equals("0"))
        {
            builder.setStatus(LCListenerStatus.ListenerStatus.listening);
        }
        else
        {
            builder.setStatus(LCListenerStatus.ListenerStatus.calling);
        }
        Packet packet = new Packet();
        packet.setUniqueMark(cmd.getUniqueMark());
        packet.setContentForBytes(builder.build().toByteArray());
        packet.setCommand(cmd.getCommand());
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
