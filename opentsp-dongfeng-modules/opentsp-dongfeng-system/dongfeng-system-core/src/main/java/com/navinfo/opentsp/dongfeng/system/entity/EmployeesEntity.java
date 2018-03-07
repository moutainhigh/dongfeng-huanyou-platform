package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Sunyu on 2017/3/17.
 */
@Entity
@Table(name = "hy_employees")
public class EmployeesEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "E_ID", columnDefinition = "bigint")
    private BigInteger id;

    /**
     * 分区服务编码
     */
    @Column(nullable = false, name = "DISTRICT")
    private Integer district;

    /**
     * 姓名
     */
    @Column(nullable = true, name = "E_NAME")
    private String name;

    /**
     * 证件类型（数据字典）
     */
    @Column(nullable = true, name = "E_CARD_TYPE")
    private Integer cardType;

    /**
     * 证件编号
     */
    @Column(nullable = true, name = "E_CARD")
    private String card;

    /**
     * 车队ID
     */
    @Column(nullable = true, name = "E_TEAM_ID")
    private Long teamId;

    /**
     * 从业资格证号码
     */
    @Column(nullable = true, name = "E_CYZGHM")
    private String cyzghm;

    /**
     * 驾驶员号码
     */
    @Column(nullable = true, name = "E_DRIVER_NUMBER")
    private String driverNumber;

    /**
     * 登签卡号
     */
    @Column(nullable = true, name = "E_CARD_DQK")
    private String cardDqk;

    /**
     * 逻辑删除标志
     */
    @Column(nullable = true, name = "DEL_FLAG")
    private Integer delFlag;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getCyzghm() {
        return cyzghm;
    }

    public void setCyzghm(String cyzghm) {
        this.cyzghm = cyzghm;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getCardDqk() {
        return cardDqk;
    }

    public void setCardDqk(String cardDqk) {
        this.cardDqk = cardDqk;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}