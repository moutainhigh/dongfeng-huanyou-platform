package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/09.
 */
@Entity
@Table(name = "hy_atc_mapping")
public class AccountTeamMapping implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false, name = "ATC_ID", columnDefinition="bigint")
    private BigInteger atcId;

    @Column(nullable = true, name = "CAR_TEAM_ID", columnDefinition="bigint")
    private BigInteger carTeamId;

    @Column(nullable = true, name = "ACCOUNT_ID", columnDefinition="bigint")
    private BigInteger accountId;

    @Column(nullable = true, name = "ATC_TYPE")
    private int atcType;

    public BigInteger getAtcId() {
        return atcId;
    }

    public void setAtcId(BigInteger atcId) {
        this.atcId = atcId;
    }

    public BigInteger getCarTeamId() {
        return carTeamId;
    }

    public void setCarTeamId(BigInteger carTeamId) {
        this.carTeamId = carTeamId;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public int getAtcType() {
        return atcType;
    }

    public void setAtcType(int atcType) {
        this.atcType = atcType;
    }
}
