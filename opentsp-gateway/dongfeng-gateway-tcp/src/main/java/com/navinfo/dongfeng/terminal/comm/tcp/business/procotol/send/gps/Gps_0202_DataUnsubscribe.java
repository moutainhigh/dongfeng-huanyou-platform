package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.lc.core.protocol.platform.LCDataUnsubscribe;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 取消数据订阅
 * 
 */
@Component
public class Gps_0202_DataUnsubscribe extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			LCDataUnsubscribe.DataUnsubscribe.Builder builder = LCDataUnsubscribe.DataUnsubscribe.newBuilder();
			List<Long> simsList = new ArrayList<Long>();
			Set<String> cloudRpIpSet = new HashSet<String>();
			Map<Long, String> carIpMap = CloudLinkCache.instance().getCarIpCache();
			for (Map.Entry<Long, String> entry : carIpMap.entrySet()) {
				simsList.add(entry.getKey());
				cloudRpIpSet.add(entry.getValue());
			}
			builder.addAllTerminalIdentify(simsList);
			packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
			packet.setCommand("0202");
			packet.setContentForBytes(builder.build().toByteArray());
			packet.setUniqueMark(CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify() + "");
			// 根据RP服务IP，获取对应的链路信息
			for (String cloudRpIp : cloudRpIpSet) {
				IoSession session = CloudLinkCache.instance().getcache(cloudRpIp);
				packet.setSession(session);
				if (session != null) {
					session.write(packet);
				}
			}
		} catch (Exception e) {
			log.error("取消数据订阅异常", e);
		}
		return null;
	}

}
