package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.serializers;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

public class KafkaJsonDecoder<T> extends KafkaJsonDeserializer<T> implements Decoder<T>
{
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public KafkaJsonDecoder(VerifiableProperties props)
    {
        configure(new KafkaJsonDecoderConfig(props.props()), (Class<T>)Object.class);
    }
    
    public KafkaJsonDecoder(VerifiableProperties props, Class<T> type)
    {
        configure(new KafkaJsonDecoderConfig(props.props()), type);
    }
    
    @Override
    public T fromBytes(byte[] bytes)
    {
        return super.deserialize(null, bytes);
    }
}
