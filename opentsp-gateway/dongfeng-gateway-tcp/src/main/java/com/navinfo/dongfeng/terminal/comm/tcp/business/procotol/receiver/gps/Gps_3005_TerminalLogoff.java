package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.terminal.LCUpCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 终端注销
 * Gps_3005_TerminalLogoff
 * @author maojin
 *
 */
@Component
public class Gps_3005_TerminalLogoff extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "终端注销");
			// 回复云平台
			LCUpCommonRes.UpCommonRes.Builder bulider =  LCUpCommonRes.UpCommonRes.newBuilder();
			bulider.setSerialNumber(Integer.parseInt(packet.getSerialNumber()));
			bulider.setResponseId(3005);
			bulider.setResult(LCResponseResult.ResponseResult.success);
			packet.setCommand("2001");
			packet.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
			packet.setContentForBytes(bulider.build().toByteArray());
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "上行消息的通用应答");
		} catch (Exception e) {
			log.error("终端注销异常", e);
		}
		return null;
	}


}
