package com.navinfo.opentsp.dongfeng.kafka.handler;

import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.AbstractKafkaCommandHandler;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaConsumerHandler;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3170_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 3170指令回复
 *
 * @wenya
 * @create 2017-03-31 17:51
 **/
@KafkaConsumerHandler(topic = "qq_poscan_pkt", commandId = "3170")
@Component
public class Gps_3170_Handler extends AbstractKafkaCommandHandler {
    @Autowired
    private IGps_3170_Service gps_3170_service;

    private static final Logger logger = LoggerFactory.getLogger(Gps_3170_Handler.class);

    protected Gps_3170_Handler() {
        super(KafkaCommand.class);;
    }

    @Override
    public void handle(KafkaCommand kafkaCommand) {
        try
        {
            logger.info("==========accept 3170 location data starting==========");
            gps_3170_service.businessManage(kafkaCommand);
        }
        catch (Exception e)
        {
            logger.error("==3170 location data format error!==", e);
        }
    }
}

