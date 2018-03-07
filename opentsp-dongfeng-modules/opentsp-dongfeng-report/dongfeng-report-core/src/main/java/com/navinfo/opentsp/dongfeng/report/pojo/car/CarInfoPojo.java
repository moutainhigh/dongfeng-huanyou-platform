package com.navinfo.opentsp.dongfeng.report.pojo.car;

import java.math.BigInteger;

public class CarInfoPojo {
    private BigInteger carId;// 车辆ID
    private String chassisNum;// 底盘号
    private String carNo;//车牌号
    private String registerTimeStr;//注册时间
    private BigInteger tId;//北斗一体机ID
    private String terminalCode;// 一体机CODE
    private String terminalSim;// 一体机SIM
    private BigInteger controllerId;//控制器ID
    private String fkCode;//控制器CODE
    private String fkSim;//FK控制器SIM
    private BigInteger communicationId;//通讯号
    private BigInteger dealerId;//经销商ID
    private String dealerCode;//经销商编码
    private String companyName;// 经销商名称
    private BigInteger businessId;// 客户ID
    private String businessCode;//客户证件号
    private String businessName;//客户名称
    private Integer saleStatusId;//销售状态ID
    private String salesStatusValue;//销售状态名称
    private String salesDateStr;//销售时间
    private Integer aakSalesStatus;//aak销售状态名称
    private String aakSalesDateStr;//aak销售时间
    private String autoFlagStr;//同步方式
    private String removalTimeStr;//出库时间
    private String firstGpsTimeStr;//首次上线时间
    private String firstGpsLat;//首次上线纬度
    private String firstGpsLng;//首次上线经度
    private String invoiceNumber;//发票号
    private Integer carTypeCode;//车辆类型ID
    private String carTypeName;//车辆类型名称
    private String engineTypeCode;//发动机类型
    private String engineTypeName;//发动机类型名称
    private String engineNumber;//发动机号
    private String structureNum;//结构号
    private Integer color;//车牌颜色
    private String syncTime;//同步时间


    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getRegisterTimeStr() {
        return registerTimeStr;
    }

    public void setRegisterTimeStr(String registerTimeStr) {
        this.registerTimeStr = registerTimeStr;
    }

    public BigInteger gettId() {
        return tId;
    }

    public void settId(BigInteger tId) {
        this.tId = tId;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getTerminalSim() {
        return terminalSim;
    }

    public void setTerminalSim(String terminalSim) {
        this.terminalSim = terminalSim;
    }

    public BigInteger getControllerId() {
        return controllerId;
    }

    public void setControllerId(BigInteger controllerId) {
        this.controllerId = controllerId;
    }

    public String getFkCode() {
        return fkCode;
    }

    public void setFkCode(String fkCode) {
        this.fkCode = fkCode;
    }

    public String getFkSim() {
        return fkSim;
    }

    public void setFkSim(String fkSim) {
        this.fkSim = fkSim;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    public BigInteger getDealerId() {
        return dealerId;
    }

    public void setDealerId(BigInteger dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigInteger getBusinessId() {
        return businessId;
    }

    public void setBusinessId(BigInteger businessId) {
        this.businessId = businessId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getSaleStatusId() {
        return saleStatusId;
    }

    public void setSaleStatusId(Integer saleStatusId) {
        this.saleStatusId = saleStatusId;
    }

    public String getSalesStatusValue() {
        return salesStatusValue;
    }

    public void setSalesStatusValue(String salesStatusValue) {
        this.salesStatusValue = salesStatusValue;
    }

    public String getSalesDateStr() {
        return salesDateStr;
    }

    public void setSalesDateStr(String salesDateStr) {
        this.salesDateStr = salesDateStr;
    }

    public String getAutoFlagStr() {
        return autoFlagStr;
    }

    public void setAutoFlagStr(String autoFlagStr) {
        this.autoFlagStr = autoFlagStr;
    }

    public String getRemovalTimeStr() {
        return removalTimeStr;
    }

    public void setRemovalTimeStr(String removalTimeStr) {
        this.removalTimeStr = removalTimeStr;
    }

    public String getFirstGpsTimeStr() {
        return firstGpsTimeStr;
    }

    public void setFirstGpsTimeStr(String firstGpsTimeStr) {
        this.firstGpsTimeStr = firstGpsTimeStr;
    }

    public String getFirstGpsLat() {
        return firstGpsLat;
    }

    public void setFirstGpsLat(String firstGpsLat) {
        this.firstGpsLat = firstGpsLat;
    }

    public String getFirstGpsLng() {
        return firstGpsLng;
    }

    public void setFirstGpsLng(String firstGpsLng) {
        this.firstGpsLng = firstGpsLng;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getCarTypeCode() {
        return carTypeCode;
    }

    public void setCarTypeCode(Integer carTypeCode) {
        this.carTypeCode = carTypeCode;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getEngineTypeCode() {
        return engineTypeCode;
    }

    public void setEngineTypeCode(String engineTypeCode) {
        this.engineTypeCode = engineTypeCode;
    }

    public String getEngineTypeName() {
        return engineTypeName;
    }

    public void setEngineTypeName(String engineTypeName) {
        this.engineTypeName = engineTypeName;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getAakSalesStatus() {
        return aakSalesStatus;
    }

    public void setAakSalesStatus(Integer aakSalesStatus) {
        this.aakSalesStatus = aakSalesStatus;
    }

    public String getAakSalesDateStr() {
        return aakSalesDateStr;
    }

    public void setAakSalesDateStr(String aakSalesDateStr) {
        this.aakSalesDateStr = aakSalesDateStr;
    }
}
