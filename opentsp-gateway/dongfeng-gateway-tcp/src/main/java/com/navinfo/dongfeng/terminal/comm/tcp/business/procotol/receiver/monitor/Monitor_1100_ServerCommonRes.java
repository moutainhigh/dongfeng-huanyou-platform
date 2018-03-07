package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.monitor;

import com.lc.core.protocol.common.LCPlatformResponseResult;
import com.lc.core.protocol.common.LCServerCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.springframework.stereotype.Component;

/**
 * 平台通用应答
 * 
 */
@Component
public class Monitor_1100_ServerCommonRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			LCServerCommonRes.ServerCommonRes res = LCServerCommonRes.ServerCommonRes.parseFrom(packet
					.getContentForBytes());
			String responseId = Convert.decimalToHexadecimal(res.getResponseId(), 4);
			log.info("收到云平台发送的[" + responseId + "]通用应答");
			if (responseId.equals(Constant.RECONNECT)) {
				if (res.getResults().equals(LCPlatformResponseResult.PlatformResponseResult.success)) {
					log.info("重连请求云平台响应成功");
					packet.getSession().setAttribute("isconnect", true);
					
				} else {
					packet.getSession().setAttribute("isconnect", false);
					log.info("位置云响应重连请求失败："+res.getResults());
				}
			}
		} catch (Exception e) {
			log.error("解析ProtocolBuffer数据异常,原始数据：" + packet, e);
		}

		return null;
	}
	
}
