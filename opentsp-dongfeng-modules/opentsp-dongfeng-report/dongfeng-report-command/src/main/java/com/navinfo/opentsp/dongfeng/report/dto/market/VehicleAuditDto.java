package com.navinfo.opentsp.dongfeng.report.dto.market;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
public class VehicleAuditDto {
    private String id;

    private String operation;

    private String status;

    private String chassisNum;

    private String dealer;

    private String operator;

    private String operaDate;

    private String applyReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(String operaDate) {
        this.operaDate = operaDate;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }
}
