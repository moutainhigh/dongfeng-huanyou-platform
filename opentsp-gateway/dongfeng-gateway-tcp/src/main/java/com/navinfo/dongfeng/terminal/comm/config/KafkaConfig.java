package com.navinfo.dongfeng.terminal.comm.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangy
 * @version 1.0
 * @date 2017/03/20
 * @modify
 * @copyright opentsp
 */
@Configuration
@EnableConfigurationProperties
// @PropertySource("classpath:kafka.properties")
public class KafkaConfig
{
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
    
    @Value("${opentsp.kafka.bootstrap.servers:localhost:9092}")
    private String bootstrapServers;
    
    @Value("${opentsp.kafka.producer.key.serializer.class:kafka.serializer.StringEncoder}")
    private String producerKeySerializerClass;
    
    @Value("${opentsp.kafka.producer.value.serializer.class:kafka.serializer.StringEncoder}")
    private String producerValueSerializerClass;
    
    @Value("${opentsp.kafka.producer.batch.size:200}")
    private int batchSize;
    
    @Value("${opentsp.kafka.producer.buffer.memory:33554432}")
    private int bufferMemory;
    
    @Value("${opentsp.kafka.producer.request.timeout.ms:30000}")
    private int requestTimeoutMs;
    
    @Value("${opentsp.kafka.producer.retries:0}")
    private int retries;
    
    @Value("${opentsp.kafka.producer.linger.ms:1}")
    private int lingerMs;
    
    @Bean
    public ProducerFactory<String, Object> producerFactory()
    {
        return new DefaultKafkaProducerFactory<String, Object>(producerConfigs());
    }
    
    @Bean
    public Map<String, Object> producerConfigs()
    {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerKeySerializerClass);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerValueSerializerClass);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        return props;
    }
    
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate()
    {
        return new KafkaTemplate<String, Object>(producerFactory());
    }
}
