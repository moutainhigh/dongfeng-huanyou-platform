package com.navinfo.opentsp.dongfeng.system.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-09-07
 * @modify
 * @copyright Navi Tsp
 */
public class SynDealerToSignCommand extends BaseCommand<CommonResult> {

    /**
     * 经销商ID
     */
    @NotNull(message = "经销商ID 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "经销商ID 不能为空", groups = {GroupCommand.class})
    private String tid;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

}
