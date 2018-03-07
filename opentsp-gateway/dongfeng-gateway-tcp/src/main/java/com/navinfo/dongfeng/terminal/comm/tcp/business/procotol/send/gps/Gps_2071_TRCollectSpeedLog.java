package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 采集指定的速度状态日志
 * 消息回复采集指定的速度状态日志应答
 * @author Administrator
 *
 */
@Component
public class Gps_2071_TRCollectSpeedLog extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "采集指定的速度状态日志");
		} catch (Exception e) {
			log.error("采集指定的速度状态日志异常", e);
		}
		return null;
	}


}
