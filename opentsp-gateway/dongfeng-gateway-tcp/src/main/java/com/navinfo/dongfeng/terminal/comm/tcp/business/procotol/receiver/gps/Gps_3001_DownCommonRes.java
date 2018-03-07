package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.terminal.LCDownCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.kafka.KafkaMessageChannel;
import com.navinfo.dongfeng.terminal.comm.kafka.command.KafkaCommand;
import com.navinfo.dongfeng.terminal.comm.mapper.system.instructlayin.HyInstructLayinMapper;
import com.navinfo.dongfeng.terminal.comm.model.system.instructlayin.HyInstructLayin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 下行消息的通用应答
 * Gps_3001_CommonRespDown
 * @author maojin
 *
 */
@Component
public class Gps_3001_DownCommonRes extends Command {

	@Autowired
	private KafkaMessageChannel kafkaMessageChannel;

	@Value("${opentsp.kafka.producer.qq.poscan.topic}")
	private String qqTopic;
	@Resource
	private HyInstructLayinMapper InstructLayinMapper;
	@Override
	public Object processor(Packet packet) {
		try {
			LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(packet.getContentForBytes());
			//下发指令的指令号
			String responseId = Convert.decimalToHexadecimal(res.getResponseId(), 4);
			System.out.println((new Date()).toString()+">>>>>"+packet.getUniqueMark()+">>>3001: responseId:"+responseId+"--"+"getResult:"+res.getResult());
			
			if(responseId.equals("2151")||responseId.equals("2258")||responseId.equals("2259")||responseId.equals("2268")||responseId.equals("2269")||responseId.equals("2325"))
			{
				String SerialNumber=String.valueOf(res.getSerialNumber());
				SaveReceiver_3001(packet,SerialNumber);
			}
			//推送至kafka
			KafkaCommand kafkaCommand = new KafkaCommand();
			kafkaCommand.setMessage(packet.getContentForBytes());
			kafkaCommand.setCommandId(packet.getCommand());
			kafkaCommand.setTopic(qqTopic);
			kafkaCommand.setKey(packet.getUniqueMark().substring(1));
			kafkaMessageChannel.send(kafkaCommand);
			log.info("kafka send to rp success !{}", packet.toString());
/*			String result = AnswerCache.instance().getBySerial(packet.getSerialNumber());
		    for(ResponseResult entry:constant.ResponseResult.values()){
		    	if(res.getResult().getNumber()==entry.getValue()){
		    		result=entry.getName();
		    	}
		    }
		    AnswerCache.instance().add(Convert.decimalToHexadecimal(res.getSerialNumber(),4), result);
		    Map<String, String> aa = AnswerCache.instance().get();
		    for(Entry<String, String> key:aa.entrySet()){
		       System.out.println("=="+key.getKey()+"=="+key.getValue());
		    }*/
//			HyTerminalLog hyTerminalLog=hyTerminalLogMapper.selectByPrimaryByLSH(packet.getSerialNumber());
	
/**********cz,20160714,undo		    
			if(responseId.equals("2319")&&res.getResult().getNumber()==0){
				log.error("收到2319指令，通信号=="+packet.getUniqueMark().substring(1));
				String communication = packet.getUniqueMark().substring(1);
				HyCarTerminal carTerminal=CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX, communication);
				HyCarAll car = CarCache.instance().getCarAll(String.valueOf(carTerminal.getCarId()));
				Long terminalid = carTerminal.getCarTerminal();
				HyTerminal terminal = CarCache.instance().getTerminal(terminalid+"");
			    if((terminal.gettStyle()==null&&res.getResult().getNumber()==0)||terminal.gettStyle()==1&&res.getResult().getNumber()==0){//一体机
    					if(car.getCarTerminalId()==null||car.getCarTerminalId()==0){ //终端对应车辆没有防拆盒
    						updateCarNotifyStatus(packet);
    					}
    			}else if((terminal.gettStyle()==0&&res.getResult().getNumber()==0)||
    						(terminal.gettStyle()==0&&res.getResult().getNumber()==7)){ //防拆盒
    					updateCarNotifyStatus(packet);
				}else{
					ActivationOrLockNotifyCache.instance().deleteBycarid(carTerminal.getCarId().toString());
				}
			}
			
			if(responseId.equals("2320")){
				log.error("收到2320指令，通信号=="+packet.getUniqueMark().substring(1));
				String communication = packet.getUniqueMark().substring(1);
				HyCarTerminal carTerminal=CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX, communication);
				HyCarAll car = CarCache.instance().getCarAll(String.valueOf(carTerminal.getCarId()));
				Long terminalid = carTerminal.getCarTerminal();
				HyTerminal terminal = CarCache.instance().getTerminal(terminalid+"");
				if((terminal.gettStyle()==null&&res.getResult().getNumber()==0)||terminal.gettStyle()==1&&res.getResult().getNumber()==0){//一体机
						if(car.getCarTerminalId()==null||car.getCarTerminalId()==0){ //终端对应车辆没有防拆盒
							cancleStatue(packet);
						}
				}else if((terminal.gettStyle()==0&&res.getResult().getNumber()==0)||
							(terminal.gettStyle()==0&&res.getResult().getNumber()==7)){ //防拆盒
						cancleStatue(packet);
				}else{
					ActivationOrLockNotifyCache.instance().deleteBycarid(carTerminal.getCarId().toString());
				}
				
			}
			
		    if(!responseId.equals("2057") && !responseId.equals("2056")&&!responseId.equals("2058")&&!responseId.equals("2134")&&!responseId.equals("2316")
					&&!responseId.equals("2067")&&!responseId.equals("2062")&&!responseId.equals("2063")&&!responseId.equals("2064")&&!responseId.equals("2065")
					&&!responseId.equals("2066")&&!responseId.equals("2067")&&!responseId.equals("2068")&&!responseId.equals("2069")
					&&!responseId.equals("2070")&&!responseId.equals("2071")&&!responseId.equals("2060")&&!responseId.equals("2170")){
				    packet=SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
					packet.setMsgType(constant.CLIENT_MESSAGE_TYPE);
					sendMsgToClient(packet, "下行消息通用应答"+res.getResult());
			}
************/			
		} catch (InvalidProtocolBufferException e) {
			log.error("下行消息的通用应答异常", e);
		}
		return null;
	}

	public void SaveReceiver_3001(Packet packet,String SerialNumber)
	{
		try
		{
			LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(packet.getContentForBytes());
//			String responseId = Convert.decimalToHexadecimal(res.getResponseId(), 4);
		
			HyInstructLayin instructlayin=new HyInstructLayin();
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			instructlayin.setReciveDate(format.format(date));
			if(res.getResult()== LCResponseResult.ResponseResult.success)
			{
				instructlayin.setInstructState("下发设置成功");
				instructlayin.setIssuccessOrfalse(1);
			}
			else
			{
				instructlayin.setInstructState("下发设置失败");
				instructlayin.setIssuccessOrfalse(2);
			}
			instructlayin.setInstructSerialnumber(SerialNumber);
//			InstructLayinMapper.updateByPrimaryKeySelective(instructlayin);
//			InstructLayinServiceImpl.updateByPrimaryKeySelective(instructlayin);
		}
		 catch (Exception e) {
				// TODO: handle exception
			 e.printStackTrace();
			}
	}
	
	private void cancleStatue(Packet packet) {
/******cz,20160714,undo		
		String unCommunication =packet.getUniqueMark().substring(1);
		String serialnumber = Convert.decimalToHexadecimal(Long.parseLong(packet.getAnswerSerialNumber()),4);
		HyCarTerminal carTerminal=CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX, unCommunication);
		if(carTerminal==null){
			return ;
		}
		HyCar car=new HyCar();
		car.setCarId(carTerminal.getCarId());
		CarLockBean entry = ActivationOrLockNotifyCache.instance().getByCarid(carTerminal.getCarId().toString());
		MessageType messagetype = new MessageType();
		if(entry!=null){
			messagetype.setAccountname(entry.getAccountName());
			HyAccount ac = new HyAccount();
			ac.setAccountName(entry.getAccountName());
			List<HyAccount> accountlist = new AccountManagerServiceImpl().selectByProperty(ac);
//			if(AccountCache.instance().getByName(mess.getAccountName())!=null){
				messagetype.setPassword(accountlist.get(0).getAccountPwd());
//			}
		}
		messagetype.setCarid(car.getCarId().toString());
		messagetype.setSign(1);
		InstructionImp instructionimp = (InstructionImp) SpringContextUtil.getBean("instructionImp");
		if(entry.getType()==2){   //if(entry.getType()!=1){
			car.setTamperNoticeStatus(1); //取消锁车通知
			HyCarResponse response = new CarManageServiceImpl().saveOrUpdateForBasic(car, "2");
			if (!response.isSuccessOrFail()) {// 失败后再次更新一次操作
				new CarManageServiceImpl().saveOrUpdateForBasic(car,"2");
			}
			List<MessageType> messagetypes= new ArrayList<MessageType>();
			messagetypes.add(messagetype);
			instructionimp.sendTamper(messagetypes.get(0).getAccountname(), messagetypes.get(0).getPassword(), messagetypes);
		}else if(entry.getType()==1){  //else{
			car.setTamperNoticeStatus(0); //取消激活通知
			HyCarResponse response = new CarManageServiceImpl().saveOrUpdateForBasic(car, "2");
			if (!response.isSuccessOrFail()) {// 失败后再次更新一次操作
				new CarManageServiceImpl().saveOrUpdateForBasic(car,"2");
			}
//			List<MessageType> messagetypes= new ArrayList<MessageType>();
//			messagetypes.add(messagetype);
//			new InstructionImp().sendTamper(messagetypes.get(0).getAccountname(), messagetypes.get(0).getPassword(), messagetypes);
			ActivationOrLockNotifyCache.instance().deleteBycarid(carTerminal.getCarId().toString());
		}
		
***********/		
	}

	private void updateCarNotifyStatus(Packet packet) {
/**********cz,20160714,undo		
		log.error("进入修改车辆通知状态=="+packet.getUniqueMark().substring(1));
		String unCommunication =packet.getUniqueMark().substring(1);
		String serialnumber = Convert.decimalToHexadecimal(Long.parseLong(packet.getAnswerSerialNumber()),4);
		HyCarTerminal carTerminal=CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX, unCommunication);
		if(carTerminal==null){
			return ;
		}
		HyCar car=new HyCar();
		car.setCarId(carTerminal.getCarId());
		HyCarAll c = CarCache.instance().getCarAll(String.valueOf(carTerminal.getCarId()));
		log.error("缓存中车辆通知状态为=="+c.getTamperNoticeStatus());
		if(c.getTamperNoticeStatus()==0){
			car.setTamperNoticeStatus(1); //激活通知
		}else if(c.getTamperNoticeStatus()==1){	
		    car.setTamperNoticeStatus(2); //锁车通知
		}
		HyCarResponse response = new CarManageServiceImpl().saveOrUpdateForBasic(car, "2");
		if (!response.isSuccessOrFail()) {// 失败后再次更新一次操作
			new CarManageServiceImpl().saveOrUpdateForBasic(car,"2");
		}
//		ActivationOrLockNotifyCache.instance().deleteBycarid(carTerminal.getCarId().toString());
 * 
 */
	}


}
