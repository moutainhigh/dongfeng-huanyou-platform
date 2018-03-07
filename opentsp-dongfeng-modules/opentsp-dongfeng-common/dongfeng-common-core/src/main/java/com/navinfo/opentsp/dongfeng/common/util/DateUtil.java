package com.navinfo.opentsp.dongfeng.common.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String date_pattern_mm = "yyyy-MM";

	public static final String date_pattern = "yyyy-MM-dd";

	public static final String time_pattern = "yyyy-MM-dd HH:mm:ss";

	public static final String getDate_pattern_dot = "yyyy.MM.dd";

	public static final String time_pattern_dot = "yyyy.MM.dd HH:mm:ss";

	public static final String time_pattern_min = "yyyy.MM.dd HH:mm";

	public static final String date_s_pattern = "yyyyMMdd";

	public static final String date_st_pattern = "yyyy/MM/dd HH:mm:ss";

	public static final String date_st_pattern_yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static final String start = " 00:00:00";

	public static final String end = " 23:59:59";

	public static String formatDate(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat(date_pattern).format(date);
	}

	public static String formatSimDate(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat(date_s_pattern).format(date);
	}

	public static String formatDateDot(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat(getDate_pattern_dot).format(date);
	}

	public static String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String formatTime(Date date) {
		if (date == null)
			return "";
		return getDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static Date parseDate(String datestr) {
		try {
			return new SimpleDateFormat(date_pattern).parse(datestr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static Date parseDate(String datestr, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(datestr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static Date parseDateDot(String datestr) {
		try {
			return new SimpleDateFormat(getDate_pattern_dot).parse(datestr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static Date parseTime(String time) {
		Date result = null;
		if (time == null || "".equals(time)) {
			return null;
		}

		try {
			result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	public static Date parseTimeDot(String time) {
		Date result = null;
		if (time == null || "".equals(time)) {
			return null;
		}

		try {
			result = new SimpleDateFormat(time_pattern_dot).parse(time);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	public static Date add(Date date, int mount, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, mount);
		return cal.getTime();
	}

	/**
	 * date 2 - date1
	 *
	 * @param date1
	 * @param date2
	 * @param type
	 *            {@link Calendar}
	 * @return
	 */
	public static int diff(Date date1, Date date2, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int d1 = cal.get(type);
		cal.setTime(date2);
		return cal.get(type) - d1;
	}

	/**
	 * date 2 - date1
	 *
	 * @param date1-小
	 * @param date2-大
	 * @return
	 */
	public static int diffByDay(String date1, String date2) {
		int type = Calendar.DAY_OF_YEAR;
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDateDot(date1));
		int d1 = cal.get(type);
		int year1 = cal.get(Calendar.YEAR);
		cal.setTime(DateUtil.parseDateDot(date2));
		int year2 = cal.get(Calendar.YEAR);
		if (year1 == year2) {
			return cal.get(type) - d1;
		} else {
			return (year2 - year1) * 365 + cal.get(type) - d1 + 2;
		}
	}

	/**
	 * date自然周中的那一天
	 *
	 * @param date
	 *            时间YYYY.MM.DD
	 * @return
	 */
	public static int dayInWeek(String date) {
		int type = Calendar.DAY_OF_WEEK;
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDateDot(date));
		int d1 = cal.get(type);
		return d1;
	}

	/**
	 * date自然周中的那一天
	 *
	 * @param date
	 * @return
	 */
	public static String dayInWeek(Date date) {
		int type = Calendar.DAY_OF_WEEK;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int d = cal.get(type);
		String dayOfWeek = "";
		switch (d) {
		case 1:
			dayOfWeek = "周日";
			break;
		case 2:
			dayOfWeek = "周一";
			break;
		case 3:
			dayOfWeek = "周二";
			break;
		case 4:
			dayOfWeek = "周三";
			break;
		case 5:
			dayOfWeek = "周四";
			break;
		case 6:
			dayOfWeek = "周五";
			break;
		case 7:
			dayOfWeek = "周六";
			break;
		default:
			dayOfWeek = "";
		}
		return dayOfWeek;
	}

	/**
	 * date自然月中的那一天
	 *
	 * @param date
	 *            时间YYYY.MM.DD
	 * @return
	 */
	public static int dayInMonth(String date) {
		int type = Calendar.DAY_OF_MONTH;
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDateDot(date));
		int d1 = cal.get(type);
		return d1;
	}

	/**
	 * 一个月最大的一天
	 *
	 * @param date
	 *            时间YYYY.MM.DD
	 * @return int 天数
	 */
	public static int maxDayMonth(String date) {
		int type = Calendar.DATE;
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(date.substring(0, 4));
		cal.set(Calendar.YEAR, year);
		int month = Integer.parseInt(date.substring(5, 7).replace("0", "")) - 1;
		cal.set(Calendar.MONTH, month);
		int d1 = cal.getActualMaximum(type);
		return d1;
	}

	/**
	 * 一年中有多少个自然周
	 *
	 * @param date
	 *            时间YYYY.12.31
	 *            <p/>
	 *            处于一年中第几个自然周
	 * @param date
	 *            时间YYYY.MM.DD
	 * @return
	 */
	public static int yearMoreWeek(String date) {
		Date time = DateUtil.addDay(date, 1);
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(time);
		int d1 = cal.get(Calendar.WEEK_OF_YEAR);
		return d1;
	}

	/**
	 * 根据传递过来的日期，转换为对应的 年、所在周的格式
	 *
	 * @param startWeekDay
	 * @return 返回 201601
	 */
	public static int yearofWeekStr(String startWeekDay) {
		Date time = DateUtil.addDay(startWeekDay, 1);
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(time);
		String weekNumber = cal.get(Calendar.WEEK_OF_YEAR) + "";
		String week = startWeekDay.substring(0, 4) + weekNumber;
		if (weekNumber.equals("52") && startWeekDay.substring(5, 7).equals("01")) {
			week = String.valueOf(Integer.parseInt(startWeekDay.substring(0, 4)) - 1) + weekNumber;
		}
		if (weekNumber.length() == 1) {
			week = startWeekDay.substring(0, 4) + "0" + weekNumber;
		}

		return Integer.parseInt(week);
	}

	public static String timeStr(long time) {
		Date date = new Date(time * 1000);
		return getDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String timeStrSSS(long time) {
		Date date = new Date(time);
		return getDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(date);
	}

	public static String time2Str(long time) {
		Date date = new Date(time);
		return getDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String timeStr2(long time) {
		Date date = new Date(time * 1000);
		return getDateFormat(date_pattern).format(date);
	}

	public static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat sim = new SimpleDateFormat(pattern);
		sim.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		// sim.setTimeZone(TimeZone.getTimeZone("Asia/ShangHai"));
		return sim;
	}

	/**
	 * 字符串转LONG
	 *
	 * @param time
	 * @return
	 */
	public static Long strTimeChangeLong(String time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calBegin = new GregorianCalendar();
		try {
			calBegin.setTime(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}

		long beginTime = calBegin.getTimeInMillis();
		return beginTime / 1000;
	}

	public static long strTimeChangeLong2(String time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calBegin = new GregorianCalendar();
		try {
			calBegin.setTime(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}

		long beginTime = calBegin.getTimeInMillis();
		return beginTime / 1000;
	}

	/**
	 * 取得当前日期
	 *
	 * @return Date:当前日期
	 */
	public static Date getNowDate() {
		Date now = null;
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(getDate_pattern_dot);
			now = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return now;
	}

	/**
	 * 取得当前日期
	 *
	 * @return Date:当前日期
	 */
	public static Date getNowDate(String timeType) {
		Date now = null;
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(timeType);
			now = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return now;
	}

	public static Date getDate(Date date, String pattern) {
		Date result = null;
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(pattern);
			result = dateFormat.parse(dateFormat.format(date));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	public static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * long转String
	 *
	 * @param millSec
	 * @return
	 */
	public static Date transferLongToDate(Long millSec) {
		Date date = null;
		SimpleDateFormat dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat(getDate_pattern_dot);
			date = dateFormat.parse(dateFormat.format(new Date(millSec)));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return date;
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static Date addDay(String date, int dayAmount) {
		return addInteger(DateUtil.parseDateDot(date), Calendar.DAY_OF_YEAR, dayAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DAY_OF_YEAR, dayAmount);
	}

	/**
	 * date1 与 date2 中包含独立的天数
	 *
	 * @param date1-小
	 * @param date2-大
	 * @return
	 */
	public static int containAloneDays(String date1, String date2) {
		return diffByDay(date1, date2) + 1;
	}

	/**
	 * date1 与 date2 中包含的独立周数
	 *
	 * @param date1-小
	 * @param date2-大
	 * @return
	 */
	public static int containAloneWeeks(String date1, String date2) {
		if (firstDayOfCurrentWeek(date1).equals(firstDayOfCurrentWeek(date2))) {
			return 1;
		} else {
			DateTime dt1 = DateTime.parse(lastDayOfCurrentWeek(date1), DateTimeFormat.forPattern("yyyy.MM.dd"));
			DateTime dt2 = DateTime.parse(firstDayOfCurrentWeek(date2), DateTimeFormat.forPattern("yyyy.MM.dd"));
			return Weeks.weeksBetween(dt1, dt2).getWeeks() + 2;
		}
	}

	/**
	 * 当前周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static String firstDayOfCurrentWeek(String date) {
		return formatDateDot(addDay(date, 1 - dayInWeek(date)));
	}

	/**
	 * 当前周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String lastDayOfCurrentWeek(String date) {
		return formatDateDot(addDay(date, 7 - dayInWeek(date)));
	}

	/**
	 * 下一周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static String firstDayOfNextWeek(String date) {
		return formatDateDot(addDay(date, 8 - dayInWeek(date)));
	}

	/**
	 * 下一周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String lastDayOfNextWeek(String date) {
		return formatDateDot(addDay(date, 14 - dayInWeek(date)));
	}

	/**
	 * 当前日期到当前周最后日期的日期范围(不完整周,向后)
	 *
	 * @param date
	 * @return
	 */
	public static String currentWeekRangeTail(String date) {
		date = date.substring(0, 10);
		return date + " - " + lastDayOfCurrentWeek(date);
	}

	/**
	 * 当前日期到当前周最后日期的日期范围(不完整周,向前)
	 *
	 * @param date
	 * @return
	 */
	public static String currentWeekRangeHead(String date) {
		date = date.substring(0, 10);
		return firstDayOfCurrentWeek(date) + " - " + date;
	}

	/**
	 * 当前周的日期范围
	 *
	 * @param date
	 * @return
	 */
	public static String currentWeekRange(String date) {
		date = date.substring(0, 10);
		return firstDayOfCurrentWeek(date) + " - " + lastDayOfCurrentWeek(date);
	}

	/**
	 * 下一周的日期范围
	 *
	 * @param date
	 * @return
	 */
	public static String nextWeekRange(String date) {
		date = date.substring(0, 10);
		return firstDayOfNextWeek(date) + " - " + lastDayOfNextWeek(date);
	}

	/**
	 * 增加i周后的日期范围
	 *
	 * @param date
	 * @return
	 */
	public static String addWeekRange(String date, int n) {
		date = date.substring(0, 10);
		return firstDayOfNextNWeek(date, n) + " - " + lastDayOfNextNWeek(date, n);
	}

	/**
	 * 下n周的第一天
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static String firstDayOfNextNWeek(String date, int n) {
		return formatDateDot(addDay(date, 7 * n + 1 - dayInWeek(date)));
	}

	/**
	 * 下n周的最后一天
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static String lastDayOfNextNWeek(String date, int n) {
		return formatDateDot(addDay(date, 7 * (n + 1) - dayInWeek(date)));
	}

	/**
	 * 把week封装成日期范围
	 *
	 * @param date
	 *            格式：201623（2016年第23周）
	 * @return
	 */
	public static String parseToWeekRange(String date) {
		int year = Integer.parseInt(date.substring(0, 4));
		int week = Integer.parseInt(date.substring(4, 6));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		Date weekStart = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		Date weekEnd = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(weekStart) + " - " + sdf.format(weekEnd);
	}

	/**
	 * date1 与 date2 中包含独立的月数
	 *
	 * @param date1-小
	 * @param date2-大
	 * @return
	 */
	public static int containAloneMonths(String date1, String date2) {
		date1 = date1.substring(0, date1.lastIndexOf("."));
		date2 = date2.substring(0, date2.lastIndexOf("."));
		DateTime dt1 = DateTime.parse(date1, DateTimeFormat.forPattern("yyyy.MM"));
		DateTime dt2 = DateTime.parse(date2, DateTimeFormat.forPattern("yyyy.MM"));
		return Months.monthsBetween(dt1, dt2).getMonths() + 1;
	}

	/**
	 * 当前周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static String firstDayOfCurrentMonth(String date) {
		return date.substring(0, 8) + "01";
	}

	/**
	 * 当前周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String lastDayOfCurrentMonth(String date) {
		String day = String.valueOf(maxDayMonth(date));
		if (day.length() == 2) {
			return date.substring(0, 8) + day;
		} else {
			return date.substring(0, 8) + "0" + day;
		}
	}

	/**
	 * 下一周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static String firstDayOfNextMonth(String date) {
		return firstDayOfCurrentMonth(formatDateDot(addMonth(parseDateDot(date), 1)));
	}

	/**
	 * 下一周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static String lastDayOfNextMonth(String date) {
		return lastDayOfCurrentMonth(formatDateDot(addMonth(parseDateDot(date), 1)));
	}

	/**
	 * 下n周的第一天
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static String firstDayOfNextNMonth(String date, int n) {
		return firstDayOfCurrentMonth(formatDateDot(addMonth(parseDateDot(date), n)));
	}

	/**
	 * 下n周的最后一天
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static String lastDayOfNextNMonth(String date, int n) {
		return lastDayOfCurrentMonth(formatDateDot(addMonth(parseDateDot(date), n)));
	}

	/**
	 * 当前日期到当前月最后日期的日期范围(不完整月,向后)
	 *
	 * @param date
	 * @return
	 */
	public static String currentMonthRangeTail(String date) {
		return date + " - " + lastDayOfCurrentMonth(date);
	}

	/**
	 * 当前日期到当前月最后日期的日期范围(不完整月,向前)
	 *
	 * @param date
	 * @return
	 */
	public static String currentMonthRangeHead(String date) {
		return firstDayOfCurrentMonth(date) + " - " + date;
	}

	/**
	 * 当前月范围
	 *
	 * @param date
	 * @return
	 */
	public static String currentMonthRange(String date) {
		date = date.substring(0, 7) + ".01";
		return firstDayOfCurrentMonth(date) + " - " + lastDayOfCurrentMonth(date);
	}

	/**
	 * 增加i月后的日期范围
	 *
	 * @param date
	 * @return
	 */
	public static String addMonthRange(String date, int n) {
		return firstDayOfNextNMonth(date, n) + " - " + lastDayOfNextNMonth(date, n);
	}

	/**
	 * 下个月的第一天
	 *
	 * @param date
	 *            YYYY.MM.DD
	 * @return YYYY.MM.DD
	 */
	public static String firstMonthDay(String date) {
		// 自然月区域
		int inDay = DateUtil.dayInMonth(date);
		String endDay = DateUtil.format(DateUtil.getDate_pattern_dot,
				DateUtil.addDay(date, DateUtil.maxDayMonth(date) - inDay));
		return DateUtil.format(DateUtil.getDate_pattern_dot, DateUtil.addDay(endDay, 1));
	}

	/**
	 * 下周的第一天
	 *
	 * @param date
	 *            YYYY.MM.DD
	 * @return YYYY.MM.DD
	 */
	public static String firstWeekDay(String date) {
		// 自然周区域
		int weekInDay = DateUtil.dayInWeek(date);
		String endDay = DateUtil.format(DateUtil.getDate_pattern_dot, DateUtil.addDay(date, 7 - weekInDay));
		return DateUtil.format(DateUtil.getDate_pattern_dot, DateUtil.addDay(endDay, 1));
	}

	/**
	 * 判断日期字符串是否为日期
	 *
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static boolean isDateCheck(String dateStr, String formatStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return true;
		}
		try {
			String formatedDate = DateUtil.format(formatStr, DateUtil.parseDate(dateStr, formatStr));
			if (formatedDate.equals(dateStr)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 时间段格式设置
	 *
	 * @param time
	 *            秒
	 * @return twentyFourHour 12小时制
	 */
	public static String twentyFourHour(Integer time) {
		String hour = String.valueOf(time / 3600);
		hour = hour.length() == 2 ? hour : "0" + hour;
		String minute = String.valueOf((time % 3600) / 60);
		minute = minute.length() == 2 ? minute : "0" + minute;
		String second = String.valueOf((time % 3600) % 60);
		second = second.length() == 2 ? second : "0" + second;
		String twentyFourHour = hour + ":" + minute + ":" + second;
		return twentyFourHour;
	}

	/**
	 * 时间段格式设置
	 *
	 * @param time
	 *            秒
	 * @return ta 00:00~00:59
	 */
	public static String convStringToHour(Integer time) {
		String ta = "";
		if (time < 10) {
			ta = "0" + String.valueOf(time) + ":00~0" + String.valueOf(time) + ":59";
		} else {
			ta = String.valueOf(time) + ":00~" + String.valueOf(time) + ":59";
		}
		return ta;
	}

	/**
	 * 获取今天第1秒时间戳
	 *
	 * @return 时间戳
	 */
	public static Long firstSecondToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		return today.getTimeInMillis();
	}

	/**
	 * 日期格式转换
	 *
	 * @param datestr
	 *            YYYYmmdd
	 * @param type
	 *            ./-
	 * @return Date
	 */
	public static Date parseDateDot(String datestr, String type) {
		try {
			StringBuffer str = new StringBuffer(datestr);
			str.insert(4, type);
			str.insert(7, type);
			if (type.equals(".")) {
				return new SimpleDateFormat(getDate_pattern_dot).parse(str.toString());
			}
			if (type.equals("-")) {
				return new SimpleDateFormat(date_pattern).parse(str.toString());
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 与当前日期比较
	 *
	 * @param datestr
	 *            比较时间
	 * @return 1：大于当前日期，2：小于当前日期，3：等于当前日期
	 */
	public static Integer diffNowDate(String datestr) {
		try {
			Date dEndDate = new Date();
			if (datestr.indexOf(".") == -1 && datestr.indexOf("-") == -1) {
				StringBuffer str = new StringBuffer(datestr);
				str.insert(4, ".");
				str.insert(7, ".");
				dEndDate = DateUtil.parseDateDot(str.toString());
			} else if (datestr.indexOf(".") != -1) {
				dEndDate = DateUtil.parseDateDot(datestr);
			} else if (datestr.indexOf("-") != -1) {
				dEndDate = DateUtil.parseDate(datestr);
			}

			Date nowDate = DateUtil.getNowDate();
			// 日期大于当前日期
			if (dEndDate.getTime() > nowDate.getTime()) {
				return 1;
			} else if (dEndDate.getTime() < nowDate.getTime()) {
				return 2;
			} else if (dEndDate.getTime() == nowDate.getTime()) {
				return 3;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 与当前日期比较
	 *
	 * @param datestr
	 *            比较时间
	 * @return 1：大于当前日期，2：小于当前日期，3：等于当前日期
	 */
	public static String dateStr(String datestr, String type) {
		StringBuffer str = new StringBuffer(datestr);
		if (datestr.indexOf(".") == -1 && datestr.indexOf("-") == -1) {
			str.insert(4, type);
			str.insert(7, type);
		}
		return str.toString();
	}

	/**
	 * 与当前日期比较
	 *
	 * @param datestr
	 *            比较时间
	 * @return 1：大于当前日期，2：小于当前日期，3：等于当前日期
	 */
	public static Date dateD(String datestr, String type) {
		StringBuffer str = new StringBuffer(datestr);
		Date date = new Date();
		if (datestr.indexOf(".") == -1 && datestr.indexOf("-") == -1) {
			str.insert(4, type);
			str.insert(7, type);
			if (datestr.indexOf(".") != -1) {
				date = DateUtil.parseDateDot(datestr);
			} else if (datestr.indexOf("-") != -1) {
				date = DateUtil.parseDate(datestr);
			}
		}
		return date;
	}

	/**
	 * 判断今天、昨天、前天
	 *
	 * @param date
	 *            格式要求2016-08-08 08:08:08
	 * @return 今天、昨天、前台 否则返回日期
	 */
	public static String parseDateToday(Date date) {
		String ret = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long create = date.getTime();
		Calendar now = Calendar.getInstance();
		long ms = 1000
				* (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));// 毫秒数
		long ms_now = now.getTimeInMillis();
		if (ms_now - create < ms) {
			ret = "今天";
		} else if (ms_now - create < (ms + 24 * 3600 * 1000)) {
			ret = "昨天";
		} else if (ms_now - create < (ms + 24 * 3600 * 1000 * 2)) {
			ret = "前天";
		} else {
			ret = sdf.format(date);
		}
		return ret;

	}

	/**
	 * 判断输入日期是：今天、昨天，若比昨天还早且在本周内，则返回周几，若非本周内，则返回年月日
	 *
	 * @param dateStr
	 *            待判断的日期字符串
	 * @param inputFormat
	 *            带判断日期字符串的格式
	 * @param outPutFormat
	 *            要输出的日期字符串的格式
	 * @param subFormat
	 *            显示为今天、昨天或周几时 带的时分秒格式，若为空，则不带时分秒
	 * @return System.out.println(DateUtil.parseQqDate("2016-10-21 22:10:10",
	 *         "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm" ,"HH:mm"));
	 */
	public static String parseQqDate(String dateStr, String inputFormat, String outPutFormat, String subFormat) {
		String ret = "";
		SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
		Date date = DateUtil.parseDate(dateStr, inputFormat);
		long create = date.getTime();
		Calendar now = Calendar.getInstance();
		long ms = 1000
				* (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));// 毫秒数

		long ms_now = now.getTimeInMillis();

		String subStr = "";
		if (StringUtils.isNotEmpty(subFormat)) {
			subStr = " " + DateUtil.format(subFormat, date);
		}
		if (ms_now - create < ms) {
			ret = "今天" + subStr;
		} else if (ms_now - create < (ms + 24 * 3600 * 1000)) {
			ret = "昨天" + subStr;
		} else {
			if (DateUtil.isThisWeek(date)) {
				ret = DateUtil.dayInWeek(date) + subStr;
			} else {
				sdf = new SimpleDateFormat(outPutFormat);
				ret = sdf.format(date);
			}
		}
		return ret;

	}

	/**
	 * 判断选择的日期是否是本周
	 *
	 * @param date
	 * @return
	 */
	public static boolean isThisWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(date);
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (paramWeek == currentWeek) {
			return true;
		}
		return false;
	}

	/**
	 * 与当前时间相差多少秒
	 *
	 * @param date
	 *            yyyy-mm-dd hh:mm:ss
	 * @return
	 */
	public static Long diffNoeDate(String date) {
		Date a = new Date();
		Date b = parseDate(date, time_pattern);
		Long interval = (a.getTime() - b.getTime()) / 1000;
		return interval;
	}

	/**
	 * 取得当前日期(yyyyMMddHHmmss)
	 *
	 * @return String:当前日期
	 */
	public static String getNowDate_yyyyMMddHHmmss() {
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat(date_st_pattern_yyyyMMddHHmmss);
		String now = dateFormat.format(new Date());
		return now;
	}

	/**
	 * 取得当前日期(yyyyMMddHHmmss)
	 *
	 * @return String:当前日期
	 */
	public static String getNowDate_yyyyMMdd() {
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat(date_s_pattern);
		String now = dateFormat.format(new Date());
		return now;
	}

	/**
	 * 获取给定日期的起始时间
	 *
	 * @param day
	 *            yyyy-MM-dd 格式
	 * @return array[0]beginTime, array[1]endTime
	 */
	public static long[] getDayTime(String day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long[] time = new long[2];
		if (null != day) {// && day.length() == 8
			try {
				time[0] = format.parse(day.trim() + start).getTime() / 1000;
				time[1] = format.parse(day.trim() + end).getTime() / 1000;
				return time;
			} catch (Exception e) {
				logger.error("DateUtil#getDayTime is Error:", e);
				time = new long[2];
			}
		}
		return time;
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
		} catch (Exception e) {
			logger.error("DateUtil#getFirstDayForMonth is Error:", e);
			day_first = 0;
		}
		return day_first;
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
			day_last = (calendar.getTime().getTime() / 1000) + 86399L;
		} catch (Exception e) {
			logger.error("DateUtil#getLastDayForMonth is Error:", e);
			day_last = 0;
		}
		return day_last;
	}

	/**
	 * 时间字符串转换为秒
	 *
	 * @param value
	 * @return
	 */
	public static Long parseTimeStrToSecond(String value) {
		Long date = 0L;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(value).getTime() / 1000;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return date;
	}

	/**
	 * 获取月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long getMinMonthDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(date_pattern);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String time = dateFormat.format(calendar.getTime()) + start;
		return strTimeChangeLong(time);
	}

	/**
	 * 获取月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long getMaxMonthDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(date_pattern);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(date));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String time = dateFormat.format(calendar.getTime()) + end;
		return strTimeChangeLong(time);
	}

	public static long getCurrentSeconds() {
		return new Date().getTime() / 1000;
	}

	public static long getCurrentDaySeconds() {
		DateFormat format = new SimpleDateFormat(date_pattern);
		return getCurrentSecondsByDate(format.format(new Date()));
	}

	public static long getCurrentSecondsByDate(String date) {
		return parseDate(date).getTime() / 1000;
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
		long hour = 0;
		long minute = 0;

		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute >= 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		return (hour + "小时" + minute + "分钟" + second + "秒");
	}

	/**
	 * 计算与系统日期相差多少分钟
	 *
	 * @param startTime
	 * @return
	 */
	public static Long dateDiff(long startTime) {
		long diff = getCurrentMilliSeconds() - startTime;
		return diff / 1000 / 60;// 计算差多少分钟
	}

	/**
	 * 获取系统日期毫秒数
	 * 
	 * @return
	 */
	public static long getCurrentMilliSeconds() {
		return new Date().getTime();
	}

	public static String convert(long time) {
		long day = time / 86400;
		long h = (time - day * 86400) / 3600;
		long m = (time - day * 86400 - h * 3600) / 60;
		return day + "天" + h + "时" + m + "分";
	}

	/**
	 * 获取系统当前时间-Timestamp
	 *
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前时间的小时数-24小时制
	 * @return
	 */
	public static int getCurrentHH() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前年份第一天年月日时分秒
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Long getYearFirst() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(date_pattern);
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		Date currYearFirst = calendar.getTime();
		return strTimeChangeLong(dateFormat.format(currYearFirst) + start);
	}

	/**
	 * 获取当前年份最后一天年月日时分秒
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Long getYearLast() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(date_pattern);
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearFirst = calendar.getTime();
	
		return strTimeChangeLong(dateFormat.format(currYearFirst) + end);
	}
	
}
