package com.navinfo.opentsp.dongfeng.report.commands.general;

import java.util.List;

/**
 * Created by zhangtiantong on 2018/3/5/005.
 */
public class BadDrivingCountCommand {

    /**
     * 车辆VIN码
     */
    private List<String> vinList;

    /**
     * 查询日期
     */
    private String dateStr;

    /**
     * 是否查询全部(默认不查询全部)
     */
    private Boolean isAll = false;

    public List<String> getVinList() {
        return vinList;
    }

    public void setVinList(List<String> vinList) {
        this.vinList = vinList;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Boolean isAll() {
        return isAll;
    }

    public void setIsAll(Boolean isAll) {
        this.isAll = isAll;
    }

    @Override
    public String toString() {
        return "BadDrivingCountCommand{" +
                "vinList=" + vinList +
                ", dateStr='" + dateStr + '\'' +
                ", isAll=" + isAll +
                '}';
    }
}