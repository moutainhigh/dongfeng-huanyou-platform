package com.navinfo.opentsp.dongfeng.report.dto.general;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public class QueryRearInstallVehicleDto {
    private String date;
    private String count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "QueryRearInstallVehicleDto{" +
                "date='" + date + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
