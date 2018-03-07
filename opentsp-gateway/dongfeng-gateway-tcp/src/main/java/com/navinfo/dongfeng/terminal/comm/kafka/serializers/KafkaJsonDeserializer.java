package com.navinfo.dongfeng.terminal.comm.kafka.serializers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaJsonDeserializer<T> implements Deserializer<T> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaJsonDeserializer.class);
    private ObjectMapper objectMapper;
    private Class<T> type;

    public KafkaJsonDeserializer() {

    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        configure(new KafkaJsonDeserializerConfig(props), isKey);
    }

    protected void configure(KafkaJsonDecoderConfig config, Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.type = type;

        boolean failUnknownProperties = config.getBoolean(KafkaJsonDeserializerConfig.FAIL_UNKNOWN_PROPERTIES);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failUnknownProperties);
    }

    @SuppressWarnings("unchecked")
    private void configure(KafkaJsonDeserializerConfig config, boolean isKey) {
        if (isKey) {
            configure(config, (Class<T>) config.getClass(KafkaJsonDeserializerConfig.JSON_KEY_TYPE));
        } else {
            configure(config, (Class<T>) config.getClass(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE));
        }

    }

    @Override
    public T deserialize(String _, byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;

        try {
            return  objectMapper.readValue(bytes, type);
//            logger.debug("KafkaJsonDeserializer Processing custom object start");
//            Method methodClass = t.getClass().getMethod("getMessageClass");
//            Method method = t.getClass().getMethod("getMessage");
//            Method methodSet = t.getClass().getMethod("setMessage",Object.class);
//            Class clazz = (Class) methodClass.invoke(t);
//            Object object = method.invoke(t);
//            byte[] messageBytes=objectMapper.writeValueAsBytes(object);
//            methodSet.invoke(t,objectMapper.readValue(messageBytes, clazz));
//            logger.debug("KafkaJsonDeserializer Processing custom object end");
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    protected Class<T> getType() {
        return type;
    }

    @Override
    public void close() {

    }
}
