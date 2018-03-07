package com.navinfo.dongfeng.terminal.comm.tcp.business.codec;

import com.navinfo.dongfeng.terminal.comm.common.LCConstant;
import com.navinfo.dongfeng.terminal.comm.common.LogSwitch;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.PacketProcessing;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.ArraysUtils;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CloudServerDecoder implements ProtocolDecoder {

	private static Logger logger = LoggerFactory
			.getLogger(CloudServerDecoder.class);
	static byte SIGN_7E = Convert.str2Bcd("7E")[0];
	static byte SIGN_7C = Convert.str2Bcd("7C")[0];
	static byte SIGN_7D = Convert.str2Bcd("7D")[0];
	static byte SIGN_7B = Convert.str2Bcd("7B")[0];
	static byte SIGN_7A = Convert.str2Bcd("7A")[0];
	private static Map<Long, byte[]> sliceCache = new ConcurrentHashMap<Long, byte[]>();

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
		try {
			if (LogSwitch.LogLevel.GPS_DECODER_INFO_LEVEL&& !in.getHexDump().contains("7E 01 12 03") && !in.getHexDump().contains("7E 01 12 01")
					&& !in.getHexDump().contains("7E 01 12 00") && !in.getHexDump().contains("7E 02 30 02") 
					 && !in.getHexDump().contains("7E 01 11 00") && in.getHexDump().startsWith("7E ")){
				logger.info("收到CLOUD数据：" + in.getHexDump());
//				System.out.println("收到CLOUD数据：" + in.getHexDump());
			}
			if(in.getHexDump().contains("7E 01 12 01")){
				logger.info("GPS收到CLOUD数据：" + in.getHexDump());
			}
			String currentBuffer = in.getHexDump().replaceAll(" ", "");
			in.flip();
			byte[] currentBytes = Convert.hexStringToBytes(currentBuffer);
			byte[] lastBytes = sliceCache.get(session.getId());
			sliceCache.put(session.getId(), new byte[] {});
			byte[] bytes = ArraysUtils.arraycopy(lastBytes, currentBytes);

			List<byte[]> list = PacketProcessing.subpackage(bytes, SIGN_7E,
					SIGN_7B, 0);
			for (byte[] bs : list) {
				if (bs != null) {
					if (bs[0] == SIGN_7E && bs[bs.length - 1] == SIGN_7B) {
						build(bs, session, out);
					} else if (bs[0] == SIGN_7E) {
						sliceCache.put(session.getId(), bs);
					}
				}
			}
		} catch (Exception e) {
			if(!e.toString().contains("java.util.concurrent.RejectedExecutionException")){
				logger.error("异常", e);
			}
		}

	}

	/*********************
	 * 
	 * @param packet
	 * @param session
	 * @param out
	 */
	private void build(byte[] packet, IoSession session,
			ProtocolDecoderOutput out) {

		// 去包头包尾
		byte[] tempBytes = new byte[packet.length - 2];
		System.arraycopy(packet, 1, tempBytes, 0, tempBytes.length);
		// 转义还原
		byte[] bytes = PacketProcessing.unEscape(tempBytes,
				LCConstant.toEscapeByte, LCConstant.escapeByte);
		// 取出源数据检验码
		int checkCode = bytes[bytes.length - 1];
		// 计算检验码
		int tempCode = PacketProcessing
				.checkPackage(bytes, 0, bytes.length - 2);
		if (checkCode != tempCode) {
			logger.error("Xor error , result[" + tempCode + "],source["
					+ checkCode + "].source data :"
					+ Convert.bytesToHexString(packet));
			return;
		}

		String cmdId = Convert.bytesToHexString(ArraysUtils.subarrays(bytes, 1,
				2));
		int cmdProperty = Convert.byte2Int(ArraysUtils.subarrays(bytes, 3, 2),
				2);
		String uniqueMark = Convert.bytesToHexString(ArraysUtils.subarrays(
				bytes, 5, 6));
		String serialNumber = Convert.bytesToHexString(ArraysUtils.subarrays(
				bytes, 11, 2));

		Packet outpacket = new Packet();
		outpacket.setCommand(cmdId);
		outpacket.setSerialNumber(serialNumber);
		// outpacket.setProtocol(String.valueOf(msgType));
		// outpacket.setOriginalPacket(packet);
		outpacket.setUniqueMark(uniqueMark);
		outpacket.setFrom(Long.parseLong(uniqueMark));
		// 如果指令为节点汇报,添加SessionID到Packet包,提供给Session与节点编号绑定所用
		// if(cmdId == AllCommands.NodeCluster.ReportServerIdentify_VALUE){
		// outpacket.addParameter("sessionId", session.getId());
		// }
		// 判断是否分包
		if ((cmdProperty & 8192) > 0) {
			int total = Convert
					.byte2Int(ArraysUtils.subarrays(bytes, 13, 2), 2);// 消息包封装项,包总数
			int serial = Convert.byte2Int(ArraysUtils.subarrays(bytes, 15, 2),
					2);
			;// 消息包封装项,包序号
			int blockId = Convert.byte2Int(ArraysUtils.subarrays(bytes, 17, 2),
					2);// 消息包封装项,包块ID
			byte[] content = ArraysUtils
					.subarrays(bytes, 19, bytes.length - 20);
			outpacket.setPacketTotal(total);
			outpacket.setPacketSerial(serial);
			outpacket.setBlockId(blockId);
			outpacket.setContentForBytes(content);
			boolean isComplete = PacketProcessing.mergeBlock(outpacket);
			if (isComplete) {
				outpacket = PacketProcessing.getCompletePacket(PacketProcessing
						.getCacheBlockId(uniqueMark, blockId));
				if(session.getRemoteAddress()!= null)
					outpacket.setFromIp(session.getRemoteAddress().toString().split(":")[0].substring(1));
					outpacket.setSession(session);
				out.write(outpacket);
			}
		} else {
			byte[] content = ArraysUtils
					.subarrays(bytes, 13, bytes.length - 14);
			
			outpacket.setContentForBytes(content);
			if(session.getRemoteAddress()!= null)
				outpacket.setFromIp(session.getRemoteAddress().toString().split(":")[0].substring(1));
				outpacket.setSession(session);
			out.write(outpacket);
		}

	}

	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void removePacketCache(long sessionId) {
		sliceCache.remove(sessionId);
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
