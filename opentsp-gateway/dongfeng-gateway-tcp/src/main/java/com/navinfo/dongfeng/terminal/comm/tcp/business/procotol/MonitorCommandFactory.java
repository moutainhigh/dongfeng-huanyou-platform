package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol;

/**
 * GPS指令解析类
 * @author baitao 
 * @common
 */

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.CommandCache;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MonitorCommandFactory {
	public static Logger logger = LoggerFactory.getLogger(MonitorCommandFactory.class);

	public static Object processor(Packet packet) {
		String cmd = Command.PrefixMonitor + packet.getCommand().toUpperCase();
		Command command = CommandCache.getInstance().getCommand(cmd);
		if (command != null) {
			try {
				return command.processor(packet);
			} catch (Exception e) {
			    logger.error("异常", e);
			    String commId =packet.getUniqueMark().substring(1);//平台通信号
				if(packet.getSession() != null){
					logger.error("["+packet.getSession()+" , "+commId+"]协议解析异常,指令号["+packet.getCommand()+"],源数据["+packet.getContent()+"]",e);
				}else{
					logger.error("["+commId+"]协议解析异常,指令号["+packet.getCommand()+"],源数据["+packet.getContent()+"]",e);
				}
				return null;
			}
			
		} else {
			logger.error("未找到指令[" + cmd + "]的协议解析类.");
			return null;
		}

	}
}
