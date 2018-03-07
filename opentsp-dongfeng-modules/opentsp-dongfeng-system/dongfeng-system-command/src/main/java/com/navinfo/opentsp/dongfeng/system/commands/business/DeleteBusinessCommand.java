package com.navinfo.opentsp.dongfeng.system.commands.business;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class DeleteBusinessCommand extends BaseCommand<CommonResult> {

    /**
     * 客户ID
     */
    private String cid;
    private String businessCode;
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}