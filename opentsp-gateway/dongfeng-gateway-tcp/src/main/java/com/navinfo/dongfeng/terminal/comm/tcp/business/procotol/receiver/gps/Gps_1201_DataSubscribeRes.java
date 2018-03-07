package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.platform.LCDataSubscribeRes;
import com.navinfo.dongfeng.terminal.comm.cache.DataSubscribeFailCache;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据订阅应答
 * 
 */
@Component
public class Gps_1201_DataSubscribeRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			// 数据订阅应答
			LCDataSubscribeRes.DataSubscribeRes dataSubscribeRes = LCDataSubscribeRes.DataSubscribeRes.parseFrom(packet
					.getContentForBytes());
			log.info("数据订阅应答" + dataSubscribeRes.getResult());
			List<Long> failSimsList=null;
			if(!dataSubscribeRes.getResult()){
				failSimsList=dataSubscribeRes.getTerminalIdentifyList();
				log.info("订阅失败sim总数:" + failSimsList.size());
				for(Long sim:failSimsList){
					log.info("订阅失败sim:" + sim);
				}
			}
			String RPServerAddress = packet.getSession().getRemoteAddress().toString().substring(1);
			//订阅失败sim添加缓存
			addFailCache(RPServerAddress, failSimsList);
			
			//批量位置查询请求,caozhen,20151112,注释掉放0201里进行查询
//			if(RPServerAddress!=null&&RPServerAddress!=""){
//			List<Long> simsList = new ArrayList<Long>();
//			Map<Long, String> carIpMap = CloudLinkCache.instance().getCarIpCache();
//			for (Long sim : carIpMap.keySet()) {
//				if (carIpMap.get(sim).equals(RPServerAddress)) {
//					simsList.add(sim);
//				}
//			}
//			LCBatchLocationQuery.BatchLocationQuery.Builder builder0203 = LCBatchLocationQuery.BatchLocationQuery.newBuilder();
//			Packet packet0203 = new Packet();
//			// 0203
//			builder0203.addAllTerminalIdentify(simsList);
//			packet0203.setSerialNumber(IDFactory.builderID(IDType.SerialNumber)+"");
//			packet0203.setMsgType(constant.CLOUD_MESSAGE_TYPE);
//			packet0203.setCommand("0203");
//			packet0203.setContentForBytes(builder0203.build().toByteArray());
//			packet0203.setUniqueMark(GpsLinkCache.instance().getCodesCache().get(0).getServerIdentify()
//					+ "");
//			packet0203.setSession(packet.getSession());
//			packet0203.setTo(RPServerAddress);
//			GpsCommandFactory.processor(packet0203);
//			session.write(packet0203);
//			}
		} catch (Exception e) {
			log.error("数据订阅应答异常", e);
		}
		return null;
	}

	/***
	 * 失败的sim添加到失败缓存，由定时任务再次进行订阅
	 * @param ipPort
	 * @param sims
	 */
	public void addFailCache(String ipPort,List<Long> sims){
		try {
			if(sims!=null&&sims.size()>0){
				String ip_port=ipPort.replace(":", "_");
				List<Long> temp=DataSubscribeFailCache.instance().get(ip_port);
				int size=0;
				if(temp!=null){
					size=temp.size();
                    List<Long> temps = new ArrayList<Long>(temp);
					for(Long sim:sims){
						if(sim!=null&&!temp.contains(sim)){//不包含则存入缓存
							//temp.add(sim);
							temps.add(sim);				
						}		
					}
					temp=temps;
				}else{
					temp=sims;
				}
				if(temp.size()>size){//有新增终端时才进行缓存更新
					String ip_port_time=ip_port+"_"+(System.currentTimeMillis()/1000);
					DataSubscribeFailCache.instance().load(ip_port_time, temp);//添加到缓存
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("异常", e);
		}
	}
	

}
