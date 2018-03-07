package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 提问下发 Gps_2160_AskQuestion
 * 
 * @author maojin
 * 
 */
@Component
public class Gps_2160_AskQuestion extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "提问下发");
		} catch (Exception e) {
			log.error("提问下发异常", e);
		}
		return null;
	}


}
