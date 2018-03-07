package com.navinfo.opentsp.dongfeng.report.pojo.car;

/**
 * 车辆查询参数
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-30
 * @modify
 * @copyright Navi Tsp
 */
public class CarQueryParam {
    private String accountId;
    private Integer userType;
    private String carId;
    private String chassis;
    private String cph;
    private String carType;
    private String engineType;
    private String companyName;
    private String businessName;
    private String tcode;
    private String fkCode;
    private Integer autoFlag;//同步方式
    private String pageNum;// 当前页数
    private String pageSize;// 每页多少条
    private String bSyncDate;//同步开始时间
    private String eSyncDate;//同步结束时间
    private String bNettingDate;//首次上线时间
    private String eNettingDate;//首次上线时间
    private String bProdOfflineDate;//生产线下线时间
    private String eProdOfflineDate;//生产线下线时间
    private String saleStatus;//std销售状态
    private String sim;// 通信号

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public String getFkCode() {
        return fkCode;
    }

    public void setFkCode(String fkCode) {
        this.fkCode = fkCode;
    }

    public Integer getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(Integer autoFlag) {
        this.autoFlag = autoFlag;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getbSyncDate() {
        return bSyncDate;
    }

    public void setbSyncDate(String bSyncDate) {
        this.bSyncDate = bSyncDate;
    }

    public String geteSyncDate() {
        return eSyncDate;
    }

    public void seteSyncDate(String eSyncDate) {
        this.eSyncDate = eSyncDate;
    }

    public String getbNettingDate() {
        return bNettingDate;
    }

    public void setbNettingDate(String bNettingDate) {
        this.bNettingDate = bNettingDate;
    }

    public String geteNettingDate() {
        return eNettingDate;
    }

    public void seteNettingDate(String eNettingDate) {
        this.eNettingDate = eNettingDate;
    }

    public String getbProdOfflineDate() {
        return bProdOfflineDate;
    }

    public void setbProdOfflineDate(String bProdOfflineDate) {
        this.bProdOfflineDate = bProdOfflineDate;
    }

    public String geteProdOfflineDate() {
        return eProdOfflineDate;
    }

    public void seteProdOfflineDate(String eProdOfflineDate) {
        this.eProdOfflineDate = eProdOfflineDate;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }
}
