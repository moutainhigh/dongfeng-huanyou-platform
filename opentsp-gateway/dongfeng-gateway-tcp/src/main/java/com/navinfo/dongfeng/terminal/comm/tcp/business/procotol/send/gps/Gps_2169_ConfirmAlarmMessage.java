package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 人工确认报警消息
 * 消息回复下行消息通用应答（0x3001）
 * @author Administrator
 *
 */
@Component
public class Gps_2169_ConfirmAlarmMessage extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"人工确认报警消息");
		} catch (Exception e) {
			log.error("人工确认报警消息异常", e);
		}
		return null;
	}


}
