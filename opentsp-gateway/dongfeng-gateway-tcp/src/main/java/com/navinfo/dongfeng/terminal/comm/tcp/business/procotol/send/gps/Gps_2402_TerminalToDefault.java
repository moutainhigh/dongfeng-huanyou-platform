package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 4.2.6.3	终端恢复出厂设置（前端下发指令到终端,等待通用应答 0X3001）
 * @author baitao
 */
@Component
public class Gps_2402_TerminalToDefault extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"终端恢复出厂设置");
		} catch (Exception e) {
			log.error("终端恢复出厂设置异常", e);
		}
		return null;
	}


}
