package com.navinfo.opentsp.dongfeng.report.dto;

import com.navinfo.opentsp.dongfeng.common.dto.BaseDto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/11.
 */
public class StatisticsOnlineDto  extends BaseDto implements Serializable {
    private String carId; //车辆I
    private String chassisNum;//底盘号
    private String carCph;//车牌号
    private String terId;  //一体机终端Id
    private String carTerId; //防拆盒终端Id
    private String companyName;//所属经销商t
    private String dealerCode; //经销商代码t
    private String carOwner; //所属客户（企业，车主）d
    private String carType; //车辆型号c
    private String engineNumber; //发动机型号d
    private String loanStatus;//销售状态：是否按揭
    private String autoFlag;//录入方式（DMS，平台）t
    private Long salesDate;//销售时间d
    private Long nettingDate;//首次上线时间c
    private Long carDate;//出库时间c
    private String salesDateStr;//销售时间d
    private String nettingDateStr;//首次上线时间c
    private String carDateStr;//出库时间c

    private String salesStatusValue;//销售状态
    private Double nlog;//入网经度c
    private Double nlat;//入网纬度c
    private String loction;//首次位置信息

    //缓存中数据
    private Double lastLon;//最新经度
    private Double lastLat;//最新纬度
    private String lastLoction;//末次位置信息
    private Long gpsDate;//末次位置时间
    private String gpsDateStr;//末次位置时间

    private String engineType;//发动机类型
    private Long xxsj; //销售时间
    private String firstLinkTimeStr;//首次连线时间


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    public String getTerId() {
        return terId;
    }

    public void setTerId(String terId) {
        this.terId = terId;
    }

    public String getCarTerId() {
        return carTerId;
    }

    public void setCarTerId(String carTerId) {
        this.carTerId = carTerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(String autoFlag) {
        this.autoFlag = autoFlag;
    }

    public Long getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Long salesDate) {
        this.salesDate = salesDate;
    }

    public Long getNettingDate() {
        return nettingDate;
    }

    public void setNettingDate(Long nettingDate) {
        this.nettingDate = nettingDate;
    }

    public Long getCarDate() {
        return carDate;
    }

    public void setCarDate(Long carDate) {
        this.carDate = carDate;
    }

    public String getSalesDateStr() {
        return salesDateStr;
    }

    public void setSalesDateStr(String salesDateStr) {
        this.salesDateStr = salesDateStr;
    }

    public String getNettingDateStr() {
        return nettingDateStr;
    }

    public void setNettingDateStr(String nettingDateStr) {
        this.nettingDateStr = nettingDateStr;
    }

    public String getCarDateStr() {
        return carDateStr;
    }

    public void setCarDateStr(String carDateStr) {
        this.carDateStr = carDateStr;
    }

    public String getSalesStatusValue() {
        return salesStatusValue;
    }

    public void setSalesStatusValue(String salesStatusValue) {
        this.salesStatusValue = salesStatusValue;
    }

    public Double getNlog() {
        return nlog;
    }

    public void setNlog(Double nlog) {
        this.nlog = nlog;
    }

    public Double getNlat() {
        return nlat;
    }

    public void setNlat(Double nlat) {
        this.nlat = nlat;
    }

    public String getLoction() {
        return loction;
    }

    public void setLoction(String loction) {
        this.loction = loction;
    }

    public Double getLastLon() {
        return lastLon;
    }

    public void setLastLon(Double lastLon) {
        this.lastLon = lastLon;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    public String getLastLoction() {
        return lastLoction;
    }

    public void setLastLoction(String lastLoction) {
        this.lastLoction = lastLoction;
    }

    public Long getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(Long gpsDate) {
        this.gpsDate = gpsDate;
    }

    public String getGpsDateStr() {
        return gpsDateStr;
    }

    public void setGpsDateStr(String gpsDateStr) {
        this.gpsDateStr = gpsDateStr;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Long getXxsj() {
        return xxsj;
    }

    public void setXxsj(Long xxsj) {
        this.xxsj = xxsj;
    }

    public String getFirstLinkTimeStr() {
        return firstLinkTimeStr;
    }

    public void setFirstLinkTimeStr(String firstLinkTimeStr) {
        this.firstLinkTimeStr = firstLinkTimeStr;
    }


    @Override
    public String toString() {
        return "StatisticsOnlinePojo{" +
                "carId='" + carId + '\'' +
                ", chassisNum='" + chassisNum + '\'' +
                ", carCph='" + carCph + '\'' +
                ", terId='" + terId + '\'' +
                ", carTerId='" + carTerId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", dealerCode='" + dealerCode + '\'' +
                ", carOwner='" + carOwner + '\'' +
                ", carType='" + carType + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", loanStatus='" + loanStatus + '\'' +
                ", autoFlag='" + autoFlag + '\'' +
                ", salesDate=" + salesDate +
                ", nettingDate=" + nettingDate +
                ", carDate=" + carDate +
                ", salesDateStr='" + salesDateStr + '\'' +
                ", nettingDateStr='" + nettingDateStr + '\'' +
                ", carDateStr='" + carDateStr + '\'' +
                ", salesStatusValue='" + salesStatusValue + '\'' +
                ", nlog=" + nlog +
                ", nlat=" + nlat +
                ", loction='" + loction + '\'' +
                ", lastLon=" + lastLon +
                ", lastLat=" + lastLat +
                ", lastLoction='" + lastLoction + '\'' +
                ", gpsDate=" + gpsDate +
                ", gpsDateStr='" + gpsDateStr + '\'' +
                ", engineType='" + engineType + '\'' +
                ", xxsj=" + xxsj +
                ", firstLinkTimeStr='" + firstLinkTimeStr + '\'' +
                '}';
    }
}
