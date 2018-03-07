package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 采集车辆信息
 * 消息体为空，消息回复采集车辆信息应答
 * @author Administrator
 *
 */
@Component
public class Gps_2063_TRCollectVehicleInfo extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "采集车辆信息");
		} catch (Exception e) {
			log.error("采集车辆信息异常", e);
		}
		return null;
	}


}
