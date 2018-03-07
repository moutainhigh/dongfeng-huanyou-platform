package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.monitor;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCPlatformResponseResult;
import com.lc.core.protocol.platform.auth.LCServerLoginRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.task.LinkCloudMMServerTask;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.MonitorCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.springframework.stereotype.Component;

/**
 * 平台登录应答
 * 
 */
@Component
public class Monitor_1103_ServerLoginRes extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			LCServerLoginRes.ServerLoginRes res = LCServerLoginRes.ServerLoginRes
					.parseFrom(packet.getContentForBytes());
			if (res.getResults().equals(LCPlatformResponseResult.PlatformResponseResult.success)) {
				log.info("1103>>登录成功,session:" + packet.getSession().toString());
//				System.out.println("1103>>登录成功,session:"+packet.getSession().toString());
				//流程操作成功标识,caozhen,20151202 新增
				CloudServerStart cloudServerStart=(CloudServerStart)packet.getSession().getAttribute("cloudServerStart");
				if(cloudServerStart!=null){
					cloudServerStart.setIsSucces(1);
				}
				
				// 2.取得心跳间隔时间，设置到心跳线程中
				int interval = res.getInterval();
//				new Timer().schedule(new LinkCloudServerTask(), 0, interval*1000);
				LinkCloudMMServerTask linkcloudservertask=new LinkCloudMMServerTask();
				linkcloudservertask.stopTimer();//启动新的心跳线程时先关闭以前线程
				linkcloudservertask.getTimer().schedule(linkcloudservertask, 0, interval*1000);
				// 1. 添加车辆列表到缓存中，放在初始化缓存中进行，caozhen,done
//				HyCarTerminal carTerminal = null;
//				List<HyCarTerminal> carList = BaseDataManage.getIAccountManageService().selectCarAndSim();
//				if (null != carList && carList.size() > 0) {
//					for (int i = 0; i < carList.size(); i++) {
//						carTerminal = carList.get(i);
//						CarTerminalCache.instance().add(carTerminal);
//					}
//				}
				// 3.发送多服务鉴权请求0x0109
				packet.setCommand(Constant.MULTI_SERVER_AUTH_CLOUD);
				MonitorCommandFactory.processor(packet);
			} else {
				// 3.登录失败，记录错误日志
				log.error("流水号:[" + res.getSerialNumber() + "],登录失败:["
						+ res.getResults() + "],session:"+packet.getSession().toString());
//				System.out.println("流水号:[" + res.getSerialNumber() + "],登录失败:["
//						+ res.getResults() + "],session:"+packet.getSession().toString());
			}
		} catch (InvalidProtocolBufferException e) {
			log.error("解析ProtocolBuffer数据异常,原始数据：" + packet, e);
		} catch (Exception e) {
			log.error("平台登录应答", e);
		}
		return null;
	}

}
