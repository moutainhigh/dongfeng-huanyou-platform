package com.navinfo.opentsp.dongfeng.report.entity.market;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_scancode_emptyin_info")
public class ScanCodeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false, name = "id", columnDefinition="bigint")
    private BigInteger ID;

    @Column(nullable = true, name = "CAR_CPH")
    private String plateNum;

    @Column(nullable = true, name = "CHASSIS_NUM")
    private String chassisNum;

    @Column(nullable = true, name = "T_CODE")
    private String code;

    @Column(nullable = true, name = "T_SIM")
    private String sim;

    @Column(nullable = true, name = "STATE")
    private Integer state;

    @Column(nullable = true, name = "CAR_TEAM_ID", columnDefinition="bigint")
    private BigInteger teamId;

    @Column(nullable = true, name = "curr_process")
    private String currProcess;

    @Column(nullable = true, name = "SYNC_STATE", columnDefinition="bigint")
    private BigInteger syncState;

    @Column(nullable = true, name = "operate_user")
    private String operateUser;

    @Column(nullable = true, name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public BigInteger getID() {
        return ID;
    }

    public void setID(BigInteger ID) {
        this.ID = ID;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public String getCurrProcess() {
        return currProcess;
    }

    public void setCurrProcess(String currProcess) {
        this.currProcess = currProcess;
    }

    public BigInteger getSyncState() {
        return syncState;
    }

    public void setSyncState(BigInteger syncState) {
        this.syncState = syncState;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
