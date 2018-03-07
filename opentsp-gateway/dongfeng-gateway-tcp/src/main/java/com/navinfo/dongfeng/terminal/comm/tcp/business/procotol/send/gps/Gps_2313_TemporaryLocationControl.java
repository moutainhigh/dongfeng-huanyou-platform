package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 临时位置跟踪控制
 * 消息回复下行消息通用应答（0x3001）
 * @author caozhen
 *
 */
@Component
public class Gps_2313_TemporaryLocationControl extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "临时位置跟踪控制");
		} catch (Exception e) {
			log.error("临时位置跟踪控制异常", e);
		}
		return null;
	}


}
