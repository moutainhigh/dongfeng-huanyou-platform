package com.navinfo.dongfeng.terminal.comm.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.core.protocol.terminal.monitor.LCAntiTamperBoxSetting;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2271_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.model.cache.TamperData;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.services.IRedisService;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2271_AntiTamperBoxSetting;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * 8103终端设置指令
 *
 * @wenya
 * @create 2017-03-29 14:48
 **/
@Component
public class Gps_2271_Handler extends NotValidateTokenAbstractHandler<Gps_2271_Cmd, CommonResult>
{
    @Autowired
    Gps_2271_AntiTamperBoxSetting gps_2271_AntiTamperBoxSetting;
    
    @Autowired
    private IRedisService redisService;
    
/*    @Value("${redis.cmd.liveTime}")
    private long liveTime;*/
    
    private static Log logger = LogFactory.getLog(Gps_2271_Handler.class);
    
    public Gps_2271_Handler()
    {
        super(Gps_2271_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2271_Handler(Class<Gps_2271_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }

    @Override
    public CommonResult businessHandle(Gps_2271_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            // 清除离线缓存
            clearOffline(cmd.getUniqueMark());
            // 在线则指令下发,不在线则不下发，继续留在缓存中
            if (cmd != null && cmd.getStatue() > 0)
            {
                // 入参转换
                Packet packet = this.getSendPacket(cmd);
                gps_2271_AntiTamperBoxSetting.processor(packet);
                logger.warn("指令号:2270 流水号:"+packet.getSerialNumber());
                logger.warn("send 2271:" + cmd.getSerialNumber());
            }
            else
            {
                addOfflineCache(cmd);
                logger.warn("send 2271:" + "车辆不在线");
            }
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_0103_ERROR);
            logger.error("终端设置指令", e);
        }
        return result;
    }
    
    private void clearOffline(String sim)
    {
        Set<String> keys = redisService.getKeys("MSGPUSH_*_" + sim + "_" + 2271);
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
    private Packet getSendPacket(Gps_2271_Cmd cmd)
    {
        // 封装protobuf
        LCAntiTamperBoxSetting.AntiTamperBoxSetting.Builder builder = LCAntiTamperBoxSetting.AntiTamperBoxSetting.newBuilder();
        builder.setDeviceId(cmd.getDeviceId());
        if ("0".equals(cmd.getBaseCode()))
        {// 静默不防拆 10
            builder.setLockVehiclePar(2);
        }
        else if ("1".equals(cmd.getBaseCode()))
        {// 报警防拆 01
            builder.setLockVehiclePar(1);
        }
        else
        {
            builder.setLockVehiclePar(0); // 报警不防拆 00
        }
        // 封装包
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        String serialNumber = IDFactory.builderID(IDFactory.IDType.SerialNumber).toString();
        packet.setSerialNumber(serialNumber);
        packet.setContentForBytes(builder.build().toByteArray());
        // 存redis
        TamperData data = new TamperData();
        data.setCommanId("2271");
        data.setLogId(cmd.getLogId());
        data.setToken(cmd.getToken());
        try
        {
            redisService.saveObjectToJson(CacheKeyConstants.MSG_KEY_PREFIX + "_" + serialNumber + "_"
                + 2271, data);
        }
        catch (JsonProcessingException e)
        {
            logger.error("3170指令存入redis异常", e);
        }
        return packet;
    }
    
    // 离线缓存指令下发，存入redis
    private void addOfflineCache(Gps_2271_Cmd cmd)
    {
        try
        {
            redisService.saveObjectToJson(CacheKeyConstants.MSG_KEY_PREFIX  + "_" + cmd.getUniqueMark()
                + "_" + 2271, cmd);
        }
        catch (JsonProcessingException e)
        {
            logger.error("3170指令存入redis异常",e);
        }
    }
}
