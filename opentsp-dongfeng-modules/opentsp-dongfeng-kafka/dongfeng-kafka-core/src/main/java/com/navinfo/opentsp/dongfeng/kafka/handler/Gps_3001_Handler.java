package com.navinfo.opentsp.dongfeng.kafka.handler;

import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.AbstractKafkaCommandHandler;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaConsumerHandler;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3001_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通用应答消费（kafka）
 * @author fengwm
 * @version 1.0
 * @date 2017-03-29
 * @modify
 * @copyright Navi Tsp
 */
@KafkaConsumerHandler(topic = "qq_poscan_pkt", commandId = "3001")
@Component
public class Gps_3001_Handler extends AbstractKafkaCommandHandler
{

    private static final Logger logger = LoggerFactory.getLogger(Gps_3001_Handler.class);

    @Autowired
    private IGps_3001_Service gps_3001_Service;

    protected Gps_3001_Handler()
    {
        super(KafkaCommand.class);
    }
    
    @Override
    public void handle(KafkaCommand kafkaCommand)
    {
        try
        {
            logger.info("==========accept 3001 location data starting==========");
            gps_3001_Service.businessManage(kafkaCommand);

        }
        catch (Exception e)
        {
            logger.error("==3001 location data format error!==", e);
        }
    }
}