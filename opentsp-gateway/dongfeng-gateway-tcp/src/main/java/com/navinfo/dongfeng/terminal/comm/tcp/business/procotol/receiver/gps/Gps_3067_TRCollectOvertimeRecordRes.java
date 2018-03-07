package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.SerialNumberExchange;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/***
 * 采集指定的超时驾驶记录应答
 * @author Administrator
 *
 */
@Component
public class Gps_3067_TRCollectOvertimeRecordRes extends Command {

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
//			
//			TRCollectOvertimeRecordRes res=TRCollectOvertimeRecordRes.parseFrom(packet.getContentForBytes());
//			//判断是否是809发送的，如果809发送则推送给809否则推送给前台
//			String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
////			String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
//			Packet oldPacket=Access809LinkCache.instance().getserialNumberToPacketCache(num);
//			if(null==oldPacket){
			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "采集指定的超时驾驶记录应答");
//			}else{//809发送的
//				packet.setCommand("5115");
//				packet.addParameter("oldCommand", "3067");
//			GpsCommandFactory.processor(packet);
//			}
		} catch (Exception e) {
			log.error("采集指定的超时驾驶记录应答异常", e);
		}
		return null;
	}


}
