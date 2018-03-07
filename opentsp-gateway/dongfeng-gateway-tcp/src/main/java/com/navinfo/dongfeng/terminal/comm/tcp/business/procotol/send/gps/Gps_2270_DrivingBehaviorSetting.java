package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 驾驶行为设置
 * 消息回复下行消息通用应答
 * @author Administrator
 *
 */
@Component
public class Gps_2270_DrivingBehaviorSetting extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "驾驶行为设置");
		} catch (Exception e) {
			log.error("驾驶行为设置异常", e);
		}
		return null;
	}

}
