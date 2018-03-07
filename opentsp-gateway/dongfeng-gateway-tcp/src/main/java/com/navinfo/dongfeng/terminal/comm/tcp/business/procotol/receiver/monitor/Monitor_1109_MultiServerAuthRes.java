package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.monitor;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.platform.auth.LCMultiServerAuthRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.MonitorCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 多服务鉴权请求应答
 * 
 */
@Component
public class Monitor_1109_MultiServerAuthRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			LCMultiServerAuthRes.MultiServerAuthRes res = LCMultiServerAuthRes.MultiServerAuthRes.parseFrom(packet
					.getContentForBytes());
			// 获取数据中心链路信息缓存，数据中心已经合并，没有想过链路缓存
//			Map<ServerAddress, IoSession> map = ProcessSessionCache.getInstance().getCache();
			List<LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo> list = res.getInfosList();
			CloudLinkCache.instance().getCodesCache().clear();
			if (null != list && list.size() > 0) {
				//流程操作成功标识,caozhen,20151202 新增
				CloudServerStart cloudServerStart=(CloudServerStart)packet.getSession().getAttribute("cloudServerStart");
				if(cloudServerStart!=null){
					cloudServerStart.setIsSucces(2);
				}
				
				for (int i = 0; i < list.size(); i++) {
					LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo infos = list.get(i);
					// 将多服务鉴权应答ServerAuthInfo对象放入缓存中
//					CloudLinkCache.instance().getCodesCache().clear();
					CloudLinkCache.instance().addCodesCache(infos);
					String serverIp = infos.getServerIp();
//					log.error("1109>>多服务鉴权应答，ServerIp:"+serverIp+",ServerIdentify:"+infos.getServerIdentify());
//					System.out.println("1109>>多服务鉴权应答，ServerIp:"+serverIp+",ServerIdentify:"+infos.getServerIdentify());
//					for (Map.Entry<ServerAddress, IoSession> entry : map.entrySet()) {
//						ServerAddress addr = entry.getKey();
//						// 匹配缓存中的IP信息
//						if (serverIp.equals(addr.getServerIp())) {
//							IoSession session = ProcessSessionCache.getInstance().getCache(addr);
//							if(session!=null){
//							// 向数据处理层发送多服务鉴权
//							packet.setCommand(constant.MULTI_SERVER_AUTH_RES);
//							packet.setMsgType(constant.MONITOR_MESSAGE_TYPE);
//							session.write(packet);
//							if (LogSwitch.LogLevel.INFO_LEVEL)
//								log.info("多服务鉴权");
//							}
//						} else {
//							log.error("多服务鉴权应答信息与数据处理层的IP地址未能匹配");
////							return null;
//						}
//					}
				}
				// 发起订阅请求
				packet.setCommand(Constant.SUBSCRIBE_REQUEST);
				MonitorCommandFactory.processor(packet);
			}else{
				log.error("多服务鉴权请求应答，云平台返回结果为空，请联系云平台");
//				System.out.println("多服务鉴权请求应答，云平台返回结果为空，请联系云平台");
			}

		} catch (InvalidProtocolBufferException e) {
			log.error("解析ProtocolBuffer数据异常,原始数据：" + packet, e);
		} catch (Exception e) {
			log.error("多服务鉴权请求应答异常,原始数据：" + packet, e);
		}
		return null;
	}
}
