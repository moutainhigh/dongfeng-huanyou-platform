package com.navinfo.dongfeng.terminal.comm.init;

import com.navinfo.dongfeng.terminal.comm.kafka.KafkaMessageChannel;
import com.navinfo.dongfeng.terminal.comm.kafka.command.KafkaCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;

//@Component
//public class testKfka implements ApplicationListener<ContextRefreshedEvent>
public class testKfka
{
    
//    @Autowired
    private KafkaMessageChannel kafkaMessageChannel;
    
    @Value("${opentsp.kafka.producer.qq.poscan.topic}")
    private String qqTopic;
    
//    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        
        KafkaCommand kafkaCommand = new KafkaCommand();
        kafkaCommand.setMessage(new byte[256]);
        kafkaCommand.setCommandId("3002");
        kafkaCommand.setTopic(qqTopic);
        kafkaCommand.setKey("111111111");
        kafkaMessageChannel.send(kafkaCommand);
    }
    
}
