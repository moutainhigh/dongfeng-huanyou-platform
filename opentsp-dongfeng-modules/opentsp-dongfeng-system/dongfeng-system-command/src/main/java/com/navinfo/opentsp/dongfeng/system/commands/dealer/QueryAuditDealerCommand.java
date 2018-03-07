package com.navinfo.opentsp.dongfeng.system.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
public class QueryAuditDealerCommand extends BaseCommand<HttpCommandResultWithData> {

    /**
     * 经销商ID
     */
//    @NotNull(message = "tid 不能为空", groups = {GroupCommand.class})
//    @NotBlank(message = "tid 不能为空", groups = {GroupCommand.class})
    private String tid;
    /**
     * 经销商名称
     */
    private String dealerName;
    /**
     * 经销商编码
     */
    private String code;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}