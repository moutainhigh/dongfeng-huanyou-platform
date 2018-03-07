package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * CAN数据汇报设置
 * 消息回复下行消息通用应答
 * @author Administrator
 *
 */
@Component
public class Gps_2273_CANDataReportSetting extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "CAN数据汇报设置");
		} catch (Exception e) {
			log.error("CAN数据汇报设置异常", e);
		}
		return null;
	}

}
