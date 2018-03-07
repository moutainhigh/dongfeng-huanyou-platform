package com.navinfo.opentsp.dongfeng.report.commands.location;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
public class QueryLastLocationCommand extends BaseReportCommand {

    /**
     * vin
     */
    private String vin;

    /**
     * 经销商
     */
    private String dealer;

    /**
     * 末次位置开始时间
     */
    private String lastLocationStartDate;

    /**
     * 末次位置结束时间
     */
    private String lastLocationEndDate;

    /**
     * 出库开始时间
     */
    private String beginOutDate;

    /**
     * 出库结束时间
     */
    private String endOutDate;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getLastLocationStartDate() {
        return lastLocationStartDate;
    }

    public void setLastLocationStartDate(String lastLocationStartDate) {
        this.lastLocationStartDate = lastLocationStartDate;
    }

    public String getLastLocationEndDate() {
        return lastLocationEndDate;
    }

    public void setLastLocationEndDate(String lastLocationEndDate) {
        this.lastLocationEndDate = lastLocationEndDate;
    }

    public String getBeginOutDate() {
        return beginOutDate;
    }

    public void setBeginOutDate(String beginOutDate) {
        this.beginOutDate = beginOutDate;
    }

    public String getEndOutDate() {
        return endOutDate;
    }

    public void setEndOutDate(String endOutDate) {
        this.endOutDate = endOutDate;
    }

    @Override
    public String toString() {
        return "QueryLastLocationCommand{" +
                "vin='" + vin + '\'' +
                ", dealer='" + dealer + '\'' +
                ", lastLocationStartDate='" + lastLocationStartDate + '\'' +
                ", lastLocationEndDate='" + lastLocationEndDate + '\'' +
                ", beginOutDate='" + beginOutDate + '\'' +
                ", endOutDate='" + endOutDate + '\'' +
                '}';
    }
}
