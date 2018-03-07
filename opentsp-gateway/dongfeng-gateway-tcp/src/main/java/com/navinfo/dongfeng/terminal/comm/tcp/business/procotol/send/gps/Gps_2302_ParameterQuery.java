package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 参数查询
 * 参数查询消息体为空，参数查询回复参数查询应答（0x3302）
 * @author caozhen
 *
 */
@Component
public class Gps_2302_ParameterQuery extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "参数查询");
		} catch (Exception e) {
			log.error("参数查询异常", e);
		}
		return null;
	}


}
