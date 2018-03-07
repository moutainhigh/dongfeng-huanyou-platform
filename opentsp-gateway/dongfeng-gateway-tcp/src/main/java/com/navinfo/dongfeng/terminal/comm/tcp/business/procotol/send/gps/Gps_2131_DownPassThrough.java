package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 数据下行透传 Gps_2131_DownPassThrough
 * 
 * @author maojin
 * 
 */
@Component
public class Gps_2131_DownPassThrough extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "数据下行透传");
		} catch (Exception e) {
			log.error("数据下行透传异常", e);
		}
		return null;
	}


}
