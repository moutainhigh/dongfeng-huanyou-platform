package com.navinfo.opentsp.dongfeng.system.commands.basedata;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 省市编码列表
 *
 * @wenya
 * @create 2017-04-26 15:26
 **/
public class GovDataCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "省市编码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "省市编码不能为空白", groups = {GroupCommand.class})
    private String parentCode;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
