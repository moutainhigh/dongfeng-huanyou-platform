package com.navinfo.opentsp.dongfeng.common.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间工具
 * Created by houym on 16-11-8.
 */
public class TimeUtil {


    /**
     * 默认时间格式
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("yyyy-MM");

    /**
     * 取得当前系统时间（默认时间格式）
     *
     * @return 当前系统时间
     */
    public static String getTime() {
        return new DateTime().toString(DEFAULT_PATTERN);
    }


    /**
     * 取当前时间格式化成特定字符串
     *
     * @param pattern 格式字符串
     * @return 当前系统时间
     */
    public static String getTime(String pattern) {
        return new DateTime().toString(pattern);
    }

    /**
     * 格式化时间字符串
     *
     * @param time        待格式化字符串
     * @param fromPattern 原时间格式
     * @param toPattern   转化后时间格式
     * @return 转换后的字符串
     */
    public static String getTime(String time, String fromPattern, String toPattern) {
        return DateTime.parse(time, DateTimeFormat.forPattern(fromPattern)).toString(toPattern);
    }

    /**
     * 获得当前日期的0点时间戳
     *
     * @return 当前日期的0点时间戳
     */
    public static long getDayBegainMillis() {
        return new DateTime().dayOfWeek().roundFloorCopy().getMillis();
    }

    /**
     * 取得当前系统时间
     *
     * @return 当前系统时间
     */
    public static Date getCurrentTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 判断是否为同一天
     *
     * @param d1 时间1
     * @param d2 时间2
     * @return true：时间1和时间2为同一天
     * false：时间1和时间2不是同一天
     */
    public static boolean isSameDay(long d1, long d2) {
        long oneDayMillis = 24 * 60 * 60 * 1000;
        long bufTime = d2 - d1;
        return (0 <= bufTime && bufTime < oneDayMillis);
    }


    public static void main(String[] args) {

        String yyyyMMdd = getTime("2016-02-26", "yyyy-MM-dd", "yyyyMMdd");
        System.out.println(yyyyMMdd);

        DateTime now = new DateTime(); // 2016-02-26T16:51:28.749+08:00

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTimeFormatter monthFormatter = DateTimeFormat.forPattern("yyyy-MM");
        String s = now.toString("yyyy-MM-dd");
        String d = now.toString("yyyy-MM");
        String month = StringUtils.substringBeforeLast("2016-02-26", "-");
        DateTime dataMonthCache = DateTime.parse(month, TimeUtil.MONTH_FORMATTER);
        String monthString = new DateTime(1493264934000l).toString("yyyy-MM");
        DateTime monthCurrentPoint = DateTime.parse(monthString);
        //判断是否跨月
        if (monthCurrentPoint.compareTo(dataMonthCache) == 1) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }

        DateTime dateTime = now.minusDays(1);

        DateTime parse = DateTime.parse(s);
        DateTime aaaa = DateTime.parse(d).minusMonths(1);
        System.out.println(parse.compareTo(dateTime));

        DateTime.parse(d);
    }
}
