package com.navinfo.opentsp.dongfeng.kafka.service;

import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;

/**
 * Created by wenya on 2017/3/31.
 */
public interface IGps_3170_Service {
    public void businessManage(KafkaCommand kafkaCommand)
            throws Exception;
}
