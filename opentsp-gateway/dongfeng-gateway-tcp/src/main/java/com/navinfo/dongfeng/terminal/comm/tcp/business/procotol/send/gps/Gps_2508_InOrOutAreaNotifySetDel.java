package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;


import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

@Component
public class Gps_2508_InOrOutAreaNotifySetDel extends Command {
    @Override
    public Object processor(Packet packet) {
        try {
            packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
            sendMsgToCloud(packet, "进出区域通知设置删除");
        } catch (Exception e) {
            log.error("进出区域通知设置删除异常", e);
        }
        return null;
    }

}
