package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 多媒体事件信息上传
 * 终端因特定事件而主动拍摄或录音时，应在事件发生后主动上传多媒体事件消息
 * @author Administrator
 *
 */
@Component
public class Gps_3164_MediaEventInfoUpLoad extends Command {

	@Override
	public Object processor(Packet packet) {
		packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
		sendMsgToClient(packet, "多媒体事件信息上传");
		return null;
	}


}
