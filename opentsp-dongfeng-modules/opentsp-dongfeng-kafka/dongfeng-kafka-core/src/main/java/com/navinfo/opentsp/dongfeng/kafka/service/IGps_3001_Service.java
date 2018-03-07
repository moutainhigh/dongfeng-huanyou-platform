package com.navinfo.opentsp.dongfeng.kafka.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-29
 * @modify
 * @copyright Navi Tsp
 */

public interface IGps_3001_Service {

    public void businessManage(KafkaCommand kafkaCommand)
            throws InvalidProtocolBufferException;
}
