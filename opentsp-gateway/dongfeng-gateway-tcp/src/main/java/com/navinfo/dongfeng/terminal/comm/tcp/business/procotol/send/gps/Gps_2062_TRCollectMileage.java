package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 采集累计行驶里程
 * 消息体为空，消息回复采集累计行驶里程应答
 * @author Administrator
 *
 */
@Component
public class Gps_2062_TRCollectMileage extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "采集累计行驶里程");
		} catch (Exception e) {
			log.error("采集累计行驶里程异常", e);
		}
		return null;
	}


}
