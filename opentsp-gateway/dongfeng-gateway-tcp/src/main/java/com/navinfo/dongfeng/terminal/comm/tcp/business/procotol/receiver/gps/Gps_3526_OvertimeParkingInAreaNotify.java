package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.terminal.setting.LCOvertimeParkingInAreaNotify;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Gps_3526_OvertimeParkingInAreaNotify extends Command {
	Logger logger = LoggerFactory.getLogger(Gps_3526_OvertimeParkingInAreaNotify.class);
	//cz,20160714,undo
//	AppointmentServiceImpl appointmentServiceImpl=new AppointmentServiceImpl();
//	ReportImp reportImp =new ReportImp();
	@Override
	public Object processor(Packet packet) {
		try {
			String sim = packet.getUniqueMark().substring(1);
			LCOvertimeParkingInAreaNotify.OvertimeParkingInAreaNotify retentionNotify= LCOvertimeParkingInAreaNotify.OvertimeParkingInAreaNotify.parseFrom(packet.getContentForBytes());
			
			Long aeroId=retentionNotify.getAreaIdentify();
			//解析通知结果，更新车辆进站开始服务状态，caozhen,20160426
			//根据通讯号获取车辆id
/******cz,20160714,undo			
			 HyCarTerminal  carTerminal= CarTerminalCache.instance().getBySim(Long.parseLong(sim));
			 if(carTerminal!=null){
				 Long carId=carTerminal.getCarId();
				 Map<String,Object> map=new HashMap<String, Object>();
				 map.put("stationId", aeroId);
				 map.put("carId", carId);
				 List<HyAppointment> appointments = appointmentServiceImpl.selectByTimeRange(map);
				 if(appointments!=null && appointments.size()>0){//
					 reportImp.changeAppointmentStatus(appointments.get(0).getAppointmentId().toString(), 3);//更新预约状态
				 }
			 }
*********/
		} catch (Exception e) {
			logger.error("异常", e);
		}
		return null;
	}


}
