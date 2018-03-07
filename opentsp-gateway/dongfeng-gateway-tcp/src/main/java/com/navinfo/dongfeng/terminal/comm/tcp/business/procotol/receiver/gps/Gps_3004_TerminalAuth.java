package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.terminal.LCTerminalAuth;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Gps_3004_TerminalAuth extends Command {
	Logger logger = LoggerFactory.getLogger(Gps_3004_TerminalAuth.class);
	@Override
	public Object processor(Packet packet) {
		try {
			String sim = packet.getUniqueMark().substring(1);
			LCTerminalAuth.TerminalAuth terminalAuth= LCTerminalAuth.TerminalAuth.parseFrom(packet.getContentForBytes());
			
			String authCode=terminalAuth.getAuthCoding();
			// 发送809,不处理
//			Packet packet809=new Packet();
//			packet809.setCommand("5107");
//			packet809.addParameter("vehicleNo", "");
//			packet809.addParameter("vehicleColor", "");
//			packet809.addParameter("produceId", "");
//			packet809.addParameter("terminalModelType", "");
//			packet809.addParameter("TerminalIdentify", "");
//			packet809.addParameter("authCoding", authCode);
//			GpsCommandFactory.processor(packet809);
		} catch (Exception e) {
			logger.error("异常", e);
		}
		return null;
	}


}
