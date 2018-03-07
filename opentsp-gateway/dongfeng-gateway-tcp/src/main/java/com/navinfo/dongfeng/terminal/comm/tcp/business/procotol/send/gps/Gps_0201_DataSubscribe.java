package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.lc.core.protocol.platform.LCDataSubscribe;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据订阅
 *
 */
@Component
public class Gps_0201_DataSubscribe extends Command {
	Logger logger = LoggerFactory.getLogger(Gps_0201_DataSubscribe.class);

	@Override
	public Object processor(Packet packet) {
		try {
			// 数据订阅
			LCDataSubscribe.DataSubscribe.Builder builder=LCDataSubscribe.DataSubscribe.newBuilder();
			// TODO 添加链路sim卡缓存
			List<Long> sims=new ArrayList<Long>(); 
			String cloudRpIp=packet.getTo().toString();
			log.info("0201>>cloudRpIp:" + cloudRpIp);
			if(packet.getParameter("Add")!=null&&packet.getParameter("Add").equals("true")&&packet.getObject("sims")!=null){//发送增量数据订阅，caozhen,20141218
				try {
					sims.addAll((List<Long>)packet.getObject("sims"));
					//增量订阅打印添加
					String s="";
					for(Long item:sims){
						s+=item+",";
					}
					log.info("0201>>Add>>sims:" + s);
				} catch (Exception e) {
					logger.error("异常", e);
				}
			}else{ //从缓存中获取终端标识
				Map<Long, String> carIp= CloudLinkCache.instance().getCarIpCache();
				for(Long sim:carIp.keySet()){
					if(carIp.get(sim).equals(cloudRpIp)){
						sims.add(sim);
//						if(sim.toString().contains("14429783368"))
//							System.err.println(sim);
					}
				}
			}
			
			builder.addAllTerminalIdentify(sims);
			packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
			packet.setCommand("0201");
			packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
			packet.setContentForBytes(builder.build().toByteArray());
			packet.setUniqueMark(CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify()+"");
			IoSession session=CloudLinkCache.instance().getcache(cloudRpIp);
			packet.setSession(session);
			if(session!=null){
				session.write(packet);
			}
			sendMsgToCloud(packet, "数据订阅请求");
//			log.error("0201>>cloudRpIp: "+cloudRpIp+",sims.size: "+sims.size()+",ServerIdentify"+CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify());
//			System.out.println("0201>>cloudRpIp: "+cloudRpIp+",sims.size: "+sims.size()+",ServerIdentify"+CloudLinkCache.instance().getCodesCache().get(0).getServerIdentify());
			//数据订阅返回后自动做对应批量位置查询
			if(packet.getParameter("Add")==null){//非增量订阅时查询
			packet.setCommand(Constant.BATCHLOCATIONQUERY);
			GpsCommandFactory.processor(packet);
			}
		} catch (Exception e) {
			log.error("数据订阅请求异常", e);
		}
		return null;
	}


}
