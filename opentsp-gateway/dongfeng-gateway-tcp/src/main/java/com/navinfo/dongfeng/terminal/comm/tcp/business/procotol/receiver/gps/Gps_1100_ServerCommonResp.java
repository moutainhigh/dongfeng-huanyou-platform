package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.common.LCServerCommonRes;
import com.lc.core.protocol.terminal.LCDownCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.SerialNumberExchange;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.springframework.stereotype.Component;

/**
 * 平台通用应答
 * 
 */
@Component
public class Gps_1100_ServerCommonResp extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			LCServerCommonRes.ServerCommonRes res = LCServerCommonRes.ServerCommonRes.parseFrom(packet
					.getContentForBytes());
			String responseId = Convert.decimalToHexadecimal(res.getResponseId(), 4);
			if(responseId.equals("2507")||responseId.equals("2508")){
				String serialNumber = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
				log.warn("receiver 1100:"+serialNumber+","+responseId);
			}

			if(responseId.equals("2500")||responseId.equals("2501")||responseId.equals("2502")
					||responseId.equals("2503")||responseId.equals("2317")||responseId.equals("2318")){
				packet=SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
				// 流水号-链路缓存转化处理
//						byte[] receiveContent = packet.getContentForBytes();
//						IoSession session = SerialNumberExchange.linkToClient(packet.getCommand(), receiveContent);
//						packet.setSession(session);
						
//						byte[] sendContent = SerialNumberExchange.LCChangeToClient(packet.getCommand(), receiveContent);
						//内容为3001发送
						LCDownCommonRes.DownCommonRes.Builder builder = LCDownCommonRes.DownCommonRes.newBuilder();
						builder.setResponseId(res.getResponseId());
						builder.setResult(LCResponseResult.ResponseResult.valueOf(res.getResults().getNumber()));
						builder.setSerialNumber(res.getSerialNumber());
						byte[] sendContent  = builder.build().toByteArray();
						
						packet.setContentForBytes(sendContent);
						packet.setCommand("3001");
						packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
						sendMsgToClient(packet, "下行消息通用应答"+res.getResults());
			}
			
//			log.info("平台通用应答：" + res.getResults());
		} catch (Exception e) {
			log.error("平台通用应答异常", e);
		}
		return null;
	}

}
