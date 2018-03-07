package com.navinfo.opentsp.dongfeng.system.commands.district;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询单个销售区域
 *
 * @wenya
 * @create 2017-04-24 14:10
 **/
public class GetDistrictCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "销售区域ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "销售区域不能为空白", groups = {GroupCommand.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
            return HttpCommandResultWithData.class;
    }
}
