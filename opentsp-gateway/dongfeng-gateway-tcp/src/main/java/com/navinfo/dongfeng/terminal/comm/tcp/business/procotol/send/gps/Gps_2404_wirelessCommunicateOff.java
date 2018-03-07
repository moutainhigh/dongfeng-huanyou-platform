package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 4.2.6.5	关闭所有无线通信（前端下发指令到终端,等待通用应答 0X3001）
 * @author baitao
 */
@Component
public class Gps_2404_wirelessCommunicateOff extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"关闭所有无线通信");
		} catch (Exception e) {
			log.error("关闭所有无线通信异常", e);
		}
		return null;
	}


}
