package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 设置路线
 * 设置路线回复下行消息通用应答(0x3001)
 * @author caozhen
 *
 */
@Component
public class Gps_2311_SetRouteAlarm extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "设置路线");
		} catch (Exception e) {
			log.error("设置路线异常", e);
		}
		return null;
	}


}
