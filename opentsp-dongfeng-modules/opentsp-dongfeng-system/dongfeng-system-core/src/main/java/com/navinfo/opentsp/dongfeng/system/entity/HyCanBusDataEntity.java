package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-18
 **/
@Entity
@Table(name = "hy_can_bus_data")
public class HyCanBusDataEntity {
    private BigInteger pid;
    private String canId;
    private Integer beginIndex;
    private Integer dataLength;
    private String dataDescribe;
    private String dataRange;
    private String dataAnalyticRule;

    @Id
    @Column(name = "pid", nullable = false, columnDefinition = "bigint")
    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "can_id", nullable = true, length = 10)
    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    @Basic
    @Column(name = "begin_index", nullable = true)
    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    @Basic
    @Column(name = "data_length", nullable = true)
    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    @Basic
    @Column(name = "data_describe", nullable = true, length = 100)
    public String getDataDescribe() {
        return dataDescribe;
    }

    public void setDataDescribe(String dataDescribe) {
        this.dataDescribe = dataDescribe;
    }

    @Basic
    @Column(name = "data_range", nullable = true, length = 100)
    public String getDataRange() {
        return dataRange;
    }

    public void setDataRange(String dataRange) {
        this.dataRange = dataRange;
    }

    @Basic
    @Column(name = "data_analytic_rule", nullable = true, length = 100)
    public String getDataAnalyticRule() {
        return dataAnalyticRule;
    }

    public void setDataAnalyticRule(String dataAnalyticRule) {
        this.dataAnalyticRule = dataAnalyticRule;
    }

}
