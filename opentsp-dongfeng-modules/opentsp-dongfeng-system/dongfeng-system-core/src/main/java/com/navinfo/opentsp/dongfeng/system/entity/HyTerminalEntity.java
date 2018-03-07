package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_terminal")
public class HyTerminalEntity {
    private BigInteger tId;
    private int district;
    private String tSim;
    private BigInteger tCommunicationId;
    private BigInteger tTeamId;
    private String tCode;
    private Integer tTypeId;
    private Integer tSimSign;
    private Integer delFlag;
    private Integer alarmHandle;
    private BigInteger tAutoCommunicationId;
    private Integer autoFlag;
    private BigInteger importTime;
    private Integer tStyle;
    private String tModel;
    private String tCommon;
    private Integer tMic;
    private String tCameraChannel;
    private String tLabelId;
    private BigInteger applianceEngineType;
    private BigInteger applianceCarType;
    private BigInteger oTeamId;
    private BigInteger createUser;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_ID", nullable = false, columnDefinition = "bigint")
    public BigInteger gettId() {
        return tId;
    }

    public void settId(BigInteger tId) {
        this.tId = tId;
    }

    @Basic
    @Column(name = "DISTRICT", nullable = false)
    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    @Basic
    @Column(name = "T_SIM", nullable = true, length = 50)
    public String gettSim() {
        return tSim;
    }

    public void settSim(String tSim) {
        this.tSim = tSim;
    }

    @Basic
    @Column(name = "T_COMMUNICATION_ID", nullable = true, columnDefinition = "bigint")
    public BigInteger gettCommunicationId() {
        return tCommunicationId;
    }

    public void settCommunicationId(BigInteger tCommunicationId) {
        this.tCommunicationId = tCommunicationId;
    }

    @Basic
    @Column(name = "T_TEAM_ID", nullable = true, columnDefinition = "bigint")
    public BigInteger gettTeamId() {
        return tTeamId;
    }

    public void settTeamId(BigInteger tTeamId) {
        this.tTeamId = tTeamId;
    }

    @Basic
    @Column(name = "T_CODE", nullable = true, length = 50)
    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    @Basic
    @Column(name = "T_TYPE_ID", nullable = true)
    public Integer gettTypeId() {
        return tTypeId;
    }

    public void settTypeId(Integer tTypeId) {
        this.tTypeId = tTypeId;
    }

    @Basic
    @Column(name = "T_SIM_SIGN", nullable = true)
    public Integer gettSimSign() {
        return tSimSign;
    }

    public void settSimSign(Integer tSimSign) {
        this.tSimSign = tSimSign;
    }

    @Basic
    @Column(name = "DEL_FLAG", nullable = true)
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "ALARM_HANDLE", nullable = true)
    public Integer getAlarmHandle() {
        return alarmHandle;
    }

    public void setAlarmHandle(Integer alarmHandle) {
        this.alarmHandle = alarmHandle;
    }

    @Basic
    @Column(name = "T_AUTO_COMMUNICATION_ID", nullable = true, columnDefinition = "bigint")
    public BigInteger gettAutoCommunicationId() {
        return tAutoCommunicationId;
    }

    public void settAutoCommunicationId(BigInteger tAutoCommunicationId) {
        this.tAutoCommunicationId = tAutoCommunicationId;
    }

    @Basic
    @Column(name = "AUTO_FLAG", nullable = true)
    public Integer getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(Integer autoFlag) {
        this.autoFlag = autoFlag;
    }

    @Basic
    @Column(name = "IMPORT_TIME", nullable = true, columnDefinition = "bigint")
    public BigInteger getImportTime() {
        return importTime;
    }

    public void setImportTime(BigInteger importTime) {
        this.importTime = importTime;
    }

    @Basic
    @Column(name = "T_STYLE", nullable = true)
    public Integer gettStyle() {
        return tStyle;
    }

    public void settStyle(Integer tStyle) {
        this.tStyle = tStyle;
    }

    @Basic
    @Column(name = "T_MODEL", nullable = true, length = 50)
    public String gettModel() {
        return tModel;
    }

    public void settModel(String tModel) {
        this.tModel = tModel;
    }

    @Basic
    @Column(name = "T_COMMON", nullable = true, length = 200)
    public String gettCommon() {
        return tCommon;
    }

    public void settCommon(String tCommon) {
        this.tCommon = tCommon;
    }

    @Basic
    @Column(name = "T_MIC", nullable = true)
    public Integer gettMic() {
        return tMic;
    }

    public void settMic(Integer tMic) {
        this.tMic = tMic;
    }

    @Basic
    @Column(name = "T_CAMERA_CHANNEL", nullable = true, length = 30)
    public String gettCameraChannel() {
        return tCameraChannel;
    }

    public void settCameraChannel(String tCameraChannel) {
        this.tCameraChannel = tCameraChannel;
    }

    @Basic
    @Column(name = "t_label_id", nullable = true, length = 50)
    public String gettLabelId() {
        return tLabelId;
    }

    public void settLabelId(String tLabelId) {
        this.tLabelId = tLabelId;
    }

    @Basic
    @Column(name = "applianceenginetype", columnDefinition = "bigint")
    public BigInteger getApplianceEngineType() {
        return applianceEngineType;
    }

    public void setApplianceEngineType(BigInteger applianceEngineType) {
        this.applianceEngineType = applianceEngineType;
    }

    @Basic
    @Column(name = "appliancecartype", columnDefinition = "bigint")
    public BigInteger getApplianceCarType() {
        return applianceCarType;
    }


    public void setApplianceCarType(BigInteger applianceCarType) {
        this.applianceCarType = applianceCarType;
    }

    @Basic
    @Column(name = "o_team_id", columnDefinition = "bigint")
    public BigInteger getoTeamId() {
        return oTeamId;
    }

    public void setoTeamId(BigInteger oTeamId) {
        this.oTeamId = oTeamId;
    }

    @Basic
    @Column(name = "CREATE_USER", columnDefinition = "bigint")
    public BigInteger getCreateUser() {
        return createUser;
    }

    public void setCreateUser(BigInteger createUser) {
        this.createUser = createUser;
    }
}
