package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*******
 * 区域信息播报
 * 消息回复下行消息通用应答（0x3001），支持圆形、矩形、多边形、路线，暂时只做圆形
 * @author Administrator
 *
 */
@Component
public class Gps_2500_MessageBroadcastInArea extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "区域信息播报");
		} catch (Exception e) {
			log.error("区域信息播报异常", e);
		}
		return null;
	}


}