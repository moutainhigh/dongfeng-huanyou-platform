package com.navinfo.opentsp.dongfeng.kafka.handler;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.AbstractKafkaCommandHandler;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaConsumerHandler;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3003_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 同步位置云上kafka中的位置数据（kafka）
 * <p>
 * Created by fwm on 2017/05/16
 */
@KafkaConsumerHandler(topic = "posraw", commandId = "3003")
@Component
public class Gps_3003_Handler extends AbstractKafkaCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(Gps_3003_Handler.class);

    @Autowired
    private IGps_3003_Service iGps_3003_service;

    protected Gps_3003_Handler() {
        super(KafkaCommand.class);
    }

    @Override
    public void handle(KafkaCommand kafkaCommand) {
        try {
            logger.info("==========accept 3003 location data starting==========");
            kafkaCommand.setKey(StringUtil.removeFirstStr(kafkaCommand.getKey()));
            // 修改车辆注册时间
            iGps_3003_service.updateRegTime(kafkaCommand);
        } catch (Exception e) {
            logger.error("==3003 location data format error!==", e);
        }
    }
}