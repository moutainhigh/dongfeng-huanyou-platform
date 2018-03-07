package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*******
 * 取消超速报警
 * 取消超速报警消息体为空，消息回复下行消息的通用应答(0x3001)。
 * 注：取消超速报警使用超速报警消息，最高速度、持续时间和超速预警设置为0xFFFFFFFF。
 * @author caozhen
 *
 */
@Component
public class Gps_2257_CancleSpeedingAlarm extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "取消超速报警");
		} catch (Exception e) {
			log.error("取消超速报警异常", e);
		}
		return null;
	}


}
