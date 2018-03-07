package com.navinfo.opentsp.dongfeng.report.commands.product;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 生产线漏扫报表
 *
 * @wenya
 * @create 2017-03-27 17:47
 **/
public class QueryLSCommand extends BaseReportCommand {
    private String chassisNum;//底盘号
    private String storageLoc;//库位
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getStorageLoc() {
        return storageLoc;
    }

    public void setStorageLoc(String storageLoc) {
        this.storageLoc = storageLoc;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryLSCommand{" +
                "chassisNum='" + chassisNum + '\'' +
                ", storageLoc='" + storageLoc + '\'' +
                '}';
    }
}
