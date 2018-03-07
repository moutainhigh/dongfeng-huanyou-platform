package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */
@Entity
@Table(name = "hy_terminal_model")
public class TerminalModelEntity implements Serializable {
    private long tmId;
    //终端型号
    private String tmName;

    //厂家名称
    private String tmFactoryName;

    //厂家编号
    private String tmFactoryNum;

    //交通部公告批次
    private String tmPublicNum;

    //定位模式GPS,北斗,GLANOSS,Galileo
    private String tmLoctionModel;

    //通信模式GSM,CDMA,WCDMA,TD-SCDMA,CDMA2000,TD-LTE,LTE FDD
    private String tmCommuModel;

    //适用车型客,货,危
    private String tmCarMatched;

    //型号编号
    private String tmModelNum;

    //备注
    private String tmNotes;

    @Id
    @Column(name = "TM_ID")
    public long getTmId() {
        return tmId;
    }

    public void setTmId(long tmId) {
        this.tmId = tmId;
    }

    @Column(name = "TM_NAME")
    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName;
    }

    @Column(name = "TM_FACTORY_NAME")
    public String getTmFactoryName() {
        return tmFactoryName;
    }

    public void setTmFactoryName(String tmFactoryName) {
        this.tmFactoryName = tmFactoryName;
    }

    @Column(name = "TM_FACTORY_NUM")
    public String getTmFactoryNum() {
        return tmFactoryNum;
    }

    public void setTmFactoryNum(String tmFactoryNum) {
        this.tmFactoryNum = tmFactoryNum;
    }

    @Column(name = "TM_PUBLIC_NUM")
    public String getTmPublicNum() {
        return tmPublicNum;
    }

    public void setTmPublicNum(String tmPublicNum) {
        this.tmPublicNum = tmPublicNum;
    }

    @Column(name = "TM_LOCTION_MODEL")
    public String getTmLoctionModel() {
        return tmLoctionModel;
    }

    public void setTmLoctionModel(String tmLoctionModel) {
        this.tmLoctionModel = tmLoctionModel;
    }

    @Column(name = "TM_COMMU_MODEL")
    public String getTmCommuModel() {
        return tmCommuModel;
    }

    public void setTmCommuModel(String tmCommuModel) {
        this.tmCommuModel = tmCommuModel;
    }

    @Column(name = "TM_CAR_MATCHED")
    public String getTmCarMatched() {
        return tmCarMatched;
    }

    public void setTmCarMatched(String tmCarMatched) {
        this.tmCarMatched = tmCarMatched;
    }

    @Column(name = "TM_MODEL_NUM")
    public String getTmModelNum() {
        return tmModelNum;
    }

    public void setTmModelNum(String tmModelNum) {
        this.tmModelNum = tmModelNum;
    }

    @Column(name = "TM_NOTES")
    public String getTmNotes() {
        return tmNotes;
    }

    public void setTmNotes(String tmNotes) {
        this.tmNotes = tmNotes;
    }

}
