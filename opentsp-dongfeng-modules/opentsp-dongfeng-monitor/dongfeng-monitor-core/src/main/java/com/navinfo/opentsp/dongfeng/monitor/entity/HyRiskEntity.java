package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_risk")
public class HyRiskEntity {
    private BigInteger id;
    private BigInteger regionId;
    private BigInteger vehicleId;
    private Integer riskType;
    private Integer riskValue;
    private Timestamp operateTime;
    private String operateUser;
    private String riskCommon;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, columnDefinition = "bigint")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "REGION_ID", nullable = false, columnDefinition = "bigint")
    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "VEHICLE_ID", nullable = false, columnDefinition = "bigint")
    public BigInteger getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(BigInteger vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Basic
    @Column(name = "RISK_TYPE", nullable = false)
    public Integer getRiskType() {
        return riskType;
    }

    public void setRiskType(Integer riskType) {
        this.riskType = riskType;
    }

    @Basic
    @Column(name = "RISK_VALUE", nullable = false)
    public Integer getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(Integer riskValue) {
        this.riskValue = riskValue;
    }

    @Basic
    @Column(name = "OPERATE_TIME", nullable = false)
    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Basic
    @Column(name = "OPERATE_USER", nullable = false)
    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    @Basic
    @Column(name = "RISK_COMMON")
    public String getRiskCommon() {
        return riskCommon;
    }

    public void setRiskCommon(String desc) {
        this.riskCommon = desc;
    }
}
