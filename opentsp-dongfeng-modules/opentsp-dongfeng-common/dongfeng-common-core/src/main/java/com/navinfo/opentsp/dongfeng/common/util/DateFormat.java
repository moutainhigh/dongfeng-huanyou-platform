package com.navinfo.opentsp.dongfeng.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期格式化工具类
 * 
 * @author Administrator
 * @project LjDevelopTool-20111103
 * @date 2011-11-3
 * 
 */
public class DateFormat {
	private static SimpleDateFormat sdf_1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf_7 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf_3 = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat sdf_4 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf_5 = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat sdf_6 = new SimpleDateFormat(
			"yyyy-MM-dd HH时");
	private static SimpleDateFormat sdf_8 = new SimpleDateFormat(
			"yyyyMMddHH");

	/**
	 * 获取<code>{@link SimpleDateFormat}</code>实例
	 * 
	 * @param format
	 * @return
	 */
	public static SimpleDateFormat format(Format format) {
		switch (format.ordinal()) {
		case 0:
			return sdf_1;
		case 1:
			return sdf_2;
		case 2:
			return sdf_3;
		case 3:
			return sdf_4;
		case 4:
			return sdf_5;
		case 5:
			return sdf_6;
		case 6:
			return sdf_7;
		case 7:
			return sdf_8;

		default:
			return sdf_1;
		}
	}
	
	/**
	 * 将秒转换为日期字符串格式
	 * 
	 * @param time
	 * @param format
	 * @see com.aero.develop.calendar.DateFormat.Format
	 * @return
	 */
	public static String timeToString(Object time, Format format) {
		if (time == null) {
			return "";
		}
		long longTime = Long.parseLong(String.valueOf(time));
		Date date = new Date(longTime * 1000);
		return DateFormat.format(format).format(date);
	}

	/**
	 * 将秒转换为时分秒格式
	 * 
	 * @param time
	 * @return
	 */
	public static String formatLongToTimeString(Object time) {
		if (time == null) {
			return "";
		}
		long second = Long.parseLong(String.valueOf(time));
		if (second <= 0) {
			return "";
		}
		long hour = 0;
		long minute = 0;

		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		return (hour + "小时" + minute + "分钟" + second + "秒");
	}


	/**
	 * 格式
	 * 
	 * @author Administrator
	 * @project LjDevelopTool-20111103
	 * @date 2011-11-3
	 * 
	 */
	public enum Format {
		/**
		 * Example 2011-11-03 18:00:00
		 */
		YY_YY_MM_DD_HH_MM_SS,
		/**
		 * Example 2011-11-03
		 */
		YYYY_MM_DD,
		/**
		 * Example 18:00:00
		 */
		HH_MM_SS,
		/**
		 * Example 20111103
		 */
		YYYYMMMDD,
		/**
		 * Example 201111
		 */
		YYYYMM,
		/**
		 * Example 2011-11-03 18时
		 */
		YYYYMMDD_HH,
		/**
		 * Example 2011-11-03 18:00
		 */		
		YYYY_MM_DD_HH_MM,
		YYYYMMDDHH;
	}
}
