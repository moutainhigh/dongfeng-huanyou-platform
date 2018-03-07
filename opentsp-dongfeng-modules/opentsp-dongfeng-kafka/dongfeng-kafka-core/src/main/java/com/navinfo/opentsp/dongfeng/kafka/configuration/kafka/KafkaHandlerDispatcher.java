package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @date 2016-8-24
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class KafkaHandlerDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaHandlerDispatcher.class);

    private final Map<String, KafkaCommandHandler> handlers;

    @Autowired
    public KafkaHandlerDispatcher(List<KafkaCommandHandler> handlerList) {
       Map<String, KafkaCommandHandler> map = new HashMap<>();
        for (KafkaCommandHandler handler : handlerList) {
            Class<? extends KafkaCommandHandler> clazz = handler.getClass();
            if (!clazz.isAnnotationPresent(KafkaConsumerHandler.class)) {
                continue;
            }
            KafkaConsumerHandler kafkaConsumerHandler = clazz.getAnnotation(KafkaConsumerHandler.class);
            logger.info("{} loading kafka handler : {}", kafkaConsumerHandler.topic(), handler.getClass());
            String topic = kafkaConsumerHandler.topic();
            String commandId= kafkaConsumerHandler.commandId();
            map.put(topic+"-"+commandId, handler);
        }

        handlers = Collections.unmodifiableMap(map);
    }

    public KafkaCommandHandler getHandler(String topic) {
        return handlers.get(topic);
    }

}
