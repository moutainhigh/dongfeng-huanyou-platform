package com.navinfo.dongfeng.terminal.comm.common;

/**
 * 位置云平台常量
 * 
 * @author lgw
 * 
 */
public final class LCConstant {
	public final static class ServerStatusCode{
		/***/
		public final static int create = 0x01;
		/***/
		public final static int open = 0x01;
		/***/
		public final static int close = 0x01;
		/***/
		public final static int success = 0x01;
		/***/
		public final static int failure = 0x01;
		/***/
		public static final int ready = 1;
		/***/
		public static final int work = 2;
		
	}
	/** 消息类型 */
	public class LCMessageType {
		/** 平台交互 */
		public final static int PLATFORM = 0x01;
		/** 终端交互 */
		public final static int TERMINAL = 0x02;
		/** 原始数据 */
		public final static int ORIGINAL = 0x03;
		/** 统计数据 */
		public final static int STATISTICS = 0x04;
	}
	/**
	 * 流量方向
	 * @author lgw
	 *
	 */
	public static enum FlowDirection{
		Up,Down
	}
	public static enum MasterSlave {
		Slave, Master
	}
	public static final int PACKET_MAX_LENGTH = 512;//Integer.MAX_VALUE;
	public static byte pkBegin = 0x7E;
	public static byte pkEnd = 0x7B;
	public static final byte[] escapeByte = new byte[] { 0x7E, 0x7D, 0x7B, 0x7A };
	public static final byte[][] toEscapeByte = new byte[][] { { 0x7D, 0x02 },
			{ 0x7D, 0x01 }, { 0x7A, 0x02 }, { 0x7A, 0x01 } };
	
	public static final int TERMINAL_PACKET_MAX_LENGTH_2011 = 1023;
	public static byte TERMINAL_PK_BEGIN_2011 = 0x7E;
	public static byte TERMINAL_PK_END_2011 = 0x7E;
	
	public static byte[] TERMINAL_ESCAPE_2011 =new byte[] { 0x7e, 0x7d};
	public static byte[][] TERMINAL_TO_ESCAPE_2011 = new byte[][] { { 0x7D, 0x02 },
		{ 0x7D, 0x01 }};
	
}
