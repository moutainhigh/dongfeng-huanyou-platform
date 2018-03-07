package com.navinfo.opentsp.dongfeng.common.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

	/**
	 * Number of seconds in a standard minute.
	 */
	public static final long SECOND_PER_MINUTE = 60;
	/**
	 * Number of seconds in a standard hour.
	 */
	public static final long SECOND_PER_HOUR = 60 * SECOND_PER_MINUTE;
	/**
	 * Number of seconds in a standard day.
	 */
	public static final long SECOND_PER_DAY = 24 * SECOND_PER_HOUR;


	public static final String ISO_DATE_FORMAT_DAY = "yyyy-MM-dd";

	public static final String ISO_DATE_FORMAT_MONTH = "yyyy-MM";

	public static final String ISO_DATE_FORMAT_DAY_ZH = "yyyy年MM月dd天";

	public static final String ISO_DATE_FORMAT_MONTH_ZH = "yyyy年MM月";

	static String start = " 00:00:00";
	static String end = " 23:59:59";

	/**
	 * @param day
	 *            yyyy-MM-dd 格式
	 * @return array[0]beginTime,array[1]endTime
	 */
	public static long[] getDayTime(String day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != day) {// && day.length() == 8
			try {
				long[] time = new long[2];
				time[0] = format.parse(day.trim() + start).getTime() / 1000;
				time[1] = format.parse(day.trim() + end).getTime() / 1000;
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static long[] getDayBeginAndEndTime(String datadate, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		long[] time = new long[2];
		if (!StringUtils.isEmpty(datadate)) {
			try {
				time[0] = format.parse(datadate.trim()).getTime() / 1000;
				time[1] = (format.parse(datadate.trim()).getTime() / 1000) + 86400;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time;
	}

	/**
	 * 时长 :秒时长转换为 天时分
	 * 
	 * @param time
	 * @return 时,分,秒
	 */
	public static String convert(int time) {
		int day = time / 86400;
		int h = (time - day * 86400) / 3600;
		int m = (time - day * 86400 - h * 3600) / 60;
		return day + "," + h + "," + m;
	}

	public static String convert(long time) {
		long day = time / 86400;
		long h = (time - day * 86400) / 3600;
		long m = (time - day * 86400 - h * 3600) / 60;
		return day + "," + h + "," + m;
	}

	public static String convert1(long time) {
		long day = time / 86400;
		long h = (time - day * 86400) / 3600;
		long m = (time - day * 86400 - h * 3600) / 60;
		return day + "天" + h + "时" + m + "分";
	}

	// 毫秒转换为yyyyMMdd HH:mm:ss
	public static String formatDate(long millisecond) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(millisecond));
	}

	public static String formatToDate(long millisecond) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(millisecond));
	}

	/** 获取yyyy-MM-dd 格式日期 */
	public static String getCurrentDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	public static String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	public static String getCurrentDay(String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(new Date());
	}

	public static String formatDate(long millisecond, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(new Date());
	}

	public static long getCurrentDayTimes(String dateFormat) {
		long times = 0;
		try {
			String date = getCurrentDay();
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			times = format.parse(date).getTime() / 1000L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}

	public static long getLastDayForMonth(String datadate, String dateFormat) {
		long day_last = 0;
		try {
			Date date = null;
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			date = format.parse(datadate);
			// 创建日历
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1); // 加一个月
			calendar.set(Calendar.DATE, 1); // 设置为该月第一天
			calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
			day_last = calendar.getTime().getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day_last;
	}

	public static long getFirstDayForMonth(String datadate, String dateFormat) {
		long day_first = 0;
		try {
			Date date = null;
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			date = format.parse(datadate);
			// 创建日历
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			day_first = calendar.getTime().getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day_first;
	}

	public static String getMonthAndDay(String datadate, String dateFormat) {
		SimpleDateFormat sdfMonth = new SimpleDateFormat("M");
		SimpleDateFormat sdfDay = new SimpleDateFormat("d");

		Date date = new Date();
		try {
			date = new SimpleDateFormat(dateFormat).parse(datadate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdfMonth.format(date) + "月" + sdfDay.format(date);
	}
}
