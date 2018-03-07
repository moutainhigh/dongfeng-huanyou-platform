package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 报警撤销和恢复
 * 
 * @author: tushenghong
 * @version: 1.0
 * @since: 2016-9-23 下午3:14:49
 *
 **/
@Component
public class Gps_2325_AlarmCancelOrNot extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet,"报警撤销和恢复");
		} catch (Exception e) {
			log.error("警撤销和恢复：", e);
		}
		return null;
	}

}
