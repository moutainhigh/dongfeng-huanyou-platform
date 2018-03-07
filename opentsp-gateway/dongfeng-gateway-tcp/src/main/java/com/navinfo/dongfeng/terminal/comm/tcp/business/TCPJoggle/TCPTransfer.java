package com.navinfo.dongfeng.terminal.comm.tcp.business.TCPJoggle;

import com.lc.core.protocol.common.LCMessageSign;
import com.lc.core.protocol.terminal.monitor.LCDispatchMessage;
import com.lc.core.protocol.terminal.setting.LCAlarmCancelOrNot;
import com.lc.core.protocol.terminal.setting.parameter.LCFatigueDriving;
import com.lc.core.protocol.terminal.setting.parameter.LCOvertimeParking;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.model.system.instructlayin.HyInstructLayin;
import com.navinfo.dongfeng.terminal.comm.services.IHyInstructLayinService;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service(value = "tCPTransfer")
public class TCPTransfer {
	
	@Resource
	private IHyInstructLayinService nstructLayinService;
	protected final static Logger log = LoggerFactory.getLogger(TCPTransfer.class);
	/**
	 * 疲劳驾驶报警 
	 * @param sessionId 操作人的sessionid
	 * @param TXNumber 通讯号
	 * @param continueDrivingTime 连续驾驶时间门限，单位秒 
	 * @param dayCumulativeDrivingTime 当天累计驾驶时间门限，单位秒
	 * @param minRestingTime 最小休息时间，单位秒
	 * @param warningFatigue 疲劳驾驶预警差值，单位千米/小时
	 */
	public void FatigueDriving_2258(String sessionId,List<String> TXNumber,int continueDrivingTime,int dayCumulativeDrivingTime,int minRestingTime,int warningFatigue)
	{
		for(int i=0;i<TXNumber.size();i++)
		{
			try
			{
				LCFatigueDriving.FatigueDriving.Builder bulider =  LCFatigueDriving.FatigueDriving.newBuilder();
				bulider.setContinueDrivingTime(continueDrivingTime);
				bulider.setDayCumulativeDrivingTime(dayCumulativeDrivingTime);
				bulider.setMinRestingTime(minRestingTime);
				bulider.setWarningFatigue(warningFatigue);
				
				Packet packet=new Packet();
				packet.setCommand("2258");
				packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
				packet.setContentForBytes(bulider.build().toByteArray());
				packet.setUniqueMark("0"+TXNumber.get(i));
				GpsCommandFactory.processor(packet);
				
				HyInstructLayin instructlayin=new HyInstructLayin();
				instructlayin.setTerminalNum(TXNumber.get(i));
				instructlayin.setInstructNum("2258");
				instructlayin.setInstructContent("疲劳驾驶报警设置");
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				instructlayin.setInstructDate(format.format(date));
				instructlayin.setInstructState("正在下发设置指令");
				instructlayin.setInstructSerialnumber(String.valueOf(Integer.parseInt(packet.getSerialNumber(),16)));
				instructlayin.setIssuccessOrfalse(0);
				instructlayin.setSessionId(sessionId);
				nstructLayinService.insertSelective(instructlayin);
			}
			 catch (Exception e) {
					// TODO: handle exception
                 log.error(e.getMessage(), e);
				}
		}
	}
	
	/**
	 * 取消疲劳驾驶报警
	 * @param TXNumber 通讯号
	 */
	public void FatigueDrivingQ_2259(String sessionId,List<String> TXNumber)
	{
	
		for(int i=0;i<TXNumber.size();i++)
		{
			try
			{
				LCFatigueDriving.FatigueDriving.Builder bulider =  LCFatigueDriving.FatigueDriving.newBuilder();
				bulider.setContinueDrivingTime(0xFFFFFFFF);
				bulider.setDayCumulativeDrivingTime(0xFFFFFFFF);
				bulider.setMinRestingTime(0xFFFFFFFF);
				bulider.setWarningFatigue(0xFFFFFFFF);
				
				Packet packet=new Packet();
				packet.setCommand("2259");
				packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
				packet.setContentForBytes(bulider.build().toByteArray());
				packet.setUniqueMark("0"+TXNumber.get(i));
				GpsCommandFactory.processor(packet);
				
				HyInstructLayin instructlayin=new HyInstructLayin();
				instructlayin.setTerminalNum(TXNumber.get(i));
				instructlayin.setInstructNum("2259");
				instructlayin.setInstructContent("取消疲劳驾驶报警设置");
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				instructlayin.setInstructDate(format.format(date));
				instructlayin.setInstructState("正在下发取消指令");
				instructlayin.setInstructSerialnumber(String.valueOf(Integer.parseInt(packet.getSerialNumber(),16)));
				instructlayin.setSessionId(sessionId);
				nstructLayinService.insertSelective(instructlayin);
			}
			 catch (Exception e) {
					// TODO: handle exception
                 log.error(e.getMessage(), e);
				}
		}
	}
	
	/**
	 * 超时停车报警
	 * @param sessionId 操作人的sessionid
	 * @param TXNumber 通讯号
	 * @param parkingLimit 超时停车阀值
	 */
	public void OverTimeStop_2268(String sessionId,List<String> TXNumber,int parkingLimit)
	{
		
		for(int i=0;i<TXNumber.size();i++)
		{
			try
			{
				LCOvertimeParking.OvertimeParking.Builder bulider =  LCOvertimeParking.OvertimeParking.newBuilder();
				bulider.setParkingLimit(parkingLimit);
				
				Packet packet=new Packet();
				packet.setCommand("2268");
				packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
				packet.setContentForBytes(bulider.build().toByteArray());
				packet.setUniqueMark("0"+TXNumber.get(i));
				GpsCommandFactory.processor(packet);
				
				HyInstructLayin instructlayin=new HyInstructLayin();
				instructlayin.setTerminalNum(TXNumber.get(i));
				instructlayin.setInstructNum("2268");
				instructlayin.setInstructContent("超时停车报警设置");
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				instructlayin.setInstructDate(format.format(date));
				instructlayin.setInstructState("正在下发设置指令");
				instructlayin.setInstructSerialnumber(String.valueOf(Integer.parseInt(packet.getSerialNumber(),16)));
				instructlayin.setIssuccessOrfalse(0);
				instructlayin.setSessionId(sessionId);
				nstructLayinService.insertSelective(instructlayin);
			}
		 catch (Exception e) {
				// TODO: handle exception
             log.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 取消超时停车报警
	 * @param sessionId 操作人的sessionid
	 * @param TXNumber 通讯号
	 * @param parkingLimit 超时停车阀值
	 */
	public void OverTimeStopQ_2269(String sessionId,List<String> TXNumber,int parkingLimit)
	{
		for(int i=0;i<TXNumber.size();i++)
		{
			try
			{
				LCOvertimeParking.OvertimeParking.Builder bulider =  LCOvertimeParking.OvertimeParking.newBuilder();
				bulider.setParkingLimit(0xFFFFFFFF);
				
				Packet packet=new Packet();
				packet.setCommand("2269");
				packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
				packet.setContentForBytes(bulider.build().toByteArray());
				packet.setUniqueMark("0"+TXNumber.get(i));
				GpsCommandFactory.processor(packet);
				
				HyInstructLayin instructlayin=new HyInstructLayin();
				instructlayin.setTerminalNum(TXNumber.get(i));
				instructlayin.setInstructNum("2269");
				instructlayin.setInstructContent("取消超时停车报警设置");
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				instructlayin.setInstructDate(format.format(date));
				instructlayin.setInstructState("正在下发取消指令");
				instructlayin.setInstructSerialnumber(String.valueOf(Integer.parseInt(packet.getSerialNumber(),16)));
				instructlayin.setIssuccessOrfalse(0);
				instructlayin.setSessionId(sessionId);
				nstructLayinService.insertSelective(instructlayin);
			}
		 catch (Exception e) {
				// TODO: handle exception
             log.error(e.getMessage(), e);
			}
			}
	}
	
	/**
	 * 调度短信
	 * @param sessionId 操作人的sessionid
	 * @param TXNumber 通讯号
	 * @param messageContent 信息内容
	 * @param MessageSign1
	 */
	public void OverTimeStopQ_2151(String sessionId,List<String> TXNumber,String messageContent,MessageSign1 MessageSign1)
	{
		
		for(int i=0;i<TXNumber.size();i++)
		{
			try
			{
				LCDispatchMessage.DispatchMessage.Builder bulider =  LCDispatchMessage.DispatchMessage.newBuilder();
				LCMessageSign.MessageSign.Builder sign= LCMessageSign.MessageSign.newBuilder();
				sign.setIsUrgent(false);
				if(MessageSign1.isIsDisplay()==true)
				{
					sign.setIsDisplay(true);
				}
				else
				{
					sign.setIsDisplay(false);
				}
				if(MessageSign1.isBroadcast==true)
				{
					sign.setIsBroadcast(true);
				}
				else
				{
					sign.setIsBroadcast(false);
				}
				if(MessageSign1.isAdvertiseScreen==true)
				{
					sign.setIsAdvertiseScreen(true);
				}
				else
				{
					sign.setIsAdvertiseScreen(false);
				}
				sign.setInfoType(false);
				bulider.setMessageContent(messageContent);
				bulider.setSigns(sign);
				
				Packet packet=new Packet();
				packet.setCommand("2151");
				packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
				packet.setContentForBytes(bulider.build().toByteArray());
				packet.setUniqueMark("0"+TXNumber.get(i));
				GpsCommandFactory.processor(packet);
				
				HyInstructLayin instructlayin=new HyInstructLayin();
				instructlayin.setTerminalNum(TXNumber.get(i));
				instructlayin.setInstructNum("2151");
				instructlayin.setInstructContent("调度短信设置");
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				instructlayin.setInstructDate(format.format(date));
				instructlayin.setInstructState("正在下发设置指令");
				instructlayin.setInstructSerialnumber(String.valueOf(Integer.parseInt(packet.getSerialNumber(),16)));
				instructlayin.setIssuccessOrfalse(0);
				instructlayin.setSessionId(sessionId);
				nstructLayinService.insertSelective(instructlayin);
		}
		 catch (Exception e) {
				// TODO: handle exception
             log.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * @param isDisplay：终端显示器显示
	 * @param isBroadcast：终端TTS播报  
	 * @param isAdvertiseScreen：广告屏显示
	 */
	public class MessageSign1
	{
		private Boolean isDisplay=false;
		private Boolean isBroadcast=false;
		private Boolean isAdvertiseScreen=false;
		
		public boolean isIsDisplay() {
			return isDisplay;
		}

		public void setIsDisplay(boolean isDisplay) {
			this.isDisplay = isDisplay;
		}
		
		public boolean isIsBroadcast() {
			return isBroadcast;
		}

		public void setIsBroadcast(boolean isBroadcast) {
			this.isBroadcast = isBroadcast;
		}
		
		public boolean isIsAdvertiseScreen() {
			return isAdvertiseScreen;
		}

		public void setIsAdvertiseScreen(boolean isAdvertiseScreen) {
			this.isAdvertiseScreen = isAdvertiseScreen;
		}
	}
	
	/**
	 * 报警撤销和恢复
	 * @param sessionId 操作人的sessionid
	 * @param TXNumber 通讯号
	 * @param continueDrivingTime 连续驾驶时间门限，单位秒 
	 * @param isCancel 是否撤销：true：撤销；false：恢复
	 */
	public void AlarmCancelOrNot_2325(String sessionId,List<String> TXNumber,boolean isCancel)
	{
		String iscance="";
		if(isCancel==true)
		{
			iscance="撤销";
		}
		else
		{
			iscance="恢复";
		}
		for(int i=0;i<TXNumber.size();i++)
		{
			try
			{
				LCAlarmCancelOrNot.AlarmCancelOrNot.Builder bulider =  LCAlarmCancelOrNot.AlarmCancelOrNot.newBuilder();
				bulider.setAlarmType(0x080000);
				bulider.setIsCancel(!isCancel);
				
				Packet packet=new Packet();
				packet.setCommand("2325");
				packet.setSerialNumber(IDFactory.builderID(IDFactory.IDType.SerialNumber).toString());
				packet.setContentForBytes(bulider.build().toByteArray());
				packet.setUniqueMark("0"+TXNumber.get(i));
				GpsCommandFactory.processor(packet);
				
				HyInstructLayin instructlayin=new HyInstructLayin();
				instructlayin.setTerminalNum(TXNumber.get(i));
				instructlayin.setInstructNum("2325");
				instructlayin.setInstructContent("报警"+iscance);
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				instructlayin.setInstructDate(format.format(date));
				instructlayin.setInstructState("正在下发"+iscance+"指令");
				instructlayin.setInstructSerialnumber(String.valueOf(Integer.parseInt(packet.getSerialNumber(),16)));
				instructlayin.setIssuccessOrfalse(0);
				instructlayin.setSessionId(sessionId);
				nstructLayinService.insertSelective(instructlayin);
			}
			 catch (Exception e) {
					// TODO: handle exception
                 log.error(e.getMessage(), e);
				}
		}
	}
}
