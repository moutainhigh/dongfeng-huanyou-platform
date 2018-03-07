package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 车辆选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryCarsCommand  extends BaseCommand<HttpCommandResultWithData> {
    //底盘号
    private String chassisNum;
    //搜素内容
    private String searchText;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return  HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryCarsCommand{}";
    }
}
