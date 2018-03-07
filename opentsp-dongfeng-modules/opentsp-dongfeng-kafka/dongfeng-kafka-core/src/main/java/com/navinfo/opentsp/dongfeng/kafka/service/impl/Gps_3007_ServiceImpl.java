package com.navinfo.opentsp.dongfeng.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.terminal.LCTerminalOnlineSwitch;
import com.navinfo.opentsp.dongfeng.common.cache.CarStatueCache;
import com.navinfo.opentsp.dongfeng.common.dto.Packet;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.json.JacksonUtils;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3002_Service;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3007_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 终端上下线状态汇报service
 *
 * Created by zhangyu on 2017/3/10.
 */
@Service(value = "gps_3007_Service")
public class Gps_3007_ServiceImpl extends BaseService implements IGps_3007_Service
{
    
    protected static final Logger logger = LoggerFactory.getLogger(Gps_3007_ServiceImpl.class);

    @Value("${stop.3007.server}")
    private String outOfService_3007;

    @Autowired
    private CarStatueCache stateCache;
    
    @Autowired
    private IGps_3002_Service gps_3002_Service;
    
    /**
     * 终端上下线状态汇报数据写入缓存
     *
     * @param kafkaCommand
     * @throws JsonProcessingException
     */
    @Override
    public void writeToCache(KafkaCommand kafkaCommand)
        throws InvalidProtocolBufferException
    {
        if (Boolean.parseBoolean(outOfService_3007)) {
            return;
        }

        String sim = kafkaCommand.getKey();
        try
        {
            Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
            LCTerminalOnlineSwitch.TerminalOnlineSwitch res =
                LCTerminalOnlineSwitch.TerminalOnlineSwitch.parseFrom(packet.getContent());
            
            boolean status = res.getStatus();
            
            logger.info("终端sim卡号：" + sim + " ,终端上下线状态: " + StringUtil.valueOf(status));
            
            // 写入缓存
            stateCache.addCarStatue(sim, null, status);
        }
        catch (IOException e)
        {
            logger.error("终端上下线状态汇报数据写入缓存失败", e);
        }
        
    }
    
    // 判断是否有离线缓存需要下发指令
    
    @Override
    public void sendMessage(KafkaCommand kafkaCommand)
        throws InvalidProtocolBufferException
    {
        try
        {
            Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
            LCTerminalOnlineSwitch.TerminalOnlineSwitch res =
                LCTerminalOnlineSwitch.TerminalOnlineSwitch.parseFrom(packet.getContent());
            
            if (res.getStatus())
            {
                // 上线
                gps_3002_Service.sendOffline(kafkaCommand);
            }
        }
        catch (IOException e)
        {
            logger.error("判断是否有离线缓存需要下发指令失败", e);
        }
        
    }
}
