package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*******
 * 终端消息广播开关
 * 消息回复下行消息通用应答（0x1100） 
 * @author Administrator
 *
 */
@Component
public class Gps_2520_TerminalBroadcastSwitch extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "终端消息广播开关");
		} catch (Exception e) {
			log.error("终端消息广播开关异常", e);
		}
		return null;
	}


}
