package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCPlatformResponseResult;
import com.lc.core.protocol.platform.auth.LCServerLoginRes;
import com.navinfo.dongfeng.terminal.comm.cache.RPNodeDataCache;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.task.LinkCloudServerTask;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 平台登录应答
 * 
 */
@Component
public class Gps_1103_ServerLoginRes extends Command {
//	private static boolean isInitDailyMileage=false;
	@Autowired
	private RPNodeDataCache rPNodeDataCache;

	@Override
	public Object processor(Packet packet) {
		try {
			LCServerLoginRes.ServerLoginRes res = LCServerLoginRes.ServerLoginRes
					.parseFrom(packet.getContentForBytes());
			if (res.getResults().equals(LCPlatformResponseResult.PlatformResponseResult.success)) {
				//流程操作成功标识,caozhen,20151202 新增
				CloudServerStart cloudServerStart=(CloudServerStart)packet.getSession().getAttribute("cloudServerStart");
				if(cloudServerStart!=null){
					cloudServerStart.setIsSucces(1);
				}

				log.info("1103>>登录成功,session:" + packet.getSession().toString());
//				System.out.println("1103>>登录成功,session:"+packet.getSession().toString());
				String RPServerAddress = packet.getSession().getRemoteAddress().toString().substring(1);
				CloudLinkCache.instance().getRpIslogin().put(RPServerAddress, true);
				// 取得心跳间隔时间，设置到心跳线程中,和位置云总MM使用相同才心跳任务，时间间隔也一样，caozhen，20151119
				int interval = res.getInterval();
				LinkCloudServerTask linkcloudservertask=new LinkCloudServerTask(rPNodeDataCache);
				linkcloudservertask.stopTimer();//启动新的心跳线程时先关闭以前线程
				linkcloudservertask.getTimer().schedule(linkcloudservertask, 0, interval*1000);
				//成功订阅数据
				Packet packet1=new Packet();
				packet1.setTo(RPServerAddress);
				packet1.setCommand(Constant.DATASUBSCRIBE);
				GpsCommandFactory.processor(packet1);
				
//				try{
//					if(!isInitDailyMileage){
//					//加载当日里程初始化数据
//					GpsSimCache.instance().initDailyMileageCache();
//					isInitDailyMileage=true;
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//					log.error("加载当日里程初始化数据发生异常" +e);
//				}
				
			} else {
				// 3.登录失败，记录错误日志
				log.error("流水号:[" + res.getSerialNumber() + "],登录失败:["
						+ res.getResults() +  "],session:"+packet.getSession().toString());
//				System.out.println("流水号:[" + res.getSerialNumber() + "],登录失败:["
//						+ res.getResults() +  "],session:"+packet.getSession().toString());
			}
		} catch (InvalidProtocolBufferException e) {
			log.error("解析ProtocolBuffer数据异常,原始数据：" + packet, e);
		}catch (Exception e) {
			log.error("平台登录应答", e);
		}
		return null;
	}


}
