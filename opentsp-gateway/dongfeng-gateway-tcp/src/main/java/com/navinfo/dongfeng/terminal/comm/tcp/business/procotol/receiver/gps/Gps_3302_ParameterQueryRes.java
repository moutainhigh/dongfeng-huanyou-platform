package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.SerialNumberExchange;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/*****
 * 参数查询应答
 * @author caozhen
 *
 */
@Component
public class Gps_3302_ParameterQueryRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			// 流水号-链路缓存转化处理
			packet=SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
			byte[] receiveContent = packet.getContentForBytes();
//			IoSession session = SerialNumberExchange.linkToClient(packet.getCommand(), receiveContent);
//			packet.setSession(session);
//			
//			byte[] sendContent = SerialNumberExchange.LCChangeToClient(packet.getCommand(), receiveContent);
//			packet.setContentForBytes(sendContent);

			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "参数查询应答");
		} catch (Exception e) {
			log.error("参数查询应答异常", e);
		}
		return null;
	}


}
