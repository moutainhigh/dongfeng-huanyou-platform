package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 上报驾驶员身份信息请求
 * 上报驾驶员身份信息请求消息体为空，上报驾驶员身份信息请求回复下行消息通用应答（0x3001）；
 * 终端是否有通用应答未知，如果没有，位置云给终端发送该消息后，回复通用应答
 * @author Administrator
 *
 */
@Component
public class Gps_2010_DriverInfoQuery extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"上报驾驶员身份信息请求");
		} catch (Exception e) {
			log.error("上报驾驶员身份信息请求异常", e);
		}
		return null;
	}


}
