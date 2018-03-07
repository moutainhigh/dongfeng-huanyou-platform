package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.platform.LCBatchLocationQueryRes;
import com.lc.core.protocol.platform.LCLocationObject;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 批量位置查询应答
 * @author baitao
 *
 */
@Component
public class Gps_1203_BatchLocationQueryRes extends Command {

//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
    
	@Override
	public Object processor(Packet packet) {
		try {
			LCBatchLocationQueryRes.BatchLocationQueryRes builder =
				LCBatchLocationQueryRes.BatchLocationQueryRes.parseFrom(packet.getContentForBytes());
			List<LCLocationObject.LocationObject>  LocationObjects  = builder.getLocationObjectList();
			
			if (null != LocationObjects && !LocationObjects.isEmpty()) {
			
				String sim = packet.getUniqueMark().substring(1);
//				kafkaTemplate.send("BatchLocationTP" , sim , LocationObjects);
				
//			if(LocationObjects != null && LocationObjects.size() > 0){
//				CarTerminalCache carCache = CarTerminalCache.instance();
//				for(LocationObject o:LocationObjects){
//					// 注：如果没有查询到终端位置数据，LocationData为空
//					if(o != null && o.getTerminalIdentify() > 0 && o.getLocationData() != null&& !o.getLocationData().toString().equals("")){
//					//  更新位置云的缓存GPS
//					GpsSimCache.instance().add(String.valueOf(o.getTerminalIdentify()), o.getLocationData());
//					// TODO 更新车辆入网时间及位置信息（偏转后的经纬度）
//					HyCarTerminal car = CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX,o.getTerminalIdentify()+"");
//					LocationData locationData=o.getLocationData();
//					//如果入网时间及位置信息为空则更新缓存并保存到数据库
//					if(car!=null){
//					if(car.getNettingLat()==null||car.getNettingLog()==null||car.getNettingTime()==null){
//						car.setNettingLat((long)locationData.getLatitude());
//						car.setNettingLog((long)locationData.getLongitude());
//						// 图吧-殷嗣华 BUG 2519 START
////						car.setNettingTime(locationData.getReceiveDate());
//						car.setNettingTime(locationData.getGpsDate());
//						// 图吧-殷嗣华 BUG 2519 END
//						carCache.add(car); //更新缓存
//						
//						HyCar hyCar=new HyCar();
//						hyCar.setCarId(car.getCarId());
//						hyCar.setNettingLat((long)locationData.getLatitude());
//						hyCar.setNettingLog((long)locationData.getLongitude());
//						// 图吧-殷嗣华 BUG 2519 START
////						hyCar.setNettingTime(locationData.getReceiveDate());
//						hyCar.setNettingTime(locationData.getGpsDate());
//						// 图吧-殷嗣华 BUG 2519 END
//						try {//更新数据库 caozhen,20151211,done
//							CarManageServiceImpl carService=new CarManageServiceImpl();
//							 carService.saveOrUpdateForBasic(hyCar, "2");//更新数据库
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}
//					}
//						}
//				}				
				
				
				
				
/***********cz,20160714,undo				
				CarTerminalCache carCache = CarTerminalCache.instance();
				for(LocationObject o:LocationObjects){
					// 注：如果没有查询到终端位置数据，LocationData为空
					if(o != null && o.getTerminalIdentify() > 0 && o.getLocationData() != null&& !o.getLocationData().toString().equals("")){
					//  更新位置云的缓存GPS
					GpsSimCache.instance().add(String.valueOf(o.getTerminalIdentify()), o.getLocationData());
					// TODO 更新车辆入网时间及位置信息（偏转后的经纬度）
					HyCarTerminal car = CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX,o.getTerminalIdentify()+"");
					LocationData locationData=o.getLocationData();
					//如果入网时间及位置信息为空则更新缓存并保存到数据库
					if(car!=null){
					if(car.getNettingLat()==null||car.getNettingLog()==null||car.getNettingTime()==null){
						car.setNettingLat((long)locationData.getLatitude());
						car.setNettingLog((long)locationData.getLongitude());
						car.setNettingTime(locationData.getReceiveDate());
						carCache.add(car); //更新缓存
						
						HyCar hyCar=new HyCar();
						hyCar.setCarId(car.getCarId());
						hyCar.setNettingLat((long)locationData.getLatitude());
						hyCar.setNettingLog((long)locationData.getLongitude());
						hyCar.setNettingTime(locationData.getReceiveDate());
						try {//更新数据库 caozhen,20151211,done
							CarManageServiceImpl carService=new CarManageServiceImpl();
							 carService.saveOrUpdateForBasic(hyCar, "2");//更新数据库
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					}
						}
				}
			**********/	
				
			}
		} catch (Exception e) {
			log.error("批量位置查询异常", e);
		}
		return null;
	}


}
