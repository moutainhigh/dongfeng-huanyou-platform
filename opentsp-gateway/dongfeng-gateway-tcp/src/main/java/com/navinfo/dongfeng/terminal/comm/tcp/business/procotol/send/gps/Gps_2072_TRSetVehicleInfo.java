package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 设置车辆信息
 * 设置车辆信息回复下行消息通用应答（0x3001）
 * @author Administrator
 *
 */
@Component
public class Gps_2072_TRSetVehicleInfo extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "设置车辆信息");
		} catch (Exception e) {
			log.error("设置车辆信息", e);
		}
		return null;
	}


}
