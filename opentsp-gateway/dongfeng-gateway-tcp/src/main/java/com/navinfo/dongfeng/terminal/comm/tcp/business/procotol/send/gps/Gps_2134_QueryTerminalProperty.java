package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 查询终端属性
 * 查询终端属性消息体为空，回复查询终端属性应答
 * @author Administrator
 *
 */
@Component
public class Gps_2134_QueryTerminalProperty extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"查询终端属性");
		} catch (Exception e) {
			log.error("查询终端属性异常", e);
		}
		return null;
	}


}
