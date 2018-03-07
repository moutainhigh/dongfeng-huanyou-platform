package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.kafka.KafkaMessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 位置数据汇报 Gps_3002_LocationDataReport
 *
 * @author maojin
 *
 */
@Component
public class Gps_3002_LocationDataReport extends Command
{

    @Autowired
    private KafkaMessageChannel kafkaMessageChannel;

    @Value("${opentsp.kafka.producer.qq.poscan.topic}")
    private String qqTopic;

    @Override
    public Object processor(Packet packet)
    {
        try
        {
            // KafkaCommand kafkaCommand = new KafkaCommand();
            // kafkaCommand.setMessage(packet.getContentForBytes());
            // kafkaCommand.setCommandId(packet.getCommand());
            // kafkaCommand.setTopic(qqTopic);
            // kafkaCommand.setKey(packet.getUniqueMark().substring(1));
            // kafkaMessageChannel.send(kafkaCommand);
            // log.info("kafka send to rp success !{}", packet.toString());
        }
        catch (Exception e)
        {
            log.error("推送云平台下发的位置信息异常", e);
        }
        return null;
    }

}
