package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 上行消息的通用应答
 * Gps_2001_UpCommonRes
 * @author maojin
 *
 */
@Component
public class Gps_2001_UpCommonRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "上行消息的通用应答");
		} catch (Exception e) {
			log.error("上行消息的通用应答异常", e);
		}
		return null;
	}

	
}
