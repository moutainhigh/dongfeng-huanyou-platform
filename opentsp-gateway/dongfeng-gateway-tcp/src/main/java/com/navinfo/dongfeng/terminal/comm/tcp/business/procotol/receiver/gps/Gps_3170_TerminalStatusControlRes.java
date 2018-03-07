package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;


import com.lc.core.protocol.terminal.monitor.LCTerminalStatusControlRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.kafka.KafkaMessageChannel;
import com.navinfo.dongfeng.terminal.comm.kafka.command.KafkaCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/******
 * 外设控制应答
 * @author Administrator
 *
 */
@Component
public class Gps_3170_TerminalStatusControlRes extends Command {
	@Autowired
	private KafkaMessageChannel kafkaMessageChannel;

	@Value("${opentsp.kafka.producer.qq.poscan.topic}")
	private String qqTopic;

	@Override
	public Object processor(Packet packet) {
//		try {
//			// 流水号-链路缓存转化处理
//			packet=SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
//
//			IoSession  c= SerialNumberCache.instance().getSerialNumberSessionCache(Convert.decimalToHexadecimal(Integer.parseInt(packet.getAnswerSerialNumber()), 4)+"_"+packet.getSession().getId());
//			if(c==null){
//				return null;
//			}
//			packet.setMsgType("06");
//			   if(packet.getParameter("result")!=null){
////	            	HandleLocalCar(packet);
//			   }
//		} catch (Exception e) {
//			log.error("外设控制应答异常", e);
//		}
//		return null;
//	}
		try
		{
			// 流水号-链路缓存转化处理
//			packet= SerialNumberExchange.serialNumberChange(packet);//20160112,caozhen,序列号转换
			Long auswerSeriaNumber = Long.valueOf(Convert.decimalToHexadecimal(Long.parseLong(packet.getAnswerSerialNumber()), 4));
			KafkaCommand kafkaCommand = new KafkaCommand();
			kafkaCommand.setMessage(packet.getContentForBytes());
			kafkaCommand.setCommandId(packet.getCommand());
			kafkaCommand.setTopic(qqTopic);
			kafkaCommand.setKey(packet.getUniqueMark().substring(1));
			kafkaMessageChannel.send(kafkaCommand);
			log.info("kafka send to rp success !{}", packet.toString());


			LCTerminalStatusControlRes.TerminalStatusControlRes res = LCTerminalStatusControlRes.TerminalStatusControlRes.parseFrom(packet.getContentForBytes());
			String serialNumber = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
			log.warn("3170:"+serialNumber+","+res.getResult().getNumber());
		}
		catch (Exception e)
		{
			log.error("推送云平台下发的位置信息异常", e);
		}
		return null;
	}
}
