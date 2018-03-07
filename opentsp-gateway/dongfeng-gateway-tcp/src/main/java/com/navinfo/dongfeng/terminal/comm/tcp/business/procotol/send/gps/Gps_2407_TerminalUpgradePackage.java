package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 下发终端升级包,介质下发升级
 * @author Administrator
 *
 */
@Component
public class Gps_2407_TerminalUpgradePackage extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"下发终端升级包");
		} catch (Exception e) {
			log.error("下发终端升级包异常", e);
		}
		return null;
	}


}
