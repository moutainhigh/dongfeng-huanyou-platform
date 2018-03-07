package com.navinfo.opentsp.dongfeng.system.commands.business;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class QueryBusinessesCommand extends BaseReportCommand {

    /**
     * 所属客户名称
     */
    private String businessName;
    /**
     * 省市编码
     */
    private String addressCode;
    /**
     * 证件号码
     */
    private String businessCode;
    /**
     * 客户类型 1-企业 2-个人
     */
    private String ctype;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryBusinessesCommand{" +
                "businessName='" + businessName + '\'' +
                ", addressCode='" + addressCode + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", ctype='" + ctype + '\'' +
                '}';
    }
}