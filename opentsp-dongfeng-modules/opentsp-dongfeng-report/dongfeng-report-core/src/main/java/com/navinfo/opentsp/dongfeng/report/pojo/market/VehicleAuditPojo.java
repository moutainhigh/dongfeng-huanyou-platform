package com.navinfo.opentsp.dongfeng.report.pojo.market;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
public class VehicleAuditPojo {

    private BigInteger id;

    private Integer status;

    private String chassisNum;

    private String dealer;

    private String operator;

    private Date operaDate;

    private String applyReason;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(Date operaDate) {
        this.operaDate = operaDate;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }
}
