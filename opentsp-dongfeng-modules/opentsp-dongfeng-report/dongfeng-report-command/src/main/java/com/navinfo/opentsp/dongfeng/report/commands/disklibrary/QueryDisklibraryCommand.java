package com.navinfo.opentsp.dongfeng.report.commands.disklibrary;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */
public class QueryDisklibraryCommand extends BaseReportCommand {
    //日期
    private String queryDate;
    //经销商名称
    private String dealerName;
    //经销商代码
    private String dealerCode;
    //标示
    private String invaFlag;

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getInvaFlag() {
        return invaFlag;
    }

    public void setInvaFlag(String invaFlag) {
        this.invaFlag = invaFlag;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryDisklibraryCommand{" +
                "queryDate='" + queryDate + '\'' +
                ", dealerName='" + dealerName + '\'' +
                ", dealerCode='" + dealerCode + '\'' +
                ", invaFlag='" + invaFlag + '\'' +
                '}';
    }
}