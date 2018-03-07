package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***********
 * 区域车次统计
 * @author Administrator
 *
 */
@Component
public class Gps_2505_VehiclePassInArea extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "区域车次统计");
		} catch (Exception e) {
			log.error("区域车次统计异常", e);
		}
		return null;
	}


}
