package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * 4.5.3	鏈嶅姟绔欑偣璇勯€氱煡
 * 
 */
@Component
public class Gps_2521_BroadcastInfoNotice extends Command {

	private static final Log logger = LogFactory.getLog(Gps_2521_BroadcastInfoNotice.class);

//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
    
	@Override
	public Object processor(Packet packet) {
		try {
			
			String sim = null;
			
			if (null != packet.getUniqueMark()) {
				sim = packet.getUniqueMark().substring(1);
			}
			
			if (null == sim || "".equals(sim.trim())) {
				return null;
			}
			
//			Gps_2521_BroadcastInfoNoticeTopic topic = new Gps_2521_BroadcastInfoNoticeTopic();
//
//			topic.setSim(sim);
//			topic.setData(packet.getContentForBytes());

//			kafkaTemplate.send("Gps_2521_BroadcastInfoNotice", sim, topic);
			
			
			
			
			
/*			String sim = null;
			if(packet.getUniqueMark() != null){
				sim = packet.getUniqueMark().substring(1);
			}
			log.error("鏀跺埌鏈嶅姟杩涚珯鎺ㄩ€侀€氱煡"+sim);
*//****cz,20160714,undo			
		HyCarTerminal car = CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX,sim);
		// APP閫氱煡寮€鍏?		AppParams app = new AppParams();
		app.setCarId(car.getCarId());
*******//*		
		String flag = "1";
		String sound = "1";
		//todo
//		List<AppParams> apps = BaseDataManage.getIAppParamsService().selectByProperty(app);
//		if(apps != null && apps.size() > 0){
//			 flag = apps.get(0).getParamValues().split(",")[0];
//			 sound = apps.get(0).getParamValues().split(",")[1];
//		}
			if("1".equals(flag)){
				BroadcastInfoNotice broadcastInfoNotice = BroadcastInfoNotice.parseFrom(packet.getContentForBytes());
				//鍖哄煙
				long areaId = broadcastInfoNotice.getAreaIdentify();
				log.error("鏈嶅姟杩涚珯ID"+areaId);
				//鎾姤鍐呭 todo
				String content = broadcastInfoNotice.getBroadcastContent();
//				IServiceStationManageService ser =BaseDataManage.getIIServiceStationManageService();
//				HyServiceStation  station = new HyServiceStation();
//				station.setId(areaId);
//			    station = ser.selectById(station);
//				if(car != null && station != null ){
//				   //鎺ㄩ€?//					XingeApp push = XinAppBean.instance();
//					Message mess = new Message(); //$mess = new MessageIOS();
//					//瀹屽杽 Message 娑堟伅
//					mess.setTitle("station");
//					mess.setType(Message.TYPE_MESSAGE);
//					Map<String,Object> map = new HashMap<String,Object>();
//					map.put("content", content);
//					map.put("stationName", station.getStationName() );
//					map.put("address", station.getAddress());
//					map.put("telphone", station.getPhone());
//					map.put("starLevel", station.getStarLever());
//					mess.setCustom(map);
//					if("1".equals(sound)){
//						mess.setStyle(new Style(0,1,0,1,0));
//					}
//					log.error(push.pushSingleAccount(0,car.getCarCph(), mess)+"杩涚珯鍙戦€佸埌杞﹁締"+car.getCarCph());
//				}
				// 鍥炲閫氱敤搴旂瓟
				packet.setCommand("1100");
				packet.setMsgType(constant.CLOUD_MESSAGE_TYPE);
//				this.sendMsgToCloud(packet, "浣嶇疆浜戞秷鎭€氱敤搴旂瓟"); //caozhen,娉ㄩ噴锛?0160331
			}else{
				return null;
			}*/
		} catch (Exception e) {
			log.error("骞冲彴閫氱敤搴旂瓟寮傚父", e);
		}
		return null;
	}

	
}
