package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangyu
 * @date 2016/8/23
 * @modify
 * @copyright opentsp
 */
@Component
public class KafkaConsumerListener implements MessageListener<String, Object> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    private KafkaHandlerDispatcher kafkaHandlerDispatcher;


    @Override
    public void onMessage(ConsumerRecord<String, Object> consumerRecord) {
        String topic = consumerRecord.topic();
        KafkaCommand command = (KafkaCommand) consumerRecord.value();
        logger.info("receive kafka data [topic={},key={},sendTime={},serialNumber={}]:", consumerRecord.topic(), consumerRecord.key(),command.getSerialNumber());
        if(command==null){
            logger.error("KafkaCommand is null !");
            return;
        }
        KafkaCommandHandler kafkaCommandHandler = kafkaHandlerDispatcher.getHandler(topic + "-" + command.getCommandId());
        if(kafkaCommandHandler==null){
            logger.info("not find kafkaHandler! topic={},key={},sendTime={},serialNumber={}，commandId={}",topic, consumerRecord.key(),command.getSerialNumber(),command.getCommandId());
            return;
        }
        kafkaCommandHandler.handle(command);
    }
}
