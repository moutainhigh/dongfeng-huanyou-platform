package com.navinfo.dongfeng.terminal.comm.common;

import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import org.apache.mina.core.session.IoSession;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Packet implements Serializable {

	private static final long serialVersionUID = -8765500906240643869L;
	private Object from;
	private Object to;
	private String msgType; // 消息类型
	private String command;//消息ID
	private String nodeMsg ;//消息体属性
	private String uniqueMark;//唯一标识 SIM/CODE
	private String serialNumber = "0000";//消息流水号
	private String content;//消息体
	private byte[] contentForBytes = null;
	private String answerSerialNumber = "0000";
	private String fromIp;
	private int fromModule;//数据来自哪个模块
	private long createTime = System.currentTimeMillis()/1000;
	private IoSession session = null;
	private Map<String, String> parameters = null;
	private Map<String, Object> object = null;
	private byte[] msgForBytes;//原数据bytes[]
	private int packetTotal = 0;// 包序号
	private int packetSerial = 0;// 总包数
	private int blockId = 0;//分包块ID
	/**
	 * 无参构造
	 */
	public Packet() {
		parameters = new ConcurrentHashMap<String, String>();
		object = new ConcurrentHashMap<String, Object>();
	}
	
	/**
	 * <code>isGenerateId</code>是否需要自动生成流水号
	 * 
	 * @param isGenerateId
	 *            {@link Boolean}
	 */
	public Packet(boolean isGenerateId) {
		parameters = new ConcurrentHashMap<String, String>();
		object = new ConcurrentHashMap<String, Object>();
		if (isGenerateId) {
			serialNumber = String.valueOf(IDFactory
					.builderID(IDType.SerialNumber));
		}
	}

	public byte[] getContentForBytes() {
		return contentForBytes;
	}

	public void setContentForBytes(byte[] contentForBytes) {
		this.contentForBytes = contentForBytes;
	}

	public String getUniqueMark() {
		return uniqueMark;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getFromModule() {
		return fromModule;
	}

	public void setFromModule(int fromModule) {
		this.fromModule = fromModule;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setUniqueMark(String uniqueMark) {
		this.uniqueMark = uniqueMark;
	}

	public String getAnswerSerialNumber() {
		return answerSerialNumber;
	}

	public void setAnswerSerialNumber(String answerSerialNumber) {
		this.answerSerialNumber = answerSerialNumber;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getFrom() {
		return from;
	}

	public void setFrom(Object from) {
		this.from = from;
	}

	public Object getTo() {
		return to;
	}

	public void setTo(Object to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}

	public String getParameter(String key) {
		return this.parameters.get(key);
	}

	public void addObject(String key, Object array) {
		this.object.put(key, array);
	}

	public Object getObject(String key) {
		return this.object.get(key);
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public String getNodeMsg() {
		return nodeMsg;
	}

	public void setNodeMsg(String nodeMsg) {
		this.nodeMsg = nodeMsg;
	}

	public byte[] getMsgForBytes() {
		return msgForBytes;
	}

	public void setMsgForBytes(byte[] msgForBytes) {
		this.msgForBytes = msgForBytes;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public int getPacketTotal() {
		return packetTotal;
	}

	public void setPacketTotal(int packetTotal) {
		this.packetTotal = packetTotal;
	}

	public int getPacketSerial() {
		return packetSerial;
	}

	public void setPacketSerial(int packetSerial) {
		this.packetSerial = packetSerial;
	}


}