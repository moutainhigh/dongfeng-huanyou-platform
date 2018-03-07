package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.util.Arrays;

/**
 * Created by ZHANGTIANTONG on 2018/3/6/006.
 */
public class BadDrivingChartDto {

    /**
     * 车辆VIN
     */
    private String vin;

    /**
     * 异常驾驶行为
     */
    private String[] name;

    /**
     * 异常行为驾驶分析计数
     */
    private Integer[] count;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public Integer[] getCount() {
        return count;
    }

    public void setCount(Integer[] count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BadDrivingChartDto{" +
                "vin='" + vin + '\'' +
                ", name=" + Arrays.toString(name) +
                ", count=" + Arrays.toString(count) +
                '}';
    }
}