package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.lc.core.protocol.platform.auth.LCServerLogin;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

/**
 * 登录请求
 *
 */
@Component
public class Gps_0103_ServerLogin extends Command {


	@Override
	public Object processor(Packet packet) {
		
		try {
			Thread.sleep(3*1000);//10s，等待车辆数据加载完成
			LCServerLogin.ServerLogin.Builder serverLogin = LCServerLogin.ServerLogin
			.newBuilder();
			serverLogin.setServerIdentifies(CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify());
			packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
			packet.setContentForBytes(serverLogin.build().toByteArray());
			packet.setCommand("0103");
			packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
			IoSession session=packet.getSession();
			if(session!=null){
				session.write(packet);
			}
//			log.error("->>ServerIdentifies: "+CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify()+",session:"+session.toString());
//			System.out.println("->>ServerIdentifies: "+CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify()+",session:"+session.toString());
//			sendMsgToCloud(packet, "登录请求");
		} catch (Exception e) {
			log.error("数据中心登陆位置云异常", e);
		}
		
		return null;
	}

}
