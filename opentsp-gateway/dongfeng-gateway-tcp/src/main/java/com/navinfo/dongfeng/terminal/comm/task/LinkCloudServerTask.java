package com.navinfo.dongfeng.terminal.comm.task;

import com.lc.core.protocol.platform.LCRPNodeData;
import com.lc.core.protocol.platform.auth.LCMultiServerAuthRes;
import com.navinfo.dongfeng.terminal.comm.cache.RPNodeDataCache;
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
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 主动发起心跳寰游与位置云rp服务
 * 
 * @author baitao
 * 
 */
public class LinkCloudServerTask extends TimerTask {
	private final static Logger log = LoggerFactory.getLogger(LinkCloudServerTask.class);
	private static Timer timer= new Timer() ;
	public boolean isRun=true;
	RPNodeDataCache rPNodeDataCache = null;

	public LinkCloudServerTask(RPNodeDataCache rPNodeDataCache) {
		this.rPNodeDataCache = rPNodeDataCache;
	}

	@Override
	public void run() {
		if(isRun){
			try {
				List<LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo> list = CloudLinkCache.instance().getCodesCache();
				long serverIdentify = 0;
				//鉴权码获取需要根据不同平台获取，暂时未处理
				for (int i = 0; i < list.size(); i++) {
					LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo authInfo = list.get(i);
					serverIdentify = authInfo.getServerIdentify();
				}
				//暂时用一个区域鉴权码，不同区域对应不同鉴权码
				Map<String, IoSession> sessions=CloudLinkCache.instance().getcaches();
				//rp节点
				List<LCRPNodeData.RPNodeData> rps= rPNodeDataCache.getGpsRP(serverIdentify+"");
				if(rps==null ||rps.size()==0){
					return;
				}
				CopyOnWriteArrayList<LCRPNodeData.RPNodeData> listrp = new CopyOnWriteArrayList<LCRPNodeData.RPNodeData>(rps);
				String ipPort="";

				for(LCRPNodeData.RPNodeData rp:listrp){
					ipPort=rp.getRpIp()+":"+rp.getRpPort();
					Packet packet = new Packet();
					packet.setCommand(Constant.LINK_HEART);
					packet.setUniqueMark(serverIdentify + "");
					packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
					packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
					packet.setContent("");
//				System.out.println("1099:serverIdentify-->"+serverIdentify);
					if (null != sessions.get(ipPort)) {
						CloudServerStart cloudServerStart=(CloudServerStart) sessions.get(ipPort).getAttribute("cloudServerStart");
						if((Boolean)sessions.get(ipPort).getAttribute("isconnect")&&cloudServerStart.getIsSucces()>=1){
							packet.setFromIp(ipPort);
							sessions.get(ipPort).write(packet);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
//			System.out.println("LinkCloudServerTask>>>>e："+e.toString());
				//e.printStackTrace();
				log.error("rp重连异常",e);
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
