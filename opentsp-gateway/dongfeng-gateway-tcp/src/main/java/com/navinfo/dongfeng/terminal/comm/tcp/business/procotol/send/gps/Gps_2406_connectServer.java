package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 4.2.6.7	连接指定服务器（前端下发指令到终端,等待通用应答 0X3001）
 * @author baitao
 */
@Component
public class Gps_2406_connectServer extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"连接指定服务器");
		} catch (Exception e) {
			log.error("连接指定服务器异常", e);
		}
		return null;
	}


}
