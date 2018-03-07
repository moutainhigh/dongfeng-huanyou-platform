package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 实时数据采集汇报设置
 * 对应的终端设置为特殊用户1数据信息
 * 消息回复下行消息通用应答。
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-20
 **/
@Component
public class Gps_2274_RealTimeDataCollect extends Command {

    @Override
    public Object processor(Packet packet) {
        try {
            packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
            sendMsgToCloud(packet, "实时数据采集汇报设置");
        } catch (Exception e) {
            log.error("实时数据采集汇报设置异常", e);
        }
        return null;
    }

}
