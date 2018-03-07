package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 	采集记录仪唯一性编号
 * 消息体为空，消息回复采集记录仪唯一性编号应答
 * @author Administrator
 *
 */
@Component
public class Gps_2065_TRCollectOnlyCode extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "采集记录仪唯一性编号");
		} catch (Exception e) {
			log.error("采集记录仪唯一性编号异常", e);
		}
		return null;
	}


}
