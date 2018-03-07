package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.constant.LocationUtilConstants;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 位置相关工具类
 *
 * Created by zhangyu on 2017/4/1.
 */
public class LocationUtil {

    private final static double DEFAULT_GIS_UNIT = 0.000001;
    /**
     * 经纬度间的距离 返回m
     *
     * @param fromLat
     * @param fromLng
     * @param toLat
     * @param toLng
     * @return
     */
    public static double getDistance(double fromLat, double fromLng, double toLat, double toLng) {
        double radLat1 = rad(fromLat);
        double radLat2 = rad(toLat);
        double a = radLat1 - radLat2;
        double b = rad(fromLng) - rad(toLng);
        double s =
                2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                        * Math.pow(Math.sin(b / 2), 2)));
        s = s * LocationUtilConstants.EARTH_RADIUS;
        return s * 1000;
    }

    /**
     * 经纬度换算
     *
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double cvtCoordinate(final int value) {
        return NumberUtils.toDouble(String.format("%.6f", value * DEFAULT_GIS_UNIT));
    }

}
