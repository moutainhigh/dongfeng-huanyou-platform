package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 汇报策略和间隔 消息回复下行消息的通用应答(0x3001)
 * 
 * @author caozhen
 * 
 */
@Component
public class Gps_2253_ReportTacticsAndInterval extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "汇报策略和间隔");
		} catch (Exception e) {
			log.error("汇报策略和间隔异常", e);
		}
		return null;
	}


}
