package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.terminal.LCUpCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 数据上行透传 Gps_3131_UpPassThrough
 * 
 * @author maojin
 * 
 */
@Component
public class Gps_3131_UpPassThrough extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "数据上行透传");
			// 回复云平台
			LCUpCommonRes.UpCommonRes.Builder bulider =  LCUpCommonRes.UpCommonRes.newBuilder();
			bulider.setSerialNumber(Integer.parseInt(packet.getSerialNumber()));
			bulider.setResponseId(3131);
			bulider.setResult(LCResponseResult.ResponseResult.success);
			packet.setCommand("2001");
			packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
			packet.setContentForBytes(bulider.build().toByteArray());
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "数据上行透传");
		} catch (Exception e) {
			log.error("数据上行透传异常", e);
		}
		return null;
	
	}


}
