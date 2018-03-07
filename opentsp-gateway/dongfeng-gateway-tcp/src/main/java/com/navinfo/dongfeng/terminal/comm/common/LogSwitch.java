package com.navinfo.dongfeng.terminal.comm.common;

public final class LogSwitch {

	public static class ClientModule {
		public static boolean CODEC_UP = true;
		public static boolean CODEC_DOWN = true;
	}

	public static class CachedModule {
	}

	public static class CommandModule {
	}

	public static class ForwardAccessModule {
		public static boolean TERMINAL_CODEC_UP = true;
		public static boolean TERMINAL_CODEC_DOWN = true;
		
		public static boolean ALARM_CODEC_UP = false;
		public static boolean ALARM_CODEC_DOWN = false;
		
	}
	
	public static class LogLevel {
		//业务
		public static boolean INFO_LEVEL = true;
		public static boolean ERROR_LEVEL = false;		
		public static boolean WARN_LEVEL = false;

		//收发数据
		public static boolean CLIENT_ENCODER_INFO_LEVEL=true;
		public static boolean CLIENT_ENCODER_ERROR_LEVEL=true;
		public static boolean CLIENT_DECODER_INFO_LEVEL=true;
		public static boolean CLIENT_DECODER_ERROR_LEVEL=true;

		public static boolean CLOUD_ENCODER_INFO_LEVEL=true;
		public static boolean CLOUD_ENCODER_ERROR_LEVEL=true;
		public static boolean CLOUD_DECODER_INFO_LEVEL=true;
		public static boolean CLOUD_DECODER_ERROR_LEVEL=true;
		
		public static boolean GPS_ENCODER_INFO_LEVEL=true;
		public static boolean GPS_ENCODER_ERROR_LEVEL=true;
		public static boolean GPS_DECODER_INFO_LEVEL=true;
		public static boolean GPS_DECODER_ERROR_LEVEL=true;

		public static boolean MONITOR_ENCODER_INFO_LEVEL=true;
		public static boolean MONITOR_ENCODER_ERROR_LEVEL=true;
		public static boolean MONITOR_DECODER_INFO_LEVEL=true;
		public static boolean MONITOR_DECODER_ERROR_LEVEL=true;
	}
}
