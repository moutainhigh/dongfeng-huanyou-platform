package com.navinfo.dongfeng.terminal.comm.tcp.business.codec;

import com.navinfo.dongfeng.terminal.comm.common.LCConstant;
import com.navinfo.dongfeng.terminal.comm.common.LogSwitch;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.PacketProcessing;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.ArraysUtils;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * encoder IO发送类
 * @author baitao
 *
 */
public class CloudServerEncoder implements ProtocolEncoder {
	private static Logger logger = LoggerFactory
			.getLogger(CloudServerEncoder.class);
	public static final int PACKET_MAX_LENGTH = 512;
	public static final byte[] escapeByte = new byte[] { 0x7E, 0x7D, 0x7B, 0x7A };
	public static final byte[][] toEscapeByte = new byte[][] { { 0x7D, 0x02 },
			{ 0x7D, 0x01 }, { 0x7A, 0x02 }, { 0x7A, 0x01 } };
	static byte SIGN_7E = Convert.str2Bcd("7E")[0];
	static byte SIGN_7B = Convert.str2Bcd("7B")[0];

	
	public void encode(IoSession iosession, Object message,
			ProtocolEncoderOutput out) throws Exception {
		Packet packet = (Packet) message;
		try {
			//消息类型
			String msgType = packet.getMsgType();
			byte[] contentString = null;
			//消息体
			if(packet.getContentForBytes() != null){
			 contentString = packet.getContentForBytes();
			}else{
				contentString = Convert.hexStringToBytes(packet.getContent());
			}
			// 消息ID
			String command = packet.getCommand();
			//消息体属性
			//唯一标识
			String uniqueMark = packet.getUniqueMark();
			if(uniqueMark == null || "".equals(uniqueMark)){
				uniqueMark = "000000000000";
			}
			//消息流水号
			String serialNumber = packet.getSerialNumber();
			if(contentString != null){
				if(contentString.length > PACKET_MAX_LENGTH){
					List<byte[]> list = PacketProcessing.dataSegmentation(contentString,PACKET_MAX_LENGTH);
					int packetCount = list.size();
					byte[] pkCount = Convert.longTobytes(packetCount, 2);
					byte[] blockId = Convert.intTobytes(PacketProcessing.getBlockId(), 2);
					for(int i = 0 ; i < packetCount ; i ++){
						byte[] pkNumber = Convert.longTobytes(i+1, 2);
						
						byte[] pkNode = ArraysUtils.arraycopy(pkCount, pkNumber);
						pkNode = ArraysUtils.arraycopy(pkNode, blockId);
						
						byte[] pkProperty = Convert.longTobytes(list.get(i).length + 8192, 2);//8192字节位表示当前数据分包
						
						this.write(Convert.hexStringToBytes(msgType)  , Convert.hexStringToBytes(command), pkProperty, Convert.hexStringToBytes(uniqueMark), Convert
								.hexStringToBytes(serialNumber), pkNode, list.get(i), out,packet);
					}
				}else{
					byte[] pkProperty = Convert.longTobytes(contentString.length , 2);
					this.write(Convert.hexStringToBytes(msgType)  , Convert.hexStringToBytes(command), pkProperty, Convert.hexStringToBytes(uniqueMark), Convert
							.hexStringToBytes(serialNumber), null, contentString,out,packet);
				}
			}
		} catch (Exception e) {
			logger.error("发送数据异常.", e);
		}
	}

	private void write(byte[] msgType, byte[] msgId,
			byte[] pkProperty, byte[] uniqueMark,
			byte[] serialNumber, byte[] pkNode, byte[] contentString,
			ProtocolEncoderOutput out, Packet packet) {
		try {
			//首标识位=1		消息类型=1 	消息头 ={消息ID=2		消息体属性=2		唯一标识=6		消息流水号=2		消息封包项=4}		消息体=N		校验码=1		尾标识位=1
			int contentLength = contentString != null ? contentString.length : 0;
			int pkLength = pkNode == null ? contentLength +  14 : contentLength + 20;
			byte[] data = new byte[pkLength];
			ArraysUtils.arrayappend(data, 0, msgType);//消息类型
			ArraysUtils.arrayappend(data, 1, msgId);//指令号
			ArraysUtils.arrayappend(data, 3, pkProperty);//消息体属性
			ArraysUtils.arrayappend(data, 5, uniqueMark);//唯一标识
			ArraysUtils.arrayappend(data, 11, serialNumber);//流水号
			if (pkNode != null) {// 消息封装项
				ArraysUtils.arrayappend(data, 13, pkNode);
				ArraysUtils.arrayappend(data, 19, contentString);
				ArraysUtils.arrayappend(data, data.length - 1 ,
						new byte[] { PacketProcessing.checkPackage(data, 0, data.length - 1) });
			}else{
				ArraysUtils.arrayappend(data, 13, contentString);
				ArraysUtils.arrayappend(data, data.length - 1 , 
						new byte[] { PacketProcessing.checkPackage(data, 0, data.length - 1) });
			}
			
			byte[] bytes = PacketProcessing.escape(data, LCConstant.escapeByte, LCConstant.toEscapeByte);
			IoBuffer buffer = IoBuffer.allocate(bytes.length+2);
			buffer.put(0, LCConstant.pkBegin);
			int index = 1;
			for (byte b : bytes) {
				buffer.put(index , b);
				index++;
			}
			buffer.put(bytes.length+1, LCConstant.pkEnd);
//		log.info("[SubCenter MM > Business]: "+buffer.getHexDump());
			out.write(buffer);
			if(LogSwitch.LogLevel.CLOUD_ENCODER_INFO_LEVEL&& !buffer.getHexDump().contains("7E 01 02 03") && !buffer.getHexDump().contains("7E 01 02 00") && !buffer.getHexDump().contains("7E 01 02 01")&& !buffer.getHexDump().contains("7E 01 10 99")){
			logger.info("向位置云发送数据：" + packet.getFromIp() + ":" + buffer.getHexDump());
//			System.out.println("向位置云发送数据：" + packet.getFromIp() + ":" + buffer.getHexDump());
			}
			if(buffer.getHexDump().contains("7E 01 01 03")){
				logger.info("向位置云发送数据：" + packet.getFromIp() + ":" + buffer.getHexDump());
//				System.out.println("向位置云发送数据：" + packet.getFromIp() + ":" + buffer.getHexDump());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常", e);
		}
		
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}
}
