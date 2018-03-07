package com.navinfo.opentsp.dongfeng.monitor.dto.car;

import com.navinfo.opentsp.dongfeng.monitor.dto.risk.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 区域属性
 *
 * @wenya
 * @create 2017-04-17 16:27
 **/
public class CarCountPoint {
    private String radius;//半径(圆形风控区域半径，其他为空)
    private String locPoints;//区域坐标点点坐标
    private List<Point> pointBeans = new ArrayList<Point>();//区域坐标点点坐标
    private int type;//图形类型（3：圆；1：矩形）

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLocPoints() {
        return locPoints;
    }

    public void setLocPoints(String locPoints) {
        this.locPoints = locPoints;
        List<String> arrays = Arrays.asList(this.locPoints.split(";"));
        for (String key : arrays) {
            String[] pointArray = key.split(",");
            Point point = new Point();
            final Double lat =  Double.parseDouble(pointArray[0]) * 1000000;
            point.setLat(lat);
            final Double lng =  Double.parseDouble(pointArray[1]) * 1000000;
            point.setLng(lng);
            pointBeans.add(point);
        }
    }

    public List<Point> getPointBeans() {
        return pointBeans;
    }

    public void setPointBeans(List<Point> pointBeans) {
        this.pointBeans = pointBeans;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CarCountPoint{" +
                "radius='" + radius + '\'' +
                ", locPoints='" + locPoints + '\'' +
                ", pointBeans=" + pointBeans +
                ", type=" + type +
                '}';
    }
}
