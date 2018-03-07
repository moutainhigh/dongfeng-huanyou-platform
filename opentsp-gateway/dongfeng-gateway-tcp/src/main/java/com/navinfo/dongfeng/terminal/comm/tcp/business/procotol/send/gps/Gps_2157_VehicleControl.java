package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 车辆控制 Gps_2157_VehicleControl
 * 
 * @author maojin
 * 
 */
@Component
public class Gps_2157_VehicleControl extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "车辆控制");
		} catch (Exception e) {
			log.error("车辆控制异常", e);
		}
		return null;
	}

}
