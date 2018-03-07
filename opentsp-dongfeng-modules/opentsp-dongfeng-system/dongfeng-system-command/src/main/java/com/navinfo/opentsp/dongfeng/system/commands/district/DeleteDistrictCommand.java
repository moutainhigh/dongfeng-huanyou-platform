package com.navinfo.opentsp.dongfeng.system.commands.district;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 销售区域删除Command
 *
 * @author Sunyu
 */
public class DeleteDistrictCommand extends BaseCommand<CommonResult> {

    @NotNull(message = "账号ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号ID不能为空白", groups = {GroupCommand.class})
    private String tid;
    /**
     * 分组名称
     */
    private String tName;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

    @Override
    public String toString() {
        return "deleteDistrictCommand{" +
                "tid='" + tid + '\'' +
                '}';
    }
}