package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 存储多媒体数据检索
 * 消息回复存储多媒体数据检索应答
 * @author Administrator
 *
 */
@Component
public class Gps_2165_MediaDataQuery extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"存储多媒体数据检索");
		} catch (Exception e) {
			log.error("存储多媒体数据检索", e);
		}
		return null;
	}


}
