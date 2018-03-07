package com.navinfo.dongfeng.terminal.comm.common.util.date;

import com.navinfo.dongfeng.terminal.comm.common.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils
{
    public static String start = " 00:00:00";
    
    public static String end = " 23:59:59";
    
    /**
     * @param day yyyy-MM-dd 格式
     * @return array[0]beginTime,array[1]endTime
     */
    public static long[] getDayTime(String day)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != day)
        {// && day.length() == 8
            try
            {
                long[] time = new long[2];
                time[0] = format.parse(day.trim() + start).getTime() / 1000;
                time[1] = format.parse(day.trim() + end).getTime() / 1000;
                return time;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 时长 :秒时长转换为 天时分
     *
     * @param time
     * @return 时,分,秒
     */
    public static String convert(int time)
    {
        int day = time / 86400;
        int h = (time - day * 86400) / 3600;
        int m = (time - day * 86400 - h * 3600) / 60;
        return day + "," + h + "," + m;
    }

    // public static void main(String[] args) {
    // long a = 2684485634l;
    // a = (long)(a*0.05*3600l);
    // System.out.println(a);
    // long b = 483207414120l;
    // System.out.println(b/86400);
    // System.out.println(convert(a));
    //
    // long start = 1457020800l;
    // long end = 1457052882l;
    // String s = formatDate(start*1000l);
    // String e = formatDate(end*1000l);
    // System.out.println("s="+s+",e="+e);
    // }

    public static String convert(long time)
    {
        long day = time / 86400;
        long h = (time - day * 86400) / 3600;
        long m = (time - day * 86400 - h * 3600) / 60;
        return day + "," + h + "," + m;
    }

    // yyyyMMdd 转换为毫秒
    public static Long formatDate(String day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        try
        {
            date = formatter.parse(day);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if (date == null)
        {
            return 0L;
        }
        else
        {
            long currentTime = date.getTime(); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * day:yyyy-mm-dd
     *
     * @param day
     * @return
     */
    // yyyy-MM-dd 转换为毫秒
    public static String formatDate_yyyyMMdd_start(String day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = formatter.parse(day);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (date == null)
        {
            return "";
        }
        else
        {
            long currentTime = (date.getTime()) / 1000; // date类型转成long类型
            return String.valueOf(currentTime);
        }
    }

    /**
     * day:yyyy-mm-dd
     *
     * @param day
     * @return
     */
    // yyyy-MM-dd 转换为毫秒
    public static String formatDate_yyyyMMdd_end(String day)
    {
        SimpleDateFormat formater_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try
        {
            date = formater_mm.parse(day + " 23:59:59");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (date == null)
        {
            return "";
        }
        else
        {
            long currentTime = (date.getTime()) / 1000; // date类型转成long类型
            return String.valueOf(currentTime);
        }
    }

    /**
     * 时间转成秒【驾驶员行为习惯分析使用】
     *
     * @param day(yyyyMMdd)
     * @return
     * @author Chenfangbo
     */
    public static long formatDateToSecond(String day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try
        {
            date = formatter.parse(day);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (date == null)
        {
            return 0;
        }
        else
        {
            long currentTime = (date.getTime()) / 1000; // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * 时间转成完整时分秒【驾驶员行为习惯分析使用】
     *
     * @param day yyyyMMdd 格式
     * @return array[0]beginTime,array[1]endTime
     * @author Chenfangbo
     */
    public static long[] getDayBeginAndEndTimeToSecond(String day)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        if (null != day)
        {// && day.length() == 8
            try
            {
                long[] time = new long[2];
                time[0] = format.parse(day.trim() + start).getTime() / 1000;
                time[1] = format.parse(day.trim() + end).getTime() / 1000;
                return time;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 毫秒转换为yyyyMMdd HH:mm:ss
    public static String formatDate(long millisecond)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(millisecond));
    }

    // 毫秒转换为yyyyMMdd HH:mm:ss
    public static String formatDateOnly(long millisecond)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(millisecond));
    }

    /** 获取yyyy-MM-dd 格式日期 */
    public static String getCurrentDay()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public static String getCurrentDay(String formatStr)
    {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date());
    }

    public static String formatDate(long millisecond, String formatStr)
    {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(millisecond));
    }

    public static String formatDate(String millisecond, String formatStr)
    {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(Long.valueOf(millisecond) * 1000));
    }

    /**
     * @param day
     * @return array[0]一天开始时间，array[1]一天结束时间
     */
    public static long[] getDayTime2(String day)
    {
        if (null != day && day.length() == 8)
        {
            long[] time = new long[2];
            Date date = parse(day, "YYYYMMDD");
            Calendar calendar = calendar(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            long start = calendar.getTimeInMillis();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            long end = calendar.getTimeInMillis();
            time[0] = start / 1000;
            time[1] = end / 1000;
            return time;
        }
        return null;
    }

    public static Date parse(String dateText, String dateFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        // SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        if (sdf != null)
            try
            {
                return sdf.parse(dateText);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        return null;
    }

    public static Calendar calendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // 获取头上上传的当前时间

    private String getUploadCurrentTime()
    {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

    }

    public static String getNowTime()
        throws ParseException
    {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater_mm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date start = formater_mm.parse(formater.format(new Date()) + " 23:59:59");
        ;// 取时间

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(start);
        calendar.add(calendar.DATE, 0);
        start = calendar.getTime();

        return new SimpleDateFormat("yyyyMMddHHmmss").format(start);
    }

    public static String getYesterday()
        throws ParseException
    {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater_mm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date start = formater_mm.parse(formater.format(new Date()) + " 00:00:00");
        ;// 取时间

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(start);
        calendar.add(calendar.DATE, -1);
        start = calendar.getTime();

        return new SimpleDateFormat("yyyyMMddHHmmss").format(start);
    }

    /**
     * 两个时间相小时
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public static float getDayDifference(long starttime, long endtime)
    {
        if ((endtime - starttime) < 0)
            return 0f;
        Long ss = (endtime - starttime);
        float day = (Float.valueOf(ss + "")) / 1000 / 60 / 60;
        return day;
    }

    public static Date getStartDate(Date date, String type)
    {
        if (date == null || StringUtils.isEmpty(type))
        {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(type);
        String temp = format.format(date);
        StringBuilder result = new StringBuilder(temp.split(" ")[0]);
        Date startDate = null;
        try
        {
            startDate = format.parse(result.append(" " + start).toString());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
        return startDate;
    }

    public static Date getStartDate(Long date, String type)
    {
        if (date == null || StringUtils.isEmpty(type))
        {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(type);
        String temp = format.format(date);
        StringBuilder result = new StringBuilder(temp.split(" ")[0]);
        Date startDate = null;
        try
        {
            startDate = format.parse(result.append(" " + start).toString());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
        return startDate;
    }

    public static Date getEndDate(Date date, String type)
    {
        if (date == null || StringUtils.isEmpty(type))
        {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(type);
        String temp = format.format(date);
        StringBuilder result = new StringBuilder(temp.split(" ")[0]);
        Date endDate = null;
        try
        {
            endDate = format.parse(result.append(" " + end).toString());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
        return endDate;
    }

    /**
     * 获取每天开始日期
     *
     * @return
     */
    public static String getStartDate()
        throws ParseException
    {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater_mm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date start = formater_mm.parse(formater.format(new Date()) + " 00:00:00");
        return String.valueOf(start.getTime() / 1000);
    }

    /**
     * 获取每天结束日期
     *
     * @return
     */
    public static String getEndDate()
        throws ParseException
    {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater_mm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date end = formater_mm.parse(formater.format(new Date()) + " 23:59:59");
        return String.valueOf(end.getTime() / 1000);
    }

    public static Date getEndDate(Long date, String type)
    {
        if (date == null || StringUtils.isEmpty(type))
        {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(type);
        String temp = format.format(date);
        StringBuilder result = new StringBuilder(temp.split(" ")[0]);
        Date endDate = null;
        try
        {
            endDate = format.parse(result.append(" " + end).toString());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
        return endDate;
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static String getMonthFirstDay()
        throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formater_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar)Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");

        return String.valueOf((formater_mm.parse(str.toString())).getTime() / 1000);
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static String getCurrYearFirst()
    {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return String.valueOf(getYearFirst(currentYear).getTime() / 1000);
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static String getCurrYearLast()
    {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return String.valueOf(getYearLast(currentYear).getTime() / 1000);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取当前日期的前N日的日期【驾驶员行为分析使用】
     *
     * @param num (前？天)
     * @return String (yyyyMMdd格式)
     * @author Chenfangbo
     * @date 20160825
     * */
    public static String getBeforDay(int num)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long nowDaySecond = new Date().getTime();
        long beforDaySecond = (nowDaySecond - 1000 * 60 * 60 * 24 * num);
        String beforDay = sdf.format(new Date(beforDaySecond));
        return beforDay;
    }

    /**
     * 获取当前周第?天【驾驶员行为分析使用】
     *
     * @param num=1(本周第1天)、num=7(本周最后一天)
     * @author Chenfangbo
     * @return
     */
    public static String getFirstOrLastDayOfWeek(int num)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_WEEK, num);
        long weekone = cale.getTime().getTime() + 1000 * 60 * 60 * 24;
        String beforDay = sdf.format(new Date(weekone));
        return beforDay;
    }

    /**
     * 获取当前月第一天【驾驶员行为分析使用】
     *
     * @author Chenfangbo
     * @return
     */
    public static String getFirstDayOfMonth()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        long weekone = cale.getTime().getTime();
        String beforDay = sdf.format(new Date(weekone));
        return beforDay;
    }

    /**
     * 获取当前月最后一天【驾驶员行为分析使用】
     *
     * @author Chenfangbo
     * @return
     */
    public static String getLastDayOfMonth()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sdf.format(ca.getTime());
        System.out.println(last);
        return last;
    }
    /*******/
    /**
     * 获取上周第1天【驾驶员行为分析使用】
     * @author Chenfangbo
     * @return
     */
    public static String getFirstDayOfWeek(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_WEEK, 1);
    	calendar.add(Calendar.DATE, -6);
        long weekone = calendar.getTime().getTime();
        String beforDay = sdf.format(new Date(weekone));
        return beforDay;
    }
    /**
     * 获取上周第7天【驾驶员行为分析使用】
     * @author Chenfangbo
     * @return
     */
    public static String getLastDayOfWeek(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_WEEK, 1);
    	calendar.add(Calendar.DATE, 0);
        long weekone = calendar.getTime().getTime();
        String beforDay = sdf.format(new Date(weekone));
        return beforDay;
    }
    /**
     * 获取上月第一天【驾驶员行为分析使用】
     * @author Chenfangbo
     * @return
     */
    public static String getFirstDayOfLastMonth(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH, -1);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
        long weekone = calendar.getTime().getTime();
        String beforDay = sdf.format(new Date(weekone));
        return beforDay;
    }
    /**
     * 获取上月最后一天【驾驶员行为分析使用】
     * @author Chenfangbo
     * @return
     */
    public static String getLastDayOfLastMonth(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.add(Calendar.DATE, -1);
    	long monthone = calendar.getTime().getTime();
    	String last = sdf.format(new Date(monthone));
    	return last;
    }

    /**
	 * 秒数转换成小时
	 * @Method: convertToHour
	 * @Description: 秒数转换成小时
	 * @param seconds
	 * @return Integer
	 * @throws
	 */
	public static Integer secondsToHour(int seconds) {
		BigDecimal result = new BigDecimal(seconds);
		result = result.divide(new BigDecimal(3600), BigDecimal.ROUND_HALF_UP);
		return result.intValue();
	}
	
    public static void main(String[] args)
    {
        System.out.println(getCurrYearFirst());
        System.out.println(getCurrYearLast());
    }
    
}
