package com.navinfo.opentsp.dongfeng.kafka.handler;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.dto.Packet;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.json.JacksonUtils;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.AbstractKafkaCommandHandler;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaCommand;
import com.navinfo.opentsp.dongfeng.kafka.configuration.kafka.KafkaConsumerHandler;
import com.navinfo.opentsp.dongfeng.kafka.service.IGps_3002_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 同步位置云上kafka中的位置数据（kafka）
 * <p>
 * Created by zhangy on 2017/03/20.
 */
@KafkaConsumerHandler(topic = "rpposdone", commandId = "3002")
@Component
public class Gps_3002_Handler extends AbstractKafkaCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(Gps_3002_Handler.class);
    @Value("${history.location.msg.time.sign}")
    private Integer historyLocationTimeSign;
    @Autowired
    private IGps_3002_Service gps_3002_Service;

    protected Gps_3002_Handler() {
        super(KafkaCommand.class);
    }

    @Override
    public void handle(KafkaCommand kafkaCommand) {
        try {
            logger.debug("==========accept 3002 location data starting==========");
            kafkaCommand.setKey(StringUtil.removeFirstStr(kafkaCommand.getKey()));
            if (isHistoryLocationData(kafkaCommand)) {
                return;
            }
            // 更新首次上线时间
            gps_3002_Service.updateFirstOnlineTime(kafkaCommand);
            // 写入缓存
            gps_3002_Service.writeToCache(kafkaCommand);
            // 判断是否有离线缓存需要下发指令
            gps_3002_Service.sendOffline(kafkaCommand);

            //获取车辆加油点减油点信息
            gps_3002_Service.writeOilChangeBoltToCache(kafkaCommand);

        } catch (Exception e) {
            logger.error("==3002 location data format error!==", e);
        }
    }

    /**
     * 是否是补传的定位数据
     *
     * @param kafkaCommand
     * @return
     * @throws IOException
     */
    public boolean isHistoryLocationData(KafkaCommand kafkaCommand) throws IOException {
        Packet packet = JacksonUtils.objectMapperBuilder().readValue(kafkaCommand.getMessage(), Packet.class);
        LCLocationData.LocationData locationData = LCLocationData.LocationData.parseFrom(packet.getContent());
        long gpsDate = locationData.getGpsDate();
        if (gpsDate != 0 && StringUtil.getCurrentTimeSeconds() - gpsDate > historyLocationTimeSign) {
            return true;
        }
        return false;
    }
}