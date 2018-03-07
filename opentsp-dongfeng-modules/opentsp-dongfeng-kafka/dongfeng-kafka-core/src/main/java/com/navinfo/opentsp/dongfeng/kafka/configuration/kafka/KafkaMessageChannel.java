//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

public class KafkaMessageChannel {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageChannel.class);
    private final KafkaTemplate<String, Object> template;
    private static long serialNumber = 0L;

    public KafkaMessageChannel(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public <T extends KafkaCommand> void send(T command) {
        try {
            ++serialNumber;
            command.setSerialNumber(serialNumber);
            command.setSendTime(new Date());
            this.template.send(command.getTopic(), command.getKey(), command);
            LOG.info("send message to kafka success [topic={},key={},commandId={},sendTime={},serialNumber={}]", new Object[]{command.getTopic(), command.getKey(), command.getCommandId(), command.getSendTime(), Long.valueOf(serialNumber)});
        } catch (Exception var3) {
            var3.printStackTrace();
            LOG.error("send message to kafka error [topic={},key={},commandId={}],error={},sendTime={},serialNumber={}", new Object[]{command.getTopic(), command.getKey(), command.getCommandId(), var3, command.getSendTime(), Long.valueOf(serialNumber)});
        }

    }
}
