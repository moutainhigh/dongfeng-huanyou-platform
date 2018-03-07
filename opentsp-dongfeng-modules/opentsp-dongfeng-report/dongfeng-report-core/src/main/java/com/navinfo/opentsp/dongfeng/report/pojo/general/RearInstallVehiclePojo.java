package com.navinfo.opentsp.dongfeng.report.pojo.general;

import java.math.BigInteger;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public class RearInstallVehiclePojo {
    private String date;
    private BigInteger count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RearInstallVehiclePojo{" +
                "date='" + date + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
