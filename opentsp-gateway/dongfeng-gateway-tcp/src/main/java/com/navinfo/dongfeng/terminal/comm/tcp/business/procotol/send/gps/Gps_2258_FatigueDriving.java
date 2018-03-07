package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.lc.core.protocol.terminal.setting.parameter.LCFatigueDriving;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.springframework.stereotype.Component;

/****
 * 疲劳驾驶报警
 * 消息回复下行消息的通用应答(0x3001)
 * @author caozhen
 *
 */
@Component
public class Gps_2258_FatigueDriving extends Command {

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			this.sendMsgToCloud(packet, "疲劳驾驶报警");
		} catch (Exception e) {
			log.error("疲劳驾驶报警异常", e);
		}
		return null;
	}
	
	/****
	 * 发送疲劳驾驶2258
	 * 消息回复下行消息的通用应答(0x3001)
	 * @author chenhao
	 * @continueDrivingTime 连续驾驶时间门限，单位秒
	 * @dayCumulativeDrivingTime 当天累计驾驶时间门限，单位秒
	 * @minRestingTime 最小休息时间，单位秒
	 * @warningFatigue 疲劳驾驶预警差值，单位千米/小时
	 * @sim 为终端唯一标识(如TERMINAL_ID或TERMINAL)
	 */
	public void SendFatigueDriving(int continueDrivingTime,int dayCumulativeDrivingTime,int minRestingTime,int warningFatigue,long sim)
	{
		if(continueDrivingTime!=0&&dayCumulativeDrivingTime!=0&&minRestingTime!=0&&warningFatigue!=0&&sim!=0)
		{
			LCFatigueDriving.FatigueDriving.Builder builder= LCFatigueDriving.FatigueDriving.newBuilder();
			builder.setContinueDrivingTime(continueDrivingTime);
			builder.setDayCumulativeDrivingTime(dayCumulativeDrivingTime);
			builder.setMinRestingTime(minRestingTime);
			builder.setWarningFatigue(warningFatigue);
			Packet packet = new Packet();
			packet.setUniqueMark(Convert.fillZero(sim, 12));
			packet.setContentForBytes(builder.build().toByteArray());
			packet.setCommand("2258");
			String serialnumber1 = IDFactory.builderID(IDType.SerialNumber).toString();
			packet.setSerialNumber(serialnumber1);
			String serialnumber=Convert.decimalToHexadecimal(Long.parseLong(serialnumber1),4);
			
			String content= "疲劳驾驶报警设置";		
			String logname = "疲劳驾驶报警设置";
			String name="system";
//			int res1 = AddTerminalLog(content, logname, sim,name, "0", Car.getChassisNum(),
//					Car.getCarCph(), team.getTname(),Car.getCarOwner(),mess.getAccountIp(),serialnumber);
//			if (res1>0) {
//				GpsCommandFactory.processor(packet);
//			} else {
//				falselist.add(Car.getCarCph());
//			}
		}
	}
	 
}
