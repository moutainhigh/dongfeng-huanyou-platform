package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 4.2.6.1	终端关机（前端下发指令到终端,等待通用应答 0X3001）
 *@author baitao
 */
@Component
public class Gps_2400_TerminalPowerOff extends Command {

	@Override
	public Object processor(Packet packet) {
		//1.处理业务数据将自动设置为NULL cket.setMsgForBytes(null);
		//2.没有处理 就不动直接send出去
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"终端关机");
		} catch (Exception e) {
			log.error("终端关机异常", e);
		}
		return null;
	}


}
