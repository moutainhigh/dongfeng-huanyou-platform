package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyu
 * @version 1.0
 * @modify
 * @copyright opentsp
 */
@EnableConfigurationProperties
@Configuration
public class KafkaConfig {

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

    @Value("${opentsp.kafka.consumer.concurrency.size:1}")
    private int concurrencySize;

    @Value("#{'${opentsp.kafka.consumer.listener.topics}'.split(',')}")
    private String[] topics;

    @Value("${opentsp.kafka.consumer.session.timeout.ms:15000}")
    private String sessionTimeoutMs;

    @Value("${opentsp.kafka.consumer.enable.auto.commit:true}")
    private boolean enableAutoCommit;

    @Value("${opentsp.kafka.consumer.auto.commit.interval.ms:500}")
    private String autoCommitIntervalMs;

//    @Value("${opentsp.kafka.consumer.client.id}")
//    private String clientId;

    @Value("${opentsp.kafka.consumer.key.serializer.class:kafka.serializer.StringEncoder}")
    private String consumerKeySerializerClass;

    @Value("${opentsp.kafka.consumer.value.serializer.class:kafka.serializer.StringEncoder}")
    private String consumerValueSerializerClass;

    @Value("${opentsp.kafka.consumer.group.id:defalut_group}")
    private String groupId;

    @Value("${opentsp.kafka.heartbeat.interval.ms:30000}")
    private String heartbeatInterval;

    @Value("${opentsp.kafka.max.partition.fetch.bytes:524288}")
    private String maxPartitionFetchbytes;

    @Autowired
    private KafkaConsumerListener kafkaConsumerListener;


    @ConditionalOnMissingBean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKeySerializerClass);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValueSerializerClass);
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchbytes);
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
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
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }


    @Bean
    public ContainerProperties containerProperties() {
        logger.info("load listener topics :{}", topics);
        ContainerProperties containerProperties = new ContainerProperties(topics);
        containerProperties.setMessageListener(kafkaConsumerListener);
        return containerProperties;
    }


    @Bean
    public ConcurrentMessageListenerContainer<String, Object> concurrentMessageListenerContainer() {
        ConcurrentMessageListenerContainer<String, Object> factory = new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProperties());
        factory.setConcurrency(concurrencySize);
        factory.getContainerProperties().setPollTimeout(requestTimeoutMs);
        return factory;
    }

    @Bean
    public KafkaMessageChannel kafkaMessageChannel(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaMessageChannel(kafkaTemplate);
    }

}


