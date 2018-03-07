package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.ParameterType;
import com.navinfo.opentsp.dongfeng.report.dto.market.SyncScanCode;

import java.util.ArrayList;

/**
 * @author wt
 * @version 1.0
 * @date 2017/10/16
 * @modify
 * @copyright
 */
public class SyncScanCodeCommand extends BaseReportCommand
{
    /**
     * chassisNums: [{"chassisNum":"AAAA"},{"chassisNum":"BBBB"},{"chassisNum":"CCCC"}]
     */
    @Converter(target = "scanCodeBean", type = ParameterType.LIST)
    protected String chassisNums;
    protected ArrayList<SyncScanCode> scanCodeBean;

    public String getChassisNums() {
        return chassisNums;
    }

    public void setChassisNums(String chassisNums) {
        this.chassisNums = chassisNums;
    }

    public ArrayList<SyncScanCode> getScanCodeBean() {
        return scanCodeBean;
    }

    public void setScanCodeBean(ArrayList<SyncScanCode> scanCodeBean) {
        this.scanCodeBean = scanCodeBean;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "SyncScanCodeCommand{" +
                "chassisNums='" + chassisNums + '\'' +
                ", scanCodeBean=" + scanCodeBean +
                '}';
    }
}
