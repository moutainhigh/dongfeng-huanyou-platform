package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.monitor;

import com.lc.core.protocol.common.LCDistrictCode;
import com.lc.core.protocol.platform.auth.LCRequestLoginKey;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 请求平台鉴权
 * 
 */
@Component
public class Monitor_1101_RequestLoginKey extends Command {
	@Value("${CloudServerUserName}")
	private String userName;

	@Value("${passWord}")
	private String passWord;

	@Value("${version}")
	private String version;

	@Value("${areaCode}")
	private int areaCode;

	@Override
	public Object processor(Packet packet) {
		try {
			LCRequestLoginKey.RequestLoginKey.Builder reqLogin = LCRequestLoginKey.RequestLoginKey
					.newBuilder();
			// TODO 需要确定配置文件中的名称
			reqLogin.setName(userName);
			reqLogin.setPassword(passWord);
			reqLogin.setVersion(version);
			// TODO 可能是多个/需要确定配置文件中的名称
			reqLogin.addDistrict(LCDistrictCode.DistrictCode.valueOf(areaCode));
			packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
			packet.setCommand(Constant.REQUEST_LOGIN_KEY);
			packet.setContentForBytes(reqLogin.build().toByteArray());
			packet.setSession(packet.getSession());
			sendMsgToCloudMM(packet,"请求平台鉴权");
//			log.error("1101>>总MM请求平台鉴权:"+packet.getSession().toString());
//			System.out.println("1101>>总MM请求平台鉴权:"+packet.getSession().toString());

		} catch (Exception e) {
			log.error("请求平台鉴权异常：", e);
		}

		return null;
	}

}
