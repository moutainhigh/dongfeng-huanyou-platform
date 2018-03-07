package com.navinfo.opentsp.dongfeng.system.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 经销商删除command
 * @Author zhaoming@mapbar.com
 * @Date 2017/3/10 10:28
 **/
public class DeleteDealerCommand extends BaseCommand<HttpCommandResultWithData>{
    @NotNull(message = "经销商id 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "经销商id 不能为空", groups = {GroupCommand.class})
    private String tid;

    /**
     * 经销商编码
     */
    private String dealerCode;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
