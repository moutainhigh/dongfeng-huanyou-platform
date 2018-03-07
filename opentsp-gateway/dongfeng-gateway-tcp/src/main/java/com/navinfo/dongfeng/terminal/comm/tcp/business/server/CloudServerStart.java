package com.navinfo.dongfeng.terminal.comm.tcp.business.server;

import com.navinfo.dongfeng.terminal.comm.common.ExecutorUtil;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.codec.CloudCodecFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.MonitorCommandFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  寰游连接位置云平台服务器
 * @author baitao
 * @common 客户端服务器
 */
public class CloudServerStart {

	private static Logger log = LoggerFactory.getLogger(CloudServerStart.class);
	private IoConnector connector = null;
    private  ExecutorService threadPool  = null;
    private  Boolean isClouds  = null;//是否位置云,暂无用，使用serverType进行区分
    private int serverType=0;//0位置云总MM，1位置云分MM，2位置云rp
    private  boolean isConnect  = false;//是否链接
    private  int reConnectCount = 0;//重连次数
    private  int isSucces =0;//流程成功标识，与serverType对应:
                             // 0时0x0101鉴权成功为1; 
                             // 1时0x1103登录成功为1、0x1109多服务鉴权成功为2、0x1200数据订阅成为3; 
                             // 2时0x1103登录成功为1
    public  IoConnector getConnector() {
		return connector;
	}

	public  void setConnector(IoConnector conn) {
		connector = conn;
	}

	private  int port = 0;
	private  String ip = "";

	public IoSession connect() {
		if (connector != null && connector.isActive()) {
			log.info("与位置云连接正常！！！");
			return null;
		} else if (connector != null) {
			try {
				connector.dispose();
			} catch (Exception e) {
			}
			log.error("-->位置云,正在连接...");
			connector = null;
		}
		IoSession  oldSession=CloudLinkCache.instance().getcache(ip+":"+port);
		if(oldSession!=null){
			if((Boolean)oldSession.getAttribute("isconnect")){
				log.error(ip+":"+port+"-->与位置云连接正常...");
				return null;
			}else{
				oldSession.close();
			}
		}
		isSucces=0;
		connector = new NioSocketConnector(Runtime.getRuntime().availableProcessors()+1);
		connector.setConnectTimeoutMillis(30000);
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CloudCodecFactory()));
		if(threadPool != null){
			if(!ExecutorUtil.isShutdown(threadPool)){
				ExecutorUtil.terminate(threadPool);
				threadPool = null;
			}
		}
		 threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);
		connector.getFilterChain().addLast("threadPool", new ExecutorFilter( threadPool));
		connector.getSessionConfig().setWriterIdleTime(3000);
		connector.getSessionConfig().setMinReadBufferSize(1024);
		connector.getSessionConfig().setMaxReadBufferSize(5120);
		
		try {
			connector.setHandler(new CloudHandler());
			ConnectFuture connectFuture=connector.connect(new InetSocketAddress(ip, port));
			connectFuture.awaitUninterruptibly();
			if(connectFuture.isConnected()){
				IoSession session = connectFuture.getSession();
				session.setAttribute("ip", ip);
				session.setAttribute("port", port);
				session.setAttribute("isconnect", true);
				session.setAttribute("serverType", serverType);
				session.setAttribute("cloudServerStart", this);//将当前启动实例与session绑定
				isConnect = true;
				log.info("连接位置云服务端ip:[" + ip + "],port:[" + port + "]成功！");
//				CloudLinkCache.instance().setCache(session) ;//停用
//				CloudLinkCache.instance().getcaches().clear(); //不清空
//				CloudLinkCache.instance().addCache(String.valueOf(session.getId()), session);
				CloudLinkCache.instance().addCache(ip+":"+port, session);//调整为ip端口与session映射
				CloudLinkCache.instance().addCloudIpPortServer(ip+":"+port, this);
				// 与位置云鉴权-登录流程启动
//				if(!isClouds){
//					Packet packet = new Packet();
//					packet.setCommand(constant.REQUEST_LOGIN_KEY);
//					packet.setSession(session);
//					GpsCommandFactory.processor(packet);
//					CloudServerStart.reConnectCount=0;
//				}
				if(serverType==0){//总MM连接成功后请求平台鉴权1101
					Packet packet = new Packet();
					packet.setCommand(Constant.REQUEST_LOGIN_KEY);
					packet.setSession(session);
					MonitorCommandFactory.processor(packet);
					this.reConnectCount=0;
				}
				else if(serverType==2){//Rp连接成功
					// 重连标志
					// TODO
//					new ReConnectCloudServerTask().isconnect = true;
					//连接成功发送登录
					Packet packet=new Packet();
					packet.setSession(session);
					packet.setCommand("0103");
					new GpsCommandFactory().processor(packet);
				}
				return session;
			}else{
				log.error("连接服务端位置云服务端ip:["+ip+"],port:["+port+"]失败！");
				return null;
			}
			
		} catch (Exception e) {
			// 释放连接
			try{
				if (connector != null) {
					connector.dispose();
				}
			}catch(Exception e1){
				
			}
			connector = null;
			log.error("连接服务端失败");
			log.error("异常", e);
		}
		return null;
	}

	// 停止服务
	public void stopServer() {
		if(connector != null){
			try{	
				connector.dispose();
			} catch (Exception e) {
			connector = null;
			}
		}
	}

	public  int getPort() {
		return port;
	}

	public  void setPort(int port) {
		this.port = port;
	}

	public  String getIp() {
		return ip;
	}

	public  void setIp(String ip) {
		this.ip = ip;
	}

	public  boolean isClouds() {
		return isClouds;
	}

	public  void setClouds(boolean isClouds) {
		this.isClouds = isClouds;
	}

	public  void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public  boolean isConnect() {
		return isConnect;
	}

	public int getReConnectCount() {
		return reConnectCount;
	}

	public void setReConnectCount(int reCount) {
		reConnectCount = reCount;
	}
	
	public void addReConnectCount(){
		reConnectCount +=1;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public int getIsSucces() {
		return isSucces;
	}

	public void setIsSucces(int isSucces) {
		this.isSucces = isSucces;
	}
	
}
