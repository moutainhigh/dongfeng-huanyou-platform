package com.navinfo.opentsp.dongfeng.kafka.handler;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.AbstractKafkaCommandHandler;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaConsumerHandler;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3007_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 终端上下线状态汇报数据（kafka）
 * <p>
 * Created by zhangy on 2017/03/20.
 */
@KafkaConsumerHandler(topic = "rpposdone", commandId = "3007")
@Component
public class Gps_3007_Handler extends AbstractKafkaCommandHandler
{
    
    private static final Logger logger = LoggerFactory.getLogger(Gps_3007_Handler.class);
    
    @Autowired
    private IGps_3007_Service gps_3007_Service;
    
    protected Gps_3007_Handler()
    {
        super(KafkaCommand.class);
    }
    
    @Override
    public void handle(KafkaCommand kafkaCommand)
    {
        try
        {
            logger.info("==========accept 3007 location data starting==========");
            kafkaCommand.setKey(StringUtil.removeFirstStr(kafkaCommand.getKey()));
            // 写入缓存
            gps_3007_Service.writeToCache(kafkaCommand);
            // 判断是否有离线缓存需要下发指令
            gps_3007_Service.sendMessage(kafkaCommand);
        }
        catch (Exception e)
        {
            logger.error("==3007 location data format error!==", e);
        }
    }
}