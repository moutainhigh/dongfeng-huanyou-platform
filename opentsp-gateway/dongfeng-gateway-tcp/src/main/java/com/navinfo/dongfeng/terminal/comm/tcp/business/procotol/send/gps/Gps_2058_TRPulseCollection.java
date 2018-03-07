package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 采集脉冲数据 Gps_2058_TRPulseCollection
 * 
 * @author maojin
 * 
 */
@Component
public class Gps_2058_TRPulseCollection extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "行驶记录仪--采集脉冲数据");
		} catch (Exception e) {
			log.error("行驶记录仪--采集脉冲数据异常", e);
		}
		return null;
	}


}
