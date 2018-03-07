package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.springframework.stereotype.Component;

/**
 * 4.5.3	服务站点评通知
 * 
 */
@Component
public class Gps_2522_ServerStationComment extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			String sim = null;
			if(packet.getUniqueMark() != null){
				sim = packet.getUniqueMark().substring(1);
			}
			log.error("收到服务出站推送通知"+sim);
/******	cz,20160714,undo		
			HyCarTerminal car = CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX,sim);
************/			
			// APP通知开关 todo
//			AppParams app = new AppParams();
//			app.setCarId(car.getCarId());
//			String flag = "1";
//			String sound = "1";
//			List<AppParams> apps = BaseDataManage.getIAppParamsService().selectByProperty(app);
//			if(apps != null && apps.size() > 0){
//				 flag = apps.get(0).getParamValues().split(",")[0];
//				 flag = apps.get(0).getParamValues().split(",")[1];
//			}
//				if("1".equals(flag)){
//			ServerStationComment serverStationComment = ServerStationComment.parseFrom(packet.getContentForBytes());
////			long date = serverStationComment.getArrivingDate();
//			IServiceStationManageService ser =BaseDataManage.getIIServiceStationManageService();
//			long stationId = serverStationComment.getAreaId();
//			log.error("服务出站ID"+stationId);
//			List<HyServiceStationAppointment> stations = ser.queryAppointmentByCar(car.getCarId(), stationId);
//			if(car != null && stations != null && stations.size() > 0){
//				//如果开关打开,推送给APP
//				XingeApp push = XinAppBean.instance();
//				Message mess = new Message(); //$mess = new MessageIOS();
//				//完善 Message 消息
//				mess.setTitle("appointment");
//				mess.setType(Message.TYPE_MESSAGE);
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("stationId", stationId);
//				map.put("appointmentName", stations.get(0).getAppointmentName());
//				map.put("appointmentDate", stations.get(0).getAppointmentTime());
//				map.put("appointmentType", stations.get(0).getAppointmentType());
//				map.put("content", stations.get(0).getServiceContent());
//				map.put("detail", stations.get(0).getPartsContent());
//				map.put("common", stations.get(0).getAppointmentComment());
//				map.put("stationName", stations.get(0).getStationName());
//				map.put("serviceType", stations.get(0).getServiceType());
//				map.put("phone", stations.get(0).getPhone());
//				mess.setCustom(map);
//				if("1".equals(sound)){
//					mess.setStyle(new Style(0,1,0,1,0));
//				}
//				log.error(push.pushSingleAccount(0,car.getCarCph(), mess)+"出站发送到车辆"+car.getCarCph());
//			}
//			// 回复通用应答
//			packet.setCommand("1100");
//			packet.setMsgType(constant.CLOUD_MESSAGE_TYPE);
//			this.sendMsgToCloud(packet, "位置云消息通用应答");
//			}else{
//				return null;
//			}
		} catch (Exception e) {
			log.error("平台通用应答异常", e);
		}
		return null;
	}

}
