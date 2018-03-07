package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.terminal.monitor.LCDispatchMessage;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 调度短信
 * Gps_2151_DispatchMessage
 * @author dell
 *
 */
@Component
public class Gps_2151_DispatchMessage extends Command {
	Logger logger = LoggerFactory.getLogger(Gps_2151_DispatchMessage.class);

	@Override
	public Object processor(Packet packet) {

		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "调度短信");
//			AnswerCache.instance().add(packet.getSerialNumber(),constant.WAITING);
		} catch (Exception e) {
			log.error("前端调度短信异常", e);
		}
		return null;
	}


	public static void main(String[] args) {
		String str = "0A 06 31 32 33 34 35 36 12 0A 08 01 10 00 18 00 20 00 28 01".replaceAll(" ", "");
		try {
			LCDispatchMessage.DispatchMessage dispatchMessage = LCDispatchMessage.DispatchMessage.parseFrom(Convert.hexStringToBytes(str));
			System.out.println(dispatchMessage);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
