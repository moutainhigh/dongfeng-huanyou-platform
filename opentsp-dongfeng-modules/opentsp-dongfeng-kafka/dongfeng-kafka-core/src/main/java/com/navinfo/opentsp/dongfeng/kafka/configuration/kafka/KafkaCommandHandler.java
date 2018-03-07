package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;


/**
 * @author zhangyu
 * @version 1.0
 * @modify
 * @copyright opentsp
 */
public interface KafkaCommandHandler<C extends KafkaCommand>  {

    void handle(C command);

    /**
     * @return Class of command which handler can process
     */
    Class<C> getCommandType();

}
