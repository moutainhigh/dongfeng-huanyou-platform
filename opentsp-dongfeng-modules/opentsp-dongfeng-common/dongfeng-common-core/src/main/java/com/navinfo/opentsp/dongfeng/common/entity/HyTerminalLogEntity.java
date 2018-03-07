package com.navinfo.opentsp.dongfeng.common.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-01
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_terminal_log")
public class HyTerminalLogEntity {
    private BigInteger logId;
    private String logName;
    private Integer logValue;
    private Integer logType;
    private BigInteger logSim;
    private String logContent;
    private Long logDate;
    private String logAccountIp;
    private String logAccountName;
    private Integer logCommon;
    private Integer moduleType;
    private String carCph;
    private String companyName;
    private String carOwner;
    private String chassisNum;
    private Integer logState;
    private String logLockCommon;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOG_ID", nullable = false, columnDefinition = "bigint")
    public BigInteger getLogId() {
        return logId;
    }

    public void setLogId(BigInteger logId) {
        this.logId = logId;
    }

    @Basic
    @Column(name = "LOG_NAME", nullable = true, length = 50)
    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    @Basic
    @Column(name = "LOG_VALUE", nullable = true)
    public Integer getLogValue() {
        if(logValue == null){
            return 0;
        }
        return logValue;
    }

    public void setLogValue(Integer logValue) {
        this.logValue = logValue;
    }

    @Basic
    @Column(name = "LOG_TYPE", nullable = true)
    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    @Basic
    @Column(name = "LOG_SIM", nullable = false,columnDefinition = "bigint")
    public BigInteger getLogSim() {
        return logSim;
    }

    public void setLogSim(BigInteger logSim) {
        this.logSim = logSim;
    }

    @Basic
    @Column(name = "LOG_CONTENT", nullable = true, length = 2000)
    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    @Basic
    @Column(name = "LOG_DATE", nullable = true)
    public Long getLogDate() {
        return logDate;
    }

    public void setLogDate(Long logDate) {
        this.logDate = logDate;
    }

    @Basic
    @Column(name = "LOG_ACCOUNT_IP", nullable = true, length = 50)
    public String getLogAccountIp() {
        return logAccountIp;
    }

    public void setLogAccountIp(String logAccountIp) {
        this.logAccountIp = logAccountIp;
    }

    @Basic
    @Column(name = "LOG_ACCOUNT_NAME", nullable = true, length = 50)
    public String getLogAccountName() {
        return logAccountName;
    }

    public void setLogAccountName(String logAccountName) {
        this.logAccountName = logAccountName;
    }

    @Basic
    @Column(name = "LOG_COMMON", nullable = true)
    public Integer getLogCommon() {
        return logCommon;
    }

    public void setLogCommon(Integer logCommon) {
        this.logCommon = logCommon;
    }

    @Basic
    @Column(name = "module_type", nullable = true)
    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    @Basic
    @Column(name = "CAR_CPH", nullable = true, length = 50)
    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    @Basic
    @Column(name = "COMPANY_NAME", nullable = true, length = 50)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "CAR_OWNER", nullable = true, length = 50)
    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    @Basic
    @Column(name = "CHASSIS_NUM", nullable = true, length = 50)
    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    @Basic
    @Column(name = "LOG_STATE", nullable = false)
    public Integer getLogState() {
        return logState;
    }

    public void setLogState(Integer logState) {
        this.logState = logState;
    }

    @Basic
    @Column(name = "LOG_LOCK_COMMON", nullable = true, length = 50)
    public String getLogLockCommon() {
        return logLockCommon;
    }

    public void setLogLockCommon(String logLockCommon) {
        this.logLockCommon = logLockCommon;
    }

    @Override
    public String toString() {
        return "HyTerminalLogEntity{" +
                "logId=" + logId +
                ", logName='" + logName + '\'' +
                ", logValue=" + logValue +
                ", logType=" + logType +
                ", logSim=" + logSim +
                ", logContent='" + logContent + '\'' +
                ", logDate=" + logDate +
                ", logAccountIp='" + logAccountIp + '\'' +
                ", logAccountName='" + logAccountName + '\'' +
                ", logCommon=" + logCommon +
                ", moduleType=" + moduleType +
                ", carCph='" + carCph + '\'' +
                ", companyName='" + companyName + '\'' +
                ", carOwner='" + carOwner + '\'' +
                ", chassisNum='" + chassisNum + '\'' +
                ", logState=" + logState +
                ", logLockCommon='" + logLockCommon + '\'' +
                '}';
    }
}
