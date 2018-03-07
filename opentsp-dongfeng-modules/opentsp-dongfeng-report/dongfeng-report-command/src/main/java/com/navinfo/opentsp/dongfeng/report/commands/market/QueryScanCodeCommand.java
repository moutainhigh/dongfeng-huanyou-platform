package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Vehicle Scan Code Command
 * @author wt
 * @date 2017-03-24
 * @modify
 * @version 1.0
 */
public class QueryScanCodeCommand extends BaseReportCommand {
    private String operaDate;
    private String chassisNum;
    private String scanOpera;
    private String dealer;

    public String getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(String operaDate) {
        this.operaDate = operaDate;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getScanOpera() {
        return scanOpera;
    }

    public void setScanOpera(String scanOpera) {
        this.scanOpera = scanOpera;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "ScanCodeCommand{" +
                "operaDate='" + operaDate + '\'' +
                ", chassisNum='" + chassisNum + '\'' +
                ", scanOpera='" + scanOpera + '\'' +
                ", dealer='" + dealer + '\'' +
                '}';
    }
}
