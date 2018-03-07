package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.terminal.monitor.LCTerminalStatusChangeNotify;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.SerialNumberExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//终端锁车状态变化通知
@Component
public class Gps_3270_TerminalStatusChangeNotify extends Command {
	Logger logger = LoggerFactory.getLogger(Gps_3270_TerminalStatusChangeNotify.class);
	@Override
	public Object processor(Packet packet) {
		try {
			// 流水号-链路缓存转化处理
			packet = SerialNumberExchange.serialNumberChange(packet);
			packet.setMsgType("06");
			String communication = packet.getUniqueMark().substring(1);
			LCTerminalStatusChangeNotify.TerminalStatusChangeNotify builder= LCTerminalStatusChangeNotify.TerminalStatusChangeNotify.parseFrom(packet.getContentForBytes());
/*********cz,20160714,undo		
			GpsSimCache.instance().addEcuLockStatue(communication, builder);
			if(!communication.equals("0")){
				HyEcuStatue hyEcuStatue = new HyEcuStatue();
				hyEcuStatue.setEcuStatue(builder.toByteArray());
				hyEcuStatue.setTerminalId(Long.parseLong(communication));
				HyCommonResponse res = new EcuStatueManageServiceImpl().saveOrUpdate(hyEcuStatue);
				if(!res.isSuccessOrFail()){
					new EcuStatueManageServiceImpl().saveOrUpdate(hyEcuStatue);
				}
			}
***********/			
//			sendMsgToClient(packet, "终端锁车状态变化通知应答");
		} catch (Exception e) {
			log.error("终端锁车状态变化通知应答异常", e);
		}
		return null;
	}
}
