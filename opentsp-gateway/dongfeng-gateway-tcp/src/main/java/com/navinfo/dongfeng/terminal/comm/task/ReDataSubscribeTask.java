package com.navinfo.dongfeng.terminal.comm.task;

import com.navinfo.dongfeng.terminal.comm.cache.DataSubscribeFailCache;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/***
 * 订阅失败终端重新订阅
 * @author Administrator
 *
 */
@Component
public class ReDataSubscribeTask {
	private static final Logger log = LoggerFactory
			.getLogger(ReDataSubscribeTask.class);

	@Scheduled(fixedDelay = 60000, initialDelay = 75000)
	public void execute() throws InterruptedException {
		try {
			int times=5;
			Map<String, List<Long>> map= DataSubscribeFailCache.instance().getCache();
			if(map!=null&&map.size()>0){
				for(String key:map.keySet()){
					String[] arr=key.split("_");
					if(Long.parseLong(arr[2])+times*60 < System.currentTimeMillis()/1000){//超过指定时间不再进行重新订阅，并清除缓存
						DataSubscribeFailCache.instance().delByKey(key);
						continue;
					}else{
						addDataSubscribe(arr[0], arr[1], map.get(key));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("订阅失败终端重新订阅定时任务异常", e);
		}
	}
	/*****
	 * 增量数据订阅
	 */
	public void addDataSubscribe(String rpIp,String rpPort,List<Long> sims){
		//数据订阅请求发送
		Packet packet1=new Packet();
		packet1.setTo(rpIp+":"+rpPort);
		packet1.setCommand("0201");
		packet1.addParameter("Add", "true");
		packet1.addObject("sims", sims);
		GpsCommandFactory.processor(packet1);
	}
}
