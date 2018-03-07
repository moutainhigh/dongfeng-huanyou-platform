package com.navinfo.opentsp.dongfeng.kafka.service;


import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;

/**
 * Created by fwm on 2017/5/16
 */
public interface IGps_3003_Service
{
    /**
     * 修改车辆注册时间
     * @param kafkaCommand
     * @throws Exception
     */
    public void updateRegTime(KafkaCommand kafkaCommand) throws Exception;
}
