package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 * 终端自检
 * 终端自检消息体为空
 * 终端自检回复终端自检应答，终端自检是另外一种格式的参数查询消息，通过字符串方式返回终端状态文本信息。目前星网非部标的部分终端采用此消息格式
 * @author caozhen
 *
 */
@Component
public class Gps_2303_ParameterQueryText extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "终端自检");
		} catch (Exception e) {
			log.error("终端自检异常", e);
		}
		return null;
	}


}
