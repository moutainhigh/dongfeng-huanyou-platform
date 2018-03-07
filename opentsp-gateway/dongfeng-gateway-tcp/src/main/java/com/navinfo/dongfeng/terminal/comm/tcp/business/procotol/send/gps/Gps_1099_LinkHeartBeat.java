package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 心跳，用来保持链路的心跳，不交互任何信息，上层业务系统和位置云保持心跳,消息体为空，消息回复平台通用应答
 * 
 */
@Component
public class Gps_1099_LinkHeartBeat extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			Map<String, IoSession> links=CloudLinkCache.instance().getcaches();
			long serverIdentify=0;
			if(null!= CloudLinkCache.instance().getCodesCache() && CloudLinkCache.instance().getCodesCache().size() > 0){
				serverIdentify=CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify();
			}
			for(String ip:links.keySet()){
				IoSession session = links.get(ip);
				Boolean  isLogin=CloudLinkCache.instance().getRpIslogin().get(ip);
				if (null != session&&isLogin) {
				packet.setUniqueMark(serverIdentify+"");
				packet.setSession(links.get(ip));
				packet.setCommand("1099");
				packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
				packet.setContent("");
				packet.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
				session.write(packet);
		//		sendMsgToCloud(packet, "链路心跳");
				} else {
					log.info("地址【" + ip + "】位置云已断开连接,数据无法发送."
							+ packet.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
		
	}


}
