package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-18
 **/
@Entity
@Table(name = "hy_terminal_setting_param")
public class HyTerminalSettingParamEntity {
    private BigInteger id;
    private BigInteger terminalId;
    private Integer paramType;
    private String paramJsonValue;
    private BigInteger settingTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "terminal_id", nullable = true, columnDefinition = "bigint")
    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }

    @Basic
    @Column(name = "param_type", nullable = true)
    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    @Basic
    @Column(name = "param_json_value", nullable = true, length = 500)
    public String getParamJsonValue() {
        return paramJsonValue;
    }

    public void setParamJsonValue(String paramJsonValue) {
        this.paramJsonValue = paramJsonValue;
    }

    @Basic
    @Column(name = "setting_time", nullable = true, columnDefinition = "bigint")
    public BigInteger getSettingTime() {
        return settingTime;
    }

    public void setSettingTime(BigInteger settingTime) {
        this.settingTime = settingTime;
    }
}
