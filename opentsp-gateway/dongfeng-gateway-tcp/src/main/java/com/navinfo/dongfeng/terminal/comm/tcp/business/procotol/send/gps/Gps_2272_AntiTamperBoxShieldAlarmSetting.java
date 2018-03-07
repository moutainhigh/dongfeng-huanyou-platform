package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*****
 * 防拆盒定制报警设置
 * 消息回复下行消息通用应答
 * @author Administrator
 *
 */
@Component
public class Gps_2272_AntiTamperBoxShieldAlarmSetting extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "防拆盒定制报警设置");
		} catch (Exception e) {
			log.error("防拆盒定制报警设置异常", e);
		}
		return null;
	}

}