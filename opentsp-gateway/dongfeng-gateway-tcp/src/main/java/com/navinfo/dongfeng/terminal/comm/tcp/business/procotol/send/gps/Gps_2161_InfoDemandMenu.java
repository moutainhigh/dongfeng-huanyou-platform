package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 信息点播菜单设置
 * Gps_2161_InfoDemandMenu
 * @author maojin
 *
 */
@Component
public class Gps_2161_InfoDemandMenu extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "信息点播菜单设置");
		} catch (Exception e) {
			log.error("信息点播菜单设置异常", e);
		}
		return null;
	}


}
