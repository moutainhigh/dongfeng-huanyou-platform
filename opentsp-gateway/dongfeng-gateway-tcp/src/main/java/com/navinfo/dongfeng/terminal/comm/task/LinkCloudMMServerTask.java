package com.navinfo.dongfeng.terminal.comm.task;

import com.lc.core.protocol.platform.auth.LCRequestLoginKeyRes;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主动发起心跳寰游与位置云MM服务
 * 
 * @author baitao
 * 
 */
public class LinkCloudMMServerTask extends TimerTask {
	private final static Logger log = LoggerFactory.getLogger(LinkCloudMMServerTask.class);
	private static Timer timer= new Timer() ;
	public boolean isRun=true;
	@Override
	public void run() {
		if(isRun){
			try {
				List<LCRequestLoginKeyRes.RequestLoginKeyRes> list = CloudLinkCache.instance().getCodeCache();
				long serverIdentify = 0;
				//鉴权码获取需要根据不同平台获取，暂时未处理
				for (int i = 0; i < list.size(); i++) {
					LCRequestLoginKeyRes.RequestLoginKeyRes loginRes = list.get(i);
					serverIdentify = loginRes.getServerIdentifies();
				}
				//暂时用一个区域鉴权码，不同区域对应不同鉴权码
				Map<String, IoSession> sessions=CloudLinkCache.instance().getcaches();
				for(String ipPort:sessions.keySet()){
					Packet packet = new Packet();
					packet.setCommand(Constant.LINK_HEART);
					packet.setUniqueMark(serverIdentify + "");
					packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
					packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
					packet.setContent("");
//				System.out.println("1099:serverIdentify-->"+serverIdentify);
					if (null != sessions.get(ipPort)) {
						if((Boolean)sessions.get(ipPort).getAttribute("isconnect")){
							CloudServerStart cloudServerStart=(CloudServerStart)sessions.get(ipPort).getAttribute("cloudServerStart");
							if(cloudServerStart.getServerType()!=2){
								if((cloudServerStart.getServerType()==0 && cloudServerStart.getIsSucces()>0 )
										||(cloudServerStart.getServerType()==1 && cloudServerStart.getIsSucces()>=2 )){
									packet.setFromIp(ipPort);
									sessions.get(ipPort).write(packet);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
//			System.out.println("LinkCloudServerTask>>>>e："+e.toString());
				//e.printStackTrace();
				log.error("MM定时重连异常", e);
			}
		}
	}

	public void stopTimer(){
		if(timer!=null){
			timer.cancel();
			timer=null;
		}
	}

	public Timer getTimer(){
		if(timer==null)
			timer=new Timer();
		return timer;
	}
}
