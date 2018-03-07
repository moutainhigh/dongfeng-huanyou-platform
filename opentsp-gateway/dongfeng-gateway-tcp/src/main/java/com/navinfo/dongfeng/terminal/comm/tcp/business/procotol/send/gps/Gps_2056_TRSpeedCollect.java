package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 采集速度 Gps_2056_TRSpeedCollect
 * 
 * @author maojin 注：2003行驶记录仪采集速度，TA层通过时间段匹配指令。
 */
@Component
public class Gps_2056_TRSpeedCollect extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "行驶记录仪--采集速度");

		} catch (Exception e) {
			log.error("行驶记录仪--采集速度异常", e);
		}
		return null;
	}


}
