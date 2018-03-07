package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.monitor;

import com.lc.core.protocol.platform.auth.LCServerLogin;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 登录请求
 *
 */
@Component
public class Monitor_0103_ServerLogin extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			LCServerLogin.ServerLogin.Builder serverLogin = LCServerLogin.ServerLogin
			.newBuilder();
			serverLogin.setServerIdentifies(Long.parseLong(packet.getContent()));
			packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
			packet.setContentForBytes(serverLogin.build().toByteArray());
			sendMsgToCloudMM(packet, "登录请求");
//			log.error("0103>>位置云登录请求"+packet.getSession().toString()+",ServerIdentifies："+Long.parseLong(packet.getContent()));
//			System.out.println("0103>>位置云登录请求"+packet.getSession().toString()+",ServerIdentifies："+Long.parseLong(packet.getContent()));
		} catch (Exception e) {
			log.error("前端登陆位置云异常", e);;
		}
		
		return null;
	}

}
