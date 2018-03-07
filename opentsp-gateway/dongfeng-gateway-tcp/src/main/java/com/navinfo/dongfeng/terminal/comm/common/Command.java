package com.navinfo.dongfeng.terminal.comm.common;

import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class Command {
	public static String MSG_ERROR = "";
	protected final static Logger log = LoggerFactory.getLogger(Command.class);
	/**协议前缀 */
	public static String PrefixClient = "Client_";
	public static String PrefixGps = "Gps_";
	public static String PrefixMonitor = "Monitor_";
	
	/**
	 * 进行协议处理
	 * 
	 * @param packet
	 *            Packet 数据包
	 * @return Object
	 */
	public abstract Object processor(Packet packet);

//	/**
//	 * 获取链路对象
//	 * 
//	 * @param type
//	 *            {@link Integer} 1=客户端;2=转发机链路
//	 * @param key
//	 *            {@link Object} 链路标识
//	 * @return {@link ILink}
//	 */
//	public abstract ILink getLink(Object key);
	public  Object sendMsgToMonitor(Packet packet,String msg){
//		MonitorLinkDetail ilink = MonitorLinkCache.instance().getLink(Configuration.getReourcesV("Monitor_Server_Ip")) ; 
//		IoSession session = ilink.getIoSession();
//		  if(session != null){
//			  if(packet.getSerialNumber()==null||packet.getSerialNumber().equals("0000"))
//			  packet.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
//			  session.write(packet);
//			  if(LogSwitch.LogLevel.INFO_LEVEL)
//			  log.info(msg);
//		  }
	   
	   return null;
}
	
	 public  Object sendMsgToTel(Packet packet,String msg){
		   IoSession session = packet.getSession(); 
//		   if(session==null){
//			   Map<IoSession, ILink> links = GpsTelLinkCache.instance().getCache();
//				if (links.size() > 0) {
//					IoSession ioSession;
//					for (IoSession key : links.keySet()) {
//						if (key != null) {
//							packet.setSession(key);
//							session=key;
//						}
//
//					} 
//		   }}
		   if(packet.getSerialNumber()==null||packet.getSerialNumber().equals("0000"))
		   packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
		   if(session != null){
			   session.write(packet);
			   if(LogSwitch.LogLevel.INFO_LEVEL)
			   log.info("成功发送:【"+msg+"】");
		   }
		   return null;
	   	}
	 
	 /**
	    * 下发到位置云
	    * @param packet
	    * @param msg
	    * @return
	    */
	   public  Object sendMsgToCloudMM(Packet packet,String msg){
		   IoSession session = packet.getSession();
		   if(packet.getSerialNumber()==null||packet.getSerialNumber().equals("0000"))
			   packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
		   if(session != null){
			   session.write(packet);
			   if(LogSwitch.LogLevel.INFO_LEVEL)
			   log.info(msg);
		   }
		   return null;
	   }
	   
	   /**
	    * 前端下发到位置云
	    * @param packet
	    * @param msg
	    * @return
	    */
		public Object sendMsgToCloud(Packet packet, String msg) {
			if("2500".equals(packet.getCommand())
					||"2501".equals(packet.getCommand())
					||"2502".equals(packet.getCommand())
					||"2503".equals(packet.getCommand())
					||"2505".equals(packet.getCommand())
					|| "2322".equals(packet.getCommand())
					|| "2507".equals(packet.getCommand())
					|| "2508".equals(packet.getCommand())) {//大运建服务站或取消服务站,随意发送一个位置云RP节点
				Map<String, IoSession> links= CloudLinkCache.instance().getcaches();
				if(links.size()>0){
					for(String key:links.keySet()){
						IoSession tsession=links.get(key);
						if(tsession!=null&&((CloudServerStart)tsession.getAttribute("cloudServerStart")).getServerType()==2){//rp链路才进行发送
						// 添加消息流水号和session关系的缓存
//						String serialNumber = packet.getSerialNumber()+ "_" + packet.getSession().getId();
						SerialNumberCache.instance().addSerialNumberSessionCache(packet.getSerialNumber(), tsession);
						
						//packet 的UniqueMark 换成位置云服务鉴权码
						packet.setUniqueMark(CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify()+"");
						tsession.write(packet);
						if (LogSwitch.LogLevel.INFO_LEVEL)
							log.info("成功发送:【" + msg + "】");
						break;
						}
					}
				}
				return null;
			}
			/**********************/
			String carId = packet.getUniqueMark().substring(1);
			if("0203".equals(packet.getCommand())){
				packet.getSession().write(packet);
				return null;
			}
			if (!"".equals(carId) && carId != null) {
				String ip = CloudLinkCache.instance().getCarIpCache(Long.parseLong(carId));
				
				if(ip==null)
					return null;
				
				IoSession  iLink = CloudLinkCache.instance().getcache(ip);
				if(iLink!=null){
					if(!packet.getCommand().equals("2001")){
						// 添加消息流水号和session关系的缓存
						String serialNumber = packet.getSerialNumber()+ "_" + iLink.getId();
						SerialNumberCache.instance().addSerialNumberSessionCache(serialNumber, iLink);
					}

					// 发送至云平台
					iLink.write(packet);
					if (LogSwitch.LogLevel.INFO_LEVEL)
						log.info("成功发送:【" + msg + "】");
				}else{

					if (LogSwitch.LogLevel.INFO_LEVEL)
						log.info("发送error:【" + msg + ",iLink is null】");
				}
			}
			return null;
		}
	   
	   /**
	    * 位置云发送到前端
	    * @param packet
	    * @param msg
	    * @return
	    */
		public Object sendMsgToClient(Packet packet, String msg) {
			//收到不需要进行链路转换，直接将发送的指令序列缓存删除
			if(packet.getAnswerSerialNumber()!=null &&packet.getSession()!=null){
				SerialNumberCache.instance().removeSerialNumberSessionCache(packet.getAnswerSerialNumber()+packet.getSession().getId());
			}
			String serialnumber = Convert.decimalToHexadecimal(Long.parseLong(packet.getAnswerSerialNumber()),4);
			String sim = packet.getUniqueMark().substring(1);
		   if(packet.getParameter("result")!=null){
            	String status = packet.getParameter("result");
            	//cz,20160714,undo
           // 	InstructionImp.informationHandle(serialnumber, sim, status,packet.getCommand());
                
                
            }
//			if (null != packet.getSession()) {
//				packet.getSession().write(packet);
//				if (LogSwitch.LogLevel.INFO_LEVEL)
//					log.info(msg);
//				// 删除消息流水号和session关系的缓存
//				if(!packet.getCommand().equals("3056")&&!packet.getCommand().equals("3057")&&!packet.getCommand().equals("3066")&&!packet.getCommand().equals("3070")&&!packet.getCommand().equals("3062"))//需要完善删除缓存
//				SerialNumberCache.instance().removeSerialNumberSessionCache(packet.getSerialNumber());
//			}
//			
//			if (packet.getCommand().equals("9901") || packet.getCommand().equals("9000") || packet.getCommand().equals("9112")) {
//				IoSession session = packet.getSession();
//				if (packet.getSerialNumber() == null || packet.getSerialNumber().equals(""))
//					packet.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
//				if (session != null) {
//					session.write(packet);
//					if (LogSwitch.LogLevel.INFO_LEVEL)
//						log.info(msg);
//				}
//				return null;
//			}
//			
//			if (packet.getCommand().equals("3005") || packet.getCommand().equals("3006")
//					|| packet.getCommand().equals("3007") || packet.getCommand().equals("3008")
//					|| packet.getCommand().equals("3009") || packet.getCommand().equals("3131")
//					|| packet.getCommand().equals("3154") || packet.getCommand().equals("3159")
//					|| packet.getCommand().equals("3154") || packet.getCommand().equals("3408")) {
//				String sim = packet.getUniqueMark().substring(1); // 015008387344
				// 云平台推送消息处理
				// 获取sim卡和鉴权码的缓存
//				Map<Long, List<String>> map = SimToUserCache.instance().getServerIdentifiesCache();
//				for (Map.Entry<Long, List<String>> entry : map.entrySet()) {
//					// 遍历鉴权码集合
//					List<String> list = entry.getValue();
//					// 如果该sim卡存在缓存集合中
//					if (list.contains(sim)) {
//						Long serverIdentifies = entry.getKey();
//						if (null != serverIdentifies && serverIdentifies != 0) {
//							// 获取鉴权码和session缓存
//							IoSession session = GpsLinkCache.instance().getServerIdentifiesSessionCache(serverIdentifies);
//							if(session!=null)
//							session.write(packet);
//							if (LogSwitch.LogLevel.INFO_LEVEL)
//								log.info("成功发送:【" + msg + "】");
//						}
//					}
//				}
//			}
			//809协议不考虑
			//分发所有客户端
//			if(packet.getCommand().equals("9117")||packet.getCommand().equals("6101")
//					||packet.getCommand().equals("9123")||packet.getCommand().equals("9119")
//					||packet.getCommand().equals("9122")){
//				Map<Long, IoSession> iosessionsMap=GpsLinkCache.instance().getServerIdentifiesSessionCache();
//				IoSession session=null;
//				for(Long key:iosessionsMap.keySet()){
//					session=iosessionsMap.get(key);
//					if(session!=null)
//						session.write(packet);
//				}
//				if (LogSwitch.LogLevel.INFO_LEVEL)
//					log.info("成功发送:【" + msg + "】");
//			}
			
			return null;
		}
}
