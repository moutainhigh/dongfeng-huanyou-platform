package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 采集事故疑点 Gps_2057_TRDoubtCollect
 * 
 * @author maojin
 * 
 */
@Component
public class Gps_2057_TRDoubtCollect extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "行驶记录仪--采集事故疑点");
		} catch (Exception e) {
			log.error("行驶记录仪--采集事故疑点", e);
		}
		return null;
	}


}
