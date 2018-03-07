package com.navinfo.dongfeng.terminal.comm.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.core.protocol.terminal.monitor.LCTerminalStatusControl;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2170_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.model.cache.TamperData;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.services.IRedisService;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2170_TerminalStatusControl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 防控激活指令
 *
 * @wenya
 * @create 2017-03-29 9:35
 **/
@Component
public class Gps_2170_Handler extends NotValidateTokenAbstractHandler<Gps_2170_Cmd, CommonResult>
{
    @Autowired
    Gps_2170_TerminalStatusControl gps_2170_TerminalStatusControl;
    
    @Autowired
    private IRedisService redisService;
    
/*    @Value("${redis.cmd.liveTime}")
    private long liveTime;*/
    
    private static Log logger = LogFactory.getLog(Gps_2170_Handler.class);
    
    public Gps_2170_Handler()
    {
        super(Gps_2170_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2170_Handler(Class<Gps_2170_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }

    @Override
    public CommonResult businessHandle(Gps_2170_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            Packet packet = new Packet();
            // 删除离线缓存
            clearOffline(cmd.getUniqueMark());
            // 在线则指令下发,不在线则不下发，继续留在缓存中
            if (cmd != null && cmd.getStatue() > 0)
            {
                if (cmd.getFlag().intValue() == 0)
                {
                    // 入参转换
                    packet = this.getSendTamperPacket(cmd);
                }
                else
                {
                    packet = this.getLockCarPacket(cmd);
                }
                gps_2170_TerminalStatusControl.processor(packet);
                logger.warn("指令号:2170,流水号:"+packet.getSerialNumber()+",engineType:"+cmd.getEngineType()+",terminalId:"+cmd.getTerminalId()+",sign:"+cmd.getSign()+",flag:"+cmd.getFlag()+",锁车方案:"+cmd.getLockMethod()+",lockParam:"+cmd.getLockParam()+",在线状态:"+cmd.getStatue()+",日志id:"+cmd.getLogId());
                logger.warn("send 2170:" + cmd.getSerialNumber());
            }
            else
            {// 离线，缓存下发
                getSendTamperOffline(cmd);
                logger.warn("send 2170:" + "车辆不在线");
                
            }
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_0103_ERROR);
            logger.error("===== send2170 Exception =====" , e);
        }
        return result;
    }
    
    private void clearOffline(String sim)
    {
        Set<String> keys = redisService.getKeys("MSGPUSH_*_" + sim + "_" + 2170);
        Iterator<String> it = keys.iterator();
        while (it.hasNext())
        {
            redisService.del(it.next());
        }
    }
    
    /**
     * 入参转换
     *
     * @param cmd
     * @return
     */
    private Packet getSendTamperPacket(Gps_2170_Cmd cmd)
    {
        // 封装protobuf
        LCTerminalStatusControl.TerminalStatusControl.Builder builder = LCTerminalStatusControl.TerminalStatusControl.newBuilder();
        builder.setControls(LCTerminalStatusControl.PeripheralControl.openOrClose);
        List<Integer> params = new ArrayList<Integer>();
        if (cmd.getSign().intValue() == 1)
        {
            params.add(0, 0x15F4); // 开启 0x15F4
        }
        else
        {
            params.add(0, 0x4F51); // 关闭 0x4F51
        }
        params.add(1, Integer.valueOf(cmd.getTerminalId() + ""));
        params.add(2, 0xFFFFFF - Integer.valueOf(cmd.getTerminalId() + ""));
        builder.addAllParas(params);
        int controltyp = 0;
        if (cmd.getEngineType() != null && cmd.getEngineType().equals(45))
        {
            controltyp = 0x1;
        }
        else
        {
            controltyp = 0x2;
        }
        builder.setControlType(controltyp);
        // 封装包
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        String serialNumber = IDFactory.builderID(IDType.SerialNumber).toString();
        packet.setSerialNumber(serialNumber);
        packet.setContentForBytes(builder.build().toByteArray());
        // 存redis
        TamperData data = new TamperData();
        data.setCommanId("2170");
        data.setLogId(cmd.getLogId());
        data.setToken(cmd.getToken());
        if (cmd.getSign().equals("1"))
        {
            data.setType("1"); // 开启 0x15F4
        }
        else
        {
            data.setType("2"); // 关闭 0x4F51
        }
        try
        {
            redisService.saveObjectToJson(CacheKeyConstants.MSG_KEY_PREFIX + "_" + serialNumber + "_"
                + 2170, data);
        }
        catch (JsonProcessingException e)
        {
            logger.info("3170指令存入redis异常",e);
        }
        return packet;
    }
    
    // 离线缓存指令下发，存入redis
    private void getSendTamperOffline(Gps_2170_Cmd cmd)
    {
        try
        {
            redisService.saveObjectToJson(CacheKeyConstants.MSG_KEY_PREFIX  + "_" + cmd.getUniqueMark()
                + "_" + 2170, cmd);
        }
        catch (JsonProcessingException e)
        {
            logger.info("3170指令存入redis异常", e);
        }
    }
    
    /**
     * 远程锁车
     *
     * @param cmd
     * @return
     */
    private Packet getLockCarPacket(Gps_2170_Cmd cmd)
    {
        // 封装protobuf
        LCTerminalStatusControl.TerminalStatusControl.Builder builder = LCTerminalStatusControl.TerminalStatusControl.newBuilder();
        builder.setControls(LCTerminalStatusControl.PeripheralControl.lockVehicle);
        List<Integer> builder_params = new ArrayList<Integer>();
        builder_params.add(0, cmd.getSign().intValue());
        if (cmd.getSign().intValue() == 1)
        {
            builder_params.add(1, cmd.getLockParam());
        }
        else
        {
            builder_params.add(1, 3500);
        }
        builder_params.add(2, Integer.valueOf(cmd.getTerminalId()));
        builder_params.add(3, cmd.getLockMethod());
        builder.addAllParas(builder_params);
        
        int controltyp = 0;
        if (cmd.getEngineType() != null && cmd.getEngineType().trim().equals("45"))
        {
            controltyp = 0x1;
        }
        else
        {
            controltyp = 0x2;
        }
        builder.setControlType(controltyp);
        // 封装包
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        String serialNumber = IDFactory.builderID(IDType.SerialNumber).toString();
        packet.setSerialNumber(serialNumber);
        packet.setContentForBytes(builder.build().toByteArray());
        
        // 存redis
        TamperData data = new TamperData();
        data.setCommanId("2170");
        data.setLogId(cmd.getLogId());
        data.setToken(cmd.getToken());
        
        try
        {
            redisService.saveObjectToJson(CacheKeyConstants.MSG_KEY_PREFIX + "_" + serialNumber + "_"
                + 2170, data);
        }
        catch (JsonProcessingException e)
        {
            logger.info("3170指令存入redis异常",e);
        }
        
        return packet;
    }
}
