package com.navinfo.opentsp.dongfeng.report.entity.market;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
@Entity
@Table(name = "product_check_result_record")
public class VehicleAuditEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false, name = "id", columnDefinition="bigint")
    private BigInteger id;

    @Column(nullable = true, name = "status")
    private Integer status;

    @Column(nullable = true, name = "classis_number")
    private String chassisNum;

    @Column(nullable = true, name = "trader")
    private String dealer;

    @Column(nullable = true, name = "proposer")
    private String operator;

    @Column(nullable = true, name = "current_pro_code")
    private String currentProcode;

    @Column(nullable = true, name = "next_pro_code")
    private String nextProcode;

    @Column(nullable = true, name = "reason")
    private String reason;

    @Column(nullable = true, name = "apply_reason")
    private String applyReason;

    @Column(nullable = true, name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(nullable = true, name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

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

    public String getCurrentProcode() {
        return currentProcode;
    }

    public void setCurrentProcode(String currentProcode) {
        this.currentProcode = currentProcode;
    }

    public String getNextProcode() {
        return nextProcode;
    }

    public void setNextProcode(String nextProcode) {
        this.nextProcode = nextProcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
