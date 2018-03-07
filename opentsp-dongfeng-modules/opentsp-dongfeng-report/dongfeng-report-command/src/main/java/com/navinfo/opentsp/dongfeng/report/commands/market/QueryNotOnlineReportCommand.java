package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Created by xltianc.zh on 2017/3/28.
 */
@SuppressWarnings("rawtypes")
public class QueryNotOnlineReportCommand extends BaseReportCommand {

    private String chassis_no;
    private String storage_location;
    private String flag;

    public void setChassis_no(String chassis_no) {
        this.chassis_no = chassis_no;
    }

    public void setStorage_location(String storage_location) {
        this.storage_location = storage_location;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getChassis_no() {
        return chassis_no;
    }

    public String getStorage_location() {
        return storage_location;
    }

    public String getFlag() {
        return flag;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryWsxReportCommond{" +
                "chassis_no='" + chassis_no + '\'' +
                ", storage_location='" + storage_location + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }

}
