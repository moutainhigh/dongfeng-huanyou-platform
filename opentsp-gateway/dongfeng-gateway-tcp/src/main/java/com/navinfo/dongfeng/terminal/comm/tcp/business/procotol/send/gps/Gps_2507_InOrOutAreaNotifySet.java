package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;


import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Gps_2507_InOrOutAreaNotifySet extends Command {
    Logger logger = LoggerFactory.getLogger(Gps_2507_InOrOutAreaNotifySet.class);

    @Override
    public Object processor(Packet packet) {
        try {
            packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
            sendMsgToCloud(packet, "进出区域通知设置");
        } catch (Exception e) {
            log.error("进出区域通知设置异常", e);
        }
        return null;
    }

}
