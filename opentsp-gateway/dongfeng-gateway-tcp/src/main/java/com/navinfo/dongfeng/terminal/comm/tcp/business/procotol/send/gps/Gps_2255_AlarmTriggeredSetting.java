package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 报警触发设置 消息回复下行消息的通用应答(0x3001) 注：报警屏蔽位需要处理扩展报警位的报警屏蔽，具体报警位参见附录-枚举-报警
 * 
 * @author caozhen
 * 
 */
@Component
public class Gps_2255_AlarmTriggeredSetting extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "报警触发设置");
		} catch (Exception e) {
			log.error("报警触发设置异常", e);
		}
		return null;
	}


}
