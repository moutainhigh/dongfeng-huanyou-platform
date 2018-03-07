package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 滞留超时延时
 * @author Administrator
 *
 */
@Component
public class Gps_2525_DelayOvertimeParkingInArea extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "滞留超时延时");
		} catch (Exception e) {
			log.error("滞留超时延时异常", e);
		}
		return null;
	}


}
