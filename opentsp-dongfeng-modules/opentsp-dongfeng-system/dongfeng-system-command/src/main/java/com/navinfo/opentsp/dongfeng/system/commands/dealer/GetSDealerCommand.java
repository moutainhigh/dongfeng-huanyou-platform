package com.navinfo.opentsp.dongfeng.system.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */
public class GetSDealerCommand extends BaseCommand<HttpCommandResultWithData> {

    /**
     * 经销商ID
     */
    @NotNull(message = "tid 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "tid 不能为空", groups = {GroupCommand.class})
    private String tid;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}