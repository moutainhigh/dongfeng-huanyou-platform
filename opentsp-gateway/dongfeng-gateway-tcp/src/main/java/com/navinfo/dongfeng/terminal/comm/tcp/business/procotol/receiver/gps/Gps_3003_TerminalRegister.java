package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.kafka.KafkaMessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 终端注册
 * Gps_3003_TerminalRegister
 * @author baitao	
 *
 */
@Component
public class Gps_3003_TerminalRegister extends Command {
	//cz,20160714,undo
//	private CarManageServiceImpl carManageServiceImpl=new CarManageServiceImpl();
//	@Autowired
//	private IHyCarService iHyCarService;
	@Autowired
	private KafkaMessageChannel kafkaMessageChannel;

	@Value("${opentsp.kafka.producer.qq.poscan.topic}")
	private String qqTopic;
	@Override
	public Object processor(Packet packet) {
		try {
//			String sim = packet.getUniqueMark().substring(1);
//			// 解析注册数据
//			TerminalRegister terminalRegister=TerminalRegister.parseFrom(packet.getContentForBytes());
            // KafkaCommand kafkaCommand = new KafkaCommand();
            // kafkaCommand.setMessage(packet.getContentForBytes());
            // kafkaCommand.setCommandId(packet.getCommand());
            // kafkaCommand.setTopic(qqTopic);
            // kafkaCommand.setKey(packet.getUniqueMark().substring(1));
            // kafkaMessageChannel.send(kafkaCommand);
            // log.info("kafka send to rp success !{}", packet.toString());
			// 进行注册验证
/*******cz,20160714,undo			
			//TODO 储存数据
			HyTerminalRegister register=new HyTerminalRegister();
			if(terminalRegister.hasCityIdentify()){
				register.setCityidentify((long)terminalRegister.getCityIdentify());
			}
			if(terminalRegister.hasLicense()){
				register.setVehicleNo(terminalRegister.getLicense());
			}
			if(terminalRegister.hasLicenseColorCode()){
				register.setVehicleColor(terminalRegister.getLicenseColorCode());
			}
			if(terminalRegister.hasProduceCoding()){
				register.setProducecoding(terminalRegister.getProduceCoding());
			}
			if(terminalRegister.hasProvinceIdentify()){
				register.setProvinceidentify((long)terminalRegister.getProvinceIdentify());
			}
			if(terminalRegister.hasTerminalIdentify()){
				register.setTerminalidentify(terminalRegister.getTerminalIdentify());
			}
			if(terminalRegister.hasTerminalModel()){
				register.setTerminalmodel(terminalRegister.getTerminalModel());
			}
			if(terminalRegister.hasAuthCoding()){
				register.setAccesstocken(Long.parseLong(terminalRegister.getAuthCoding()));
			}
			if(terminalRegister.hasSim()){//20151008,caozhen,注册信息里添加车辆sim卡
				register.setTerminalSim(terminalRegister.getSim());
			}
			register.setSim(sim);
			//注册信息入库 caozhen,20151211,done
			HyCommonResponse  res= new TerminalRegisterServiceImpl().saveTerminalRegister(register, "1");
 **********/
			/**
			 * 车辆注册时间更新到车辆信息里
			 */
//		    if(!StringUtils.isEmpty(sim)){
//				Map<String, Object> queryMap=new HashMap<String, Object>();
//				queryMap.put("commId", Long.parseLong(sim));
//				//根据通讯号查询车辆信息
//				HyCar hyCar = iHyCarService.selectCarByComId(queryMap);
//				if(null!=hyCar&&null!=hyCar.getCarId()){
//					Map<String, Object> updateMap=new HashMap<String, Object>();
//					updateMap.put("regTime", System.currentTimeMillis()/1000);
//					updateMap.put("carId", hyCar.getCarId());
//					//根据车辆ID修改车辆的注册时间
//					iHyCarService.updateCarByPrimaryKey(updateMap);
//				}
//			}

			// 发送809
//			Packet packet809=new Packet();
//			packet809.setCommand("5107");
//			packet809.setUniqueMark(packet.getUniqueMark());
//			packet809.addParameter("vehicleNo", register.getVehicleNo());
//			packet809.addParameter("vehicleColor", register.getVehicleColor()+"");
//			packet809.addParameter("produceId", register.getProvinceidentify()+"");
//			packet809.addParameter("terminalModelType", register.getTerminalmodel());
//			packet809.addParameter("TerminalIdentify", register.getTerminalidentify());
//			packet809.addParameter("authCoding", register.getAccesstocken()+"");
//			GpsCommandFactory.processor(packet809);
		} catch (Exception e) {
			log.error("终端注册异常", e);
		}
		
		return null;
	}


}
