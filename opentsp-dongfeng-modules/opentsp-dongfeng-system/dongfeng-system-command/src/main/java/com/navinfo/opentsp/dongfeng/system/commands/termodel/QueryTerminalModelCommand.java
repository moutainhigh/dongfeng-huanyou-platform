package com.navinfo.opentsp.dongfeng.system.commands.termodel;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询单个终端型号
 *
 * @author: zhangyue
 * @version: 1.0
 * @since: 2017-03-09
 **/
public class QueryTerminalModelCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "终端dataTypee 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "终端dataType 不能为空", groups = {GroupCommand.class})
    private String dataType;

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
