package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.terminal.LCUpCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.SerialNumberExchange;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/****
 *	终端升级结果通知
 * @author Administrator
 *
 */
@Component
public class Gps_3408_TerminalUpgradePackageRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			// 流水号-链路缓存转化处理 ，不进行转化处理，给每个可以看见该终端的客户端前台发送该指令
			packet= SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
//			byte[] receiveContent = packet.getContentForBytes();
//			IoSession session = SerialNumberExchange.linkToClient(packet.getCommand(), receiveContent);
			packet.setSession(null);
			
//			byte[] sendContent = SerialNumberExchange.LCChangeToClient(packet.getCommand(), receiveContent);
//			packet.setContentForBytes(sendContent);

			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "终端升级结果通知");
			// 回复云平台
			LCUpCommonRes.UpCommonRes.Builder bulider =  LCUpCommonRes.UpCommonRes.newBuilder();
			bulider.setSerialNumber(Integer.parseInt(packet.getSerialNumber()));
			bulider.setResponseId(3408);
			bulider.setResult(LCResponseResult.ResponseResult.success);
			packet.setCommand("2001");
			packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
			packet.setContentForBytes(bulider.build().toByteArray());
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "终端升级结果通知");
		} catch (Exception e) {
			log.error("终端升级结果通知异常", e);
		}
		return null;
	}


}
