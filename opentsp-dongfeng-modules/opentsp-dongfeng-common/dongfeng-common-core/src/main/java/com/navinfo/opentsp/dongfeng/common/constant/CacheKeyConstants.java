package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 缓存常量类
 * <p>
 * Created by zhangyu on 2017/3/13.
 */
public class CacheKeyConstants {
    public final static String PrefixGps = "NI_";

    // 末次位置缓存KEY
    public final static String GPS_KEY = PrefixGps + "GPS";

    // 末次有效位置缓存KEY
    public final static String GPS_VALID_KEY = PrefixGps + "GPS_VALID";

    // 车辆报警提醒
    public final static String ALARM_KEY = PrefixGps + "ALARM";

    // 总里程，剩余油量,在线状态
    public final static String GPS_INFO_KEY = PrefixGps + "GPS_INFO";

    //webserviceURL缓存KEY---1200
    public final static String WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY = "WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY";
    //webserviceURL缓存KEY---0101
    public final static String WEB_SERVICE_URL_SYNCHRONIZEADDRESS_KEY = "WEB_SERVICE_URL_SYNCHRONIZEADDRESS_KEY";
    //终端增量订阅前缀
    public final static String ADD_SUBSCRIBE_TERMINAL_PREFIX = "TERMINAL_SUBSCRIBE_";
    //车辆进出区域数据缓存
    public final static String AREA_ENTER_KEY = "AREA_ENTER";


}
