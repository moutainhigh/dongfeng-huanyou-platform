package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

/**
 * 热力图网格数据
 *
 * @wenya
 * @create 2017-04-13 15:25
 **/
public class BoundingBoxPojo {
    private double north;//lat
    private double south;//lat
    private double east;//lon
    private double west;//lon
    private int count;//车次

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }
}
