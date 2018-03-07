package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 单向监听
 * Gps_2152_CallListener
 * @author maojin
 *
 */
@Component
public class Gps_2152_CallListener extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "单向监听");
		} catch (Exception e) {
			log.error("单向监听异常", e);
		}
		return null;
	}


}
