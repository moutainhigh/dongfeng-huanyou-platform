package com.navinfo.dongfeng.terminal.comm.tcp.business.server;

import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.tcp.business.codec.CloudServerDecoder;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.MonitorCommandFactory;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudHandler implements IoHandler{
	private final static Logger log = LoggerFactory
			.getLogger(CloudHandler.class);
			@Override
			public void exceptionCaught(IoSession session, Throwable cause)
					throws Exception {
				log.error(" 链路【" + String.valueOf(session) + "】发生异常", cause);
				if (session != null) {
					this.sessionClosed(session);
//					session = null;
				}
			}

			@Override
			public void messageReceived(IoSession session, Object message)
					throws Exception {
				Packet packet = (Packet) message;
				packet.setSession(session);
				//根据链路类型判断走gps指令还是monitor指令
				if(session.getAttribute("serverType")!=null&&(Integer)session.getAttribute("serverType")==2){//rp链路
				GpsCommandFactory.processor(packet);
				}else{//MM链路
					MonitorCommandFactory.processor(packet);
				}
//				throw new Exception("模拟异常断开");
			}

			@Override
			public void messageSent(IoSession session, Object message) throws Exception {
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
				log.info(" 下行链路【" + String.valueOf(session) + "】断开");
				long sessionId = session.getId();
				// 设置连接为false
				CloudLinkCache.instance().removeConnectCache(session.getAttribute("ip")+":"+session.getAttribute("port"));
				CloudServerDecoder.removePacketCache(sessionId);
				// 设置重连次数为0
//				new ReConnectCloudServerTask().setReConnectCount(0);
				if (session != null) {
					// 设置与位置云的连接标示为false,caozhen,20141242
					CloudServerStart cloudServerStart=(CloudServerStart) session.getAttribute("cloudServerStart");
					cloudServerStart.setConnect(false);
					if(cloudServerStart.getConnector()!=null){
					cloudServerStart.getConnector().dispose();
					}
					session.setAttribute("isconnect", false);
					session.close();
//					session = null;//此时还不能将session置为null，后续重连需要
				}
			}

			@Override
			public void sessionCreated(IoSession session) throws Exception {
				log.info("链路【" + String.valueOf(session) + "】创建.");
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status)
					throws Exception {
				log.info("链路【" + String.valueOf(session) + "】超时,状态：" + String.valueOf(status));
				this.sessionClosed(session);
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				log.info(" 链路【" + String.valueOf(session) + "】打开.");
			}

}
