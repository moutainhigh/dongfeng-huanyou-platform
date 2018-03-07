package com.navinfo.opentsp.dongfeng.kafka.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;

/**
 * Created by zhangyu on 2017/3/21.
 */
public interface IGps_3007_Service
{
    
    public void writeToCache(KafkaCommand kafkaCommand)
            throws InvalidProtocolBufferException;

    public void sendMessage(KafkaCommand kafkaCommand)
            throws InvalidProtocolBufferException;
}
