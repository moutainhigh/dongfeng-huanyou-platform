package com.navinfo.dongfeng.terminal.comm.common.constant;

/**
 * 定义缓存key
 * 
 * Created by zhangyu on 2017/3/11.
 */
public class CacheKeyConstants
{
    //指令消息缓存key前缀
    public static final String MSG_KEY_PREFIX = "MSGPUSH_";
    // 车辆瞬时数据缓存key
 //   public final static String LAST_GPS_INFO_KEY = "LAST_GPS_INFO_";
   //前缀以区别
    public final static String PrefixGps ="NI";
    //webserviceURL缓存KEY---1200
    public final static String WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY ="WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY";
    //webserviceURL缓存KEY---0101
    public final static String WEB_SERVICE_URL_SYNCHRONIZEADDRESS_KEY ="WEB_SERVICE_URL_SYNCHRONIZEADDRESS_KEY";

    //gpsserviceURL缓存KEY
    public final static String GPS_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY ="GPS_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY";


    //webserviceURL缓存KEY
    public final static String WEB_SERVICE_URL_KEY ="WEB_SERVICE_URL_";
    //RPURL缓存KEY
    public final static String RP_URL_KEY ="RP_URL_";
    // 末次位置缓存KEY
    public final static String GPS_KEY = PrefixGps + "GPS_";
    //末次有效位置缓存KEY
    public final static String GPS_VALID_KEY = PrefixGps + "GPS_VALID_";
    //车辆报警提醒
    public final static String ALARM_KEY = PrefixGps + "ALARM_";
    //总里程，剩余油量
    public final static String GPS_INFO_KEY = PrefixGps + "GPS_INFO_";
    //终端增量订阅前缀
    public final static String ADD_SUBSCRIBE_TERMINAL_PREFIX = "TERMINAL_SUBSCRIBE_";

}
