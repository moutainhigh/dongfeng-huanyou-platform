package com.navinfo.opentsp.dongfeng.common.entity;

import javax.persistence.*;

/**
 * 平台日志
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_platform_log")
public class HyPlatformLogEntity {
    private long logId;
    private String logName;
    private Integer logValue;
    private Integer logType;
    private String logContent;
    private Long logDate;
    private String logAccountIp;
    private String logAccountName;
    private String logOperobj;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOG_ID", nullable = false)
    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
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
    @Column(name = "LOG_OPEROBJ", nullable = true, length = 100)
    public String getLogOperobj() {
        return logOperobj;
    }

    public void setLogOperobj(String logOperobj) {
        this.logOperobj = logOperobj;
    }

}
