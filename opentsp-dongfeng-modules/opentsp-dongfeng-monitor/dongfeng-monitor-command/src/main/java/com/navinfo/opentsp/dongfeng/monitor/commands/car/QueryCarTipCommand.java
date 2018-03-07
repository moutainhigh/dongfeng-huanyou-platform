package com.navinfo.opentsp.dongfeng.monitor.commands.car;
import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by wenya on 2017/3/8.
 * 查询车辆tip信息
 */
public class QueryCarTipCommand extends BaseCommand<HttpCommandResultWithData>{
    @NotNull(message = "车辆id不能为空", groups = {GroupCommand.class})
    private Long id;//车辆id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return  HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "GetCarTip{" +
                "id=" + id +
                '}';
    }
}
