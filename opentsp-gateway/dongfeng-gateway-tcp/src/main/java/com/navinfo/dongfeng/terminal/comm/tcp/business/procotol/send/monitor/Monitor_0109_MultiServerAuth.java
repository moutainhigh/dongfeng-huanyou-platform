package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.monitor;

import com.lc.core.protocol.platform.auth.LCMultiServerAuth;
import com.lc.core.protocol.platform.auth.LCRequestLoginKeyRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 多服务鉴权请求
 * 
 */
@Component
public class Monitor_0109_MultiServerAuth extends Command {
	@Value("${ProcessServerIp}")
	private String processServerIp;

	@Override
	public Object processor(Packet packet) {
		LCMultiServerAuth.MultiServerAuth.Builder builder = LCMultiServerAuth.MultiServerAuth
				.newBuilder();
		try {
			// GPS服务对应的IP
			String[] gpsServerIps = processServerIp.split(",");
			// webservice服务对应的IP,webservice已经不存在，caozhen,20151118
//			String wsServerIps = Configuration.getReourcesV("WsServerIp");
			
			List<String> listServerIp = new ArrayList<String>();
//			listServerIp.add(wsServerIps);
			String gpsServerIp = "";
			for (int i = 0; i < gpsServerIps.length; i++) {
				gpsServerIp = gpsServerIps[i];
				if(!listServerIp.contains(gpsServerIps[i])){
					listServerIp.add(gpsServerIp);
//					log.error("请求鉴权ip："+gpsServerIp);
//					System.out.println("请求鉴权ip："+gpsServerIp);
				}
			}
			
			builder.addAllServerIp(listServerIp);
			
			// 获取鉴权码
			long serverIdentifies = 0;
			List<LCRequestLoginKeyRes.RequestLoginKeyRes> list = CloudLinkCache.instance().getCodeCache();
			if(null != list && list.size() > 0){
				LCRequestLoginKeyRes.RequestLoginKeyRes res = list.get(0);
				serverIdentifies = res.getServerIdentifies();
			}
			builder.setChannelIdentify(serverIdentifies+"");
			packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
			packet.setContentForBytes(builder.build().toByteArray());
			sendMsgToCloudMM(packet, "多服务鉴权请求");
//			log.error("0109>>多服务鉴权请求,serverIdentifies:"+serverIdentifies+",session:"+packet.getSession().toString());
//			System.out.println("0109>>多服务鉴权请求,serverIdentifies:"+serverIdentifies+",session:"+packet.getSession().toString());
		} catch (Exception e) {
			log.error("多服务鉴权请求异常：", e);
		}

		return null;
	}

}
