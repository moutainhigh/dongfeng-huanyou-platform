package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.terminal.monitor.LCTakePhotographyRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.SerialNumberExchange;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

@Component
public class Gps_3153_TakePhotographyRes extends Command {

	@Override
	public Object processor(Packet packet) {
		// TODO Auto-generated method stub
		
		try {
			LCTakePhotographyRes.TakePhotographyRes res=LCTakePhotographyRes.TakePhotographyRes.parseFrom(packet.getContentForBytes());
			// 流水号-链路缓存转化处理
			packet=SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
			byte[] receiveContent = packet.getContentForBytes();
//			IoSession session = SerialNumberExchange.linkToClient(packet.getCommand(), receiveContent);
//			packet.setSession(session);
//			
//			byte[] sendContent = SerialNumberExchange.LCChangeToClient(packet.getCommand(), receiveContent);
//			packet.setContentForBytes(sendContent);
			
			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "拍照应答:"+res.getResults());
			
			//判断是否是809所发送指令，如果是809所发，则将809发送指令缓存的序列号和发送包映射缓存调整为返回多媒体id与发送包映射的缓存，为接收到多媒体数据后判断是否为809拍照数据提供依据
//			String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
//			String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
//			Packet oldPacket=Access809LinkCache.instance().getserialNumberToPacketCache(oldNum);
//			if(null!=oldPacket){
//				//删除以前映射关系
//				Access809LinkCache.instance().delserialNumberToPacketCache(oldNum);
//				//添加新映射关系
//				Access809LinkCache.instance().addserialNumberToPacketCache(res.getMediaIdentifyList().get(0)+"", oldPacket);
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("拍照应答异常:",e);
		}
		
		return null;
	}


	
}
