package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * Created by Administrator on 2017/3/9.
 */

public class TerminalModelPojo {

    private BigInteger tmId;
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

    public BigInteger getTmId() {
        return tmId;
    }

    public void setTmId(BigInteger tmId) {
        this.tmId = tmId;
    }

    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName;
    }

    public String getTmFactoryName() {
        return tmFactoryName;
    }

    public void setTmFactoryName(String tmFactoryName) {
        this.tmFactoryName = tmFactoryName;
    }

    public String getTmFactoryNum() {
        return tmFactoryNum;
    }

    public void setTmFactoryNum(String tmFactoryNum) {
        this.tmFactoryNum = tmFactoryNum;
    }

    public String getTmPublicNum() {
        return tmPublicNum;
    }

    public void setTmPublicNum(String tmPublicNum) {
        this.tmPublicNum = tmPublicNum;
    }

    public String getTmLoctionModel() {
        return tmLoctionModel;
    }

    public void setTmLoctionModel(String tmLoctionModel) {
        this.tmLoctionModel = tmLoctionModel;
    }

    public String getTmCommuModel() {
        return tmCommuModel;
    }

    public void setTmCommuModel(String tmCommuModel) {
        this.tmCommuModel = tmCommuModel;
    }

    public String getTmCarMatched() {
        return tmCarMatched;
    }

    public void setTmCarMatched(String tmCarMatched) {
        this.tmCarMatched = tmCarMatched;
    }

    public String getTmModelNum() {
        return tmModelNum;
    }

    public void setTmModelNum(String tmModelNum) {
        this.tmModelNum = tmModelNum;
    }

    public String getTmNotes() {
        return tmNotes;
    }

    public void setTmNotes(String tmNotes) {
        this.tmNotes = tmNotes;
    }
}
