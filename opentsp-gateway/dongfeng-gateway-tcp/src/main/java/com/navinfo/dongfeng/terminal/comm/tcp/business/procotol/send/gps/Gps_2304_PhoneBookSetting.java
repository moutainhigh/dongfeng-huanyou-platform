package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/******
 * 电话薄设置
 * 电话薄设置回复下行消息通用应答(0x3001)
 * @author caozhen
 *
 */
@Component
public class Gps_2304_PhoneBookSetting extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "电话薄设置");
		} catch (Exception e) {
			log.error("电话薄设置异常", e);
		}
		return null;
	}


}
