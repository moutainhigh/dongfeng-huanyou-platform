package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 采集执行标准版本 Gps_2055_TRVersion
 * 
 * @author maojin 注：行驶记录仪2003版本不支持查询版本，查询指令无应答代表是2003版本。
 */
@Component
public class Gps_2055_TRVersion extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "行驶记录仪--采集执行标准版本");
		} catch (Exception e) {
			log.error("行驶记录仪--采集执行标准版本异常", e);
		}
		return null;
	}


}
