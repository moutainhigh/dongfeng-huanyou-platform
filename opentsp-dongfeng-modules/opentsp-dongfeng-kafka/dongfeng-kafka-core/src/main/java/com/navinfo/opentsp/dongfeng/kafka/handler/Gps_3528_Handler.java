package com.navinfo.opentsp.dongfeng.kafka.handler;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.AbstractKafkaCommandHandler;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaConsumerHandler;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3528_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 3528指令回复
 *
 * @wenya
 * @create 2017-03-31 17:51
 **/
@SuppressWarnings("rawtypes")
@KafkaConsumerHandler(topic = "rpposdone", commandId = "3528")
@Component
public class Gps_3528_Handler extends AbstractKafkaCommandHandler {
	@Autowired
	private IGps_3528_Service gps_3528_service;

	@Value("${stop.3528.server}")
	private String outOfService;

	private static final Logger logger = LoggerFactory.getLogger(Gps_3528_Handler.class);

	@SuppressWarnings("unchecked")
	protected Gps_3528_Handler() {
		super(KafkaCommand.class);
	}

	@Override
	public void handle(KafkaCommand kafkaCommand) {
		if (Boolean.parseBoolean(outOfService)) {
			return;
		}
		try {
			logger.info("==========accept 3528 data starting==========");
			kafkaCommand.setKey(StringUtil.removeFirstStr(kafkaCommand.getKey()));
			gps_3528_service.insertSecondInfoWithCar(kafkaCommand);
		} catch (Exception e) {
			logger.error("==3528  data format error!==", e);
		}
	}
}
