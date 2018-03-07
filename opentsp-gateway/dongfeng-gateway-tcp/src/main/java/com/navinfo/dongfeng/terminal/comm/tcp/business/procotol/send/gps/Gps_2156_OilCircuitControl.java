package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;


/**
 * 油路/电路控制
 * Gps_2156_OilCircuitControl
 * @author maojin
 *
 */
@Component
public class Gps_2156_OilCircuitControl extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "油路/电路控制");
		} catch (Exception e) {
			log.error("油路/电路控制异常", e);
		}
		return null;
	}


}
