package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 删除路线
 * 删除路线回复下行消息通用应答(0x3001)
 * @author 消息回复下行消息通用应答（0x3001）
 *
 */
@Component
public class Gps_2312_DeleteRouteAlarm extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "删除路线");
		} catch (Exception e) {
			log.error("删除路线异常", e);
		}
		return null;
	}


}
