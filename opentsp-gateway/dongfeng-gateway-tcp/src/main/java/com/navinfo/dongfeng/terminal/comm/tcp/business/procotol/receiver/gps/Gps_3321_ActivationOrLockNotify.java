package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.springframework.stereotype.Component;

/*******
 * 激活/锁车通知
 * 消息回复上行消息通用应答（0x2001）
 * @author Administrator
 *
 */
@Component
public class Gps_3321_ActivationOrLockNotify extends Command {
	
	@Override
	public Object processor(Packet packet) {
		try {
			String communication = packet.getUniqueMark().substring(1);
/*******cz,20160714,undo			
			HyCarTerminal carTerminal=CarTerminalCache.instance().getBySim(Long.parseLong(communication));
			HyCarAll car = CarCache.instance().getCarAll(String.valueOf(carTerminal.getCarId()));
			CarLockBean mess = ActivationOrLockNotifyCache.instance().getByCarid(car.getCarId().toString());
			ActivationOrLockNotify builder=ActivationOrLockNotify.parseFrom(packet.getContentForBytes());
			MessageType messagetype = new MessageType();
			if(mess!=null){
				messagetype.setAccountname(mess.getAccountName());
				HyAccount ac = new HyAccount();
				ac.setAccountName(mess.getAccountName());
				List<HyAccount> accountlist = new AccountManagerServiceImpl().selectByProperty(ac);
				//				if(AccountCache.instance().getByName(mess.getAccountName())!=null){
					messagetype.setPassword(accountlist.get(0).getAccountPwd());
//				}
			}
			messagetype.setCarid(car.getCarId().toString());
			messagetype.setSign(0);
			
			InstructionImp instructionimp = (InstructionImp) SpringContextUtil.getBean("instructionImp");
			if(builder.getStatus()==0&&(car.getLockStauts()==null||car.getLockStauts()==0)){//激活   //if(builder.getStatus()==0){
				messagetype.setDataCode("0");
				List<MessageType> messagetypes= new ArrayList<MessageType>();
				messagetypes.add(messagetype);
				instructionimp.sendTamper(messagetype.getAccountname(), messagetype.getPassword(), messagetypes);
//				mess.setType(2);
//				new InstructionImp().activationOrLockNotify(car.getCarId().toString(), mess, mess.getAccountName());
			}else if(builder.getStatus()==1&&car.getLockStauts()==2){ //锁车 //if(builder.getStatus()==1){
				messagetype.setRev("1000");
				List<MessageType> messagetypes= new ArrayList<MessageType>();
				messagetypes.add(messagetype);
				instructionimp.sendLockCar(messagetype.getAccountname(), messagetype.getPassword(), messagetypes);
			}
			packet.setMsgType(constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "激活/锁车通知");
**********/			
		} catch (Exception e) {
			log.error("激活/锁车通知", e);
		}
		return null;
	}
}
