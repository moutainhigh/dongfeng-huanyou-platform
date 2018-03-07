package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*****
 * CAN总线设置
 * 消息回复下行消息的通用应答(0x3001)
 * @author caozhen
 *
 */
@Component
public class Gps_2267_CanBusSetting extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "CAN总线设置");
		} catch (Exception e) {
			log.error("CAN总线设置异常", e);
		}
		return null;
	}


}
