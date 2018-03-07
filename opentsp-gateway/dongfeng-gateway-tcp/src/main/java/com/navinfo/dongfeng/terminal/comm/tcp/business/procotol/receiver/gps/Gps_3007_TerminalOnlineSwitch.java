package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.springframework.stereotype.Component;

/**
 * 终端上下线状态汇报 Gps_3007_TerminalOnlineSwitch
 *
 * @author maojin
 *
 */
@Component
public class Gps_3007_TerminalOnlineSwitch extends Command
{
    // Logger logger = LoggerFactory.getLogger(Gps_3007_TerminalOnlineSwitch.class);
    
    // @Value("${opentsp.kafka.producer.qq.online.topic}")
    // private String qqOnlinePkt;
    
    // @Autowired
    // private KafkaMessageChannel kafkaMessageChannel;
    
    @Override
    public Object processor(Packet packet)
    {
        try
        {
            // KafkaCommand kafkaCommand = new KafkaCommand();
            // kafkaCommand.setMessage(packet.getContentForBytes());
            // kafkaCommand.setCommandId(packet.getCommand());
            // kafkaCommand.setTopic(qqOnlinePkt);
            // kafkaCommand.setKey(packet.getUniqueMark().substring(1));
            // kafkaMessageChannel.send(kafkaCommand);
            // log.info("kafka send to rp success !{}", packet.toString());
            
        }
        catch (Exception e)
        {
            // log.error("终端上下线状态汇报异常", e);
        }
        return null;
    }
    
}
