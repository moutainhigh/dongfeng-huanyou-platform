package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 位置查询
 * Gps_2050_LocationDataQuery
 * @author maojin
 *
 */
@Component
public class Gps_2050_LocationDataQuery extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "下发位置查询指令");
		} catch (Exception e) {
			log.error("前端位置查询指令异常", e);
		}
		return null;
	}


}
