package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.lc.core.protocol.platform.LCBatchLocationQuery;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 批量位置查询
 *
 *位置云提供批量位置查询，
 *根据数据订阅指定的提供终端数据的服务来查询位置数据；
 *寰游服务端提供最新位置数据缓存，
 *为寰游客户端提供批量位置查询
 */
@Component
public class Gps_0203_BatchLocationQuery  extends Command {

	public static int Max=2000;
	@Override
	public Object processor(Packet packet) {
		try {
			String cloudRpIp=packet.getTo().toString();
//			log.info("0203>>cloudRpIp:"+cloudRpIp);
//			System.out.println("0203>>cloudRpIp:"+cloudRpIp);
			Map<Long, String> carIp= CloudLinkCache.instance().getCarIpCache();
			List<Long> sims=new ArrayList<Long>();
			for(Long sim:carIp.keySet()){
				if(carIp.get(sim).equals(cloudRpIp)){
					// 终端唯一标识
//					batchLocationQuery.addTerminalIdentify(sim);
					sims.add(sim);
				}
			}
			//末次位置查询批量多次查询
			
			int times=sims.size()/Max;
			for(int i=0;i<=times;i++){
				LCBatchLocationQuery.BatchLocationQuery.Builder batchLocationQuery = LCBatchLocationQuery.BatchLocationQuery.newBuilder();
				for(int k=i*Max;(k < (i+1)*Max) && (k<sims.size());k++){
					batchLocationQuery.addTerminalIdentify(sims.get(k));
				}
				packet.setContentForBytes(batchLocationQuery.build().toByteArray());
				// 批量位置查询
				packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
				packet.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
				sendMsgToCloud(packet, "批量位置查询");
			}
		} catch (Exception e) {
			log.error("批量位置查询异常", e);
		}
		return null;
	}


}
