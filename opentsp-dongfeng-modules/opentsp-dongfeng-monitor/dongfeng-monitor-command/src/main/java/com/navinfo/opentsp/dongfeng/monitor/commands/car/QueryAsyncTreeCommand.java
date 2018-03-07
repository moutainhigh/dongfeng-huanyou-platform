package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
public class QueryAsyncTreeCommand extends BaseCommand<HttpCommandResultWithData> {

    //组id
    private String id;
    //是否选中
    private Boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryAsyncTreeCommand{}";
    }
}
