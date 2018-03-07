package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.util.List;

/**
 * Created by zhangtiantong on 2018/3/5/005.
 */
public class BadDrivingResultDto {


    /**
     * 车辆异常驾驶行为分析统计数据
     */
    private List<BadDrivingCountDto> data;

    public List<BadDrivingCountDto> getData() {
        return data;
    }

    public void setData(List<BadDrivingCountDto> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BadDrivingResultDto{" +
                "data=" + data +
                '}';
    }
}