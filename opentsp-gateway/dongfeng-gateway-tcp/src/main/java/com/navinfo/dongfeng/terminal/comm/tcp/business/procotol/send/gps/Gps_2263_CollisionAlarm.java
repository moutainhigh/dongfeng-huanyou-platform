package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*****
 * 碰撞报警
 * 消息回复下行消息的通用应答(0x3001)
 * @author caozhen
 *
 */
@Component
public class Gps_2263_CollisionAlarm extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "碰撞报警");
		} catch (Exception e) {
			log.error("碰撞报警异常", e);
		}
		return null;
	}


}
