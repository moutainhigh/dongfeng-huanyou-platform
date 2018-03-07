package com.navinfo.dongfeng.terminal.comm.task;

import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.MonitorCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/*****
 * 位置云MM重连
 * @author caozhen
 *
 */
@Component
public class ReConnectCloudMMTask {
	private final static Logger log = LoggerFactory.getLogger(ReConnectCloudMMTask.class);

	@Value("${CloudServerIp}")
	private String cloudServerIp;

	@Value("${CloudServerPort}")
	private String cloudServerPort;

	@Scheduled(fixedDelay = 30000, initialDelay = 75000)
	public void execute() throws InterruptedException {
		try{
			//所有缓存
			Map<String, IoSession> tempsessions = CloudLinkCache.instance().getcaches();
			if(tempsessions.size()>0){
				CloudServerStart temp=null;
				IoSession tempI=null;
				/*******总MM重连************/
				//1.从配置文件获取总MM IP和端口
//				String ip= Configuration.getReourcesV("CloudServerIp");
//				int port=Integer.parseInt(Configuration.getReourcesV("CloudServerPort"));

				//2.从链路缓存中获取总MM链路
				IoSession mMSession=tempsessions.get(cloudServerIp+":"+cloudServerPort);

				//3.链路是否存在
				if(mMSession!=null){
					//4.链路存在，根据是否连接、流程状态判断是否端口进行新连接,重新连接前断开总MM和分MM连接
					CloudServerStart cloudServerStart=(CloudServerStart) mMSession.getAttribute("cloudServerStart");
					if(cloudServerStart!=null) {//总MM连接
						if(!(Boolean)mMSession.getAttribute("isconnect") || cloudServerStart.getIsSucces()<1){//链路断开或0101未成功
							//停止总MM链路
							cloudServerStart.stopServer();
							//断开分MM链路
							stopM();
							//重新总MM连接
							cloudServerStart.connect();
						}
					}else{
						//断开分MM链路
						stopM();
						//重新总MM连接，cz,20160714,undo
//							 ClientServer.startCloudServer();
					}
				}else{//5.链路不存在,重新连接,重新连接前分MM连接
					//断开分MM链路
//					 stopM();
					//重新总MM连接,cz,20160714,undo
//					 ClientServer.startCloudServer();
				}

				/*************分MM重连**************/
				//1.从链路缓存中获取分MM链路
				for(String ipPort:tempsessions.keySet()){
					tempI=tempsessions.get(ipPort);
					temp=(CloudServerStart)tempI.getAttribute("cloudServerStart");
					if(temp.getServerType()==1){//分MM重连
						break;
					}else{
						tempI=null;
						temp=null;
					}
				}

				//2.链路是否存在
				if(tempI!=null){
					//3.链路存在，根据是否连接、流程状态判断是否端口进行新连接
					if(!(Boolean)tempI.getAttribute("isconnect") ||(temp!=null&&temp.getIsSucces()<2)){//重连
						temp.stopServer();
						tempI= temp.connect();
						//发送登录请求
						Packet packet=new Packet();
						packet.setSession(tempI);
						packet.setContent(CloudLinkCache.instance().getCodeCache().get(0).getServerIdentifies()+"");//鉴权码
						packet.setCommand(Constant.UP_LOGIN);
						MonitorCommandFactory.processor(packet);
					}
				}else{
					//4.链路不存在,总MM断开重新连接
					if(mMSession!=null){
						CloudServerStart cloudServerStart=(CloudServerStart) mMSession.getAttribute("cloudServerStart");
						if(cloudServerStart!=null) {//总MM连接
							//停止总MM链路
							cloudServerStart.stopServer();
							//重新总MM连接
							cloudServerStart.connect();
						}else{
							//caozhen,20160324,新增
							mMSession.setAttribute("isconnect",false);
							mMSession.close(true);
							//cz,20160714,undo
//								ClientServer.startCloudServer();
						}
					}
				}
			}else{//所有链路都不存在时
				//cz,20160714,undo
//				 ClientServer.startCloudServer();
			}

//				CloudServerStart cloudServerStart=new CloudServerStart();
//				cloudServerStart.setIp(GpsServerConfig.get("CloudServerIp"));
//				cloudServerStart.setPort(Integer.valueOf(GpsServerConfig.get("CloudServerPort")));
//				cloudServerStart.setClouds(false);
//				cloudServerStart.setConnect(false);
//				cloudServerStart.connect();

		} catch (Exception e) {
			log.error("重连MM异常", e);
		}
	}

	/************
	 * 分MM链路关闭
	 */
	public void stopM(){
		try {
			Map<String, IoSession> links = CloudLinkCache.instance().getcaches();
			for(String key:links.keySet()){
				IoSession session=links.get(key);
				if(session!=null){
					CloudServerStart cloudServerStart=(CloudServerStart) session.getAttribute("cloudServerStart");
					if(cloudServerStart!=null && cloudServerStart.getServerType()==1&&(Boolean)session.getAttribute("isconnect")){//分MM连接
						cloudServerStart.stopServer();
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void executeOnce(String cloudServerIp, String cloudServerPort) throws InterruptedException {
		try{
			//所有缓存
			Map<String, IoSession> tempsessions = CloudLinkCache.instance().getcaches();
			if(tempsessions.size()>0){
				CloudServerStart temp=null;
				IoSession tempI=null;
				/*******总MM重连************/
				//1.从配置文件获取总MM IP和端口
//				String ip= Configuration.getReourcesV("CloudServerIp");
//				int port=Integer.parseInt(Configuration.getReourcesV("CloudServerPort"));

				//2.从链路缓存中获取总MM链路
				IoSession mMSession=tempsessions.get(cloudServerIp+":"+cloudServerPort);

				//3.链路是否存在
				if(mMSession!=null){
					//4.链路存在，根据是否连接、流程状态判断是否端口进行新连接,重新连接前断开总MM和分MM连接
					CloudServerStart cloudServerStart=(CloudServerStart) mMSession.getAttribute("cloudServerStart");
					if(cloudServerStart!=null) {//总MM连接
						if(!(Boolean)mMSession.getAttribute("isconnect") || cloudServerStart.getIsSucces()<1){//链路断开或0101未成功
							//停止总MM链路
							cloudServerStart.stopServer();
							//断开分MM链路
							stopM();
							//重新总MM连接
							cloudServerStart.connect();
						}
					}else{
						//断开分MM链路
						stopM();
						//重新总MM连接，cz,20160714,undo
//							 ClientServer.startCloudServer();
					}
				}else{//5.链路不存在,重新连接,重新连接前分MM连接
					//断开分MM链路
//					 stopM();
					//重新总MM连接,cz,20160714,undo
//					 ClientServer.startCloudServer();
				}

				/*************分MM重连**************/
				//1.从链路缓存中获取分MM链路
				for(String ipPort:tempsessions.keySet()){
					tempI=tempsessions.get(ipPort);
					temp=(CloudServerStart)tempI.getAttribute("cloudServerStart");
					if(temp.getServerType()==1){//分MM重连
						break;
					}else{
						tempI=null;
						temp=null;
					}
				}

				//2.链路是否存在
				if(tempI!=null){
					//3.链路存在，根据是否连接、流程状态判断是否端口进行新连接
					if(!(Boolean)tempI.getAttribute("isconnect") ||(temp!=null&&temp.getIsSucces()<2)){//重连
						temp.stopServer();
						tempI= temp.connect();
						//发送登录请求
						Packet packet=new Packet();
						packet.setSession(tempI);
						packet.setContent(CloudLinkCache.instance().getCodeCache().get(0).getServerIdentifies()+"");//鉴权码
						packet.setCommand(Constant.UP_LOGIN);
						MonitorCommandFactory.processor(packet);
					}
				}else{
					//4.链路不存在,总MM断开重新连接
					if(mMSession!=null){
						CloudServerStart cloudServerStart=(CloudServerStart) mMSession.getAttribute("cloudServerStart");
						if(cloudServerStart!=null) {//总MM连接
							//停止总MM链路
							cloudServerStart.stopServer();
							//重新总MM连接
							cloudServerStart.connect();
						}else{
							//caozhen,20160324,新增
							mMSession.setAttribute("isconnect",false);
							mMSession.close(true);
							//cz,20160714,undo
//								ClientServer.startCloudServer();
						}
					}
				}
			}else{//所有链路都不存在时
				//cz,20160714,undo
//				 ClientServer.startCloudServer();
			}

//				CloudServerStart cloudServerStart=new CloudServerStart();
//				cloudServerStart.setIp(GpsServerConfig.get("CloudServerIp"));
//				cloudServerStart.setPort(Integer.valueOf(GpsServerConfig.get("CloudServerPort")));
//				cloudServerStart.setClouds(false);
//				cloudServerStart.setConnect(false);
//				cloudServerStart.connect();

		} catch (Exception e) {
			log.error("重连MM异常", e);
		}
	}
}
