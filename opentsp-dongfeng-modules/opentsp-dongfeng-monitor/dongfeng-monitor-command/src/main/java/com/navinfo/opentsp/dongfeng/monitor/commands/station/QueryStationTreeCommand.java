package com.navinfo.opentsp.dongfeng.monitor.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 获取服务站搜索树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
public class QueryStationTreeCommand extends BaseCommand<HttpCommandResultWithData> {
    //搜索类型
    private Integer searchType;
    //搜索内容
    private String searchText;

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationTreeCommand{}";
    }
}
