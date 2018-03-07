package com.navinfo.opentsp.dongfeng.kafka.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;

import java.io.IOException;

/**
 * Created by zhangyu on 2017/3/21.
 */
public interface IGps_3002_Service {
    /**
     * 末次位置入缓存
     *
     * @param kafkaCommand
     * @throws InvalidProtocolBufferException
     */
    void writeToCache(KafkaCommand kafkaCommand)
            throws IOException;

    /**
     * 发送离线缓存指令
     *
     * @param kafkaCommand
     */
    void sendOffline(KafkaCommand kafkaCommand);

    /**
     * 更新首次上线时间
     *
     * @param kafkaCommand
     */
    void updateFirstOnlineTime(KafkaCommand kafkaCommand) throws Exception;

    /**
     * 获取车辆异常加油点减油点信息
     *
     * @param kafkaCommand
     */
    void writeOilChangeBoltToCache(KafkaCommand kafkaCommand);

}
