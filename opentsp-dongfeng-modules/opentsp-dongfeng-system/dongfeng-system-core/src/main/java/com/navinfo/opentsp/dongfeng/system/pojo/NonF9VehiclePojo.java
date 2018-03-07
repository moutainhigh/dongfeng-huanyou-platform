package com.navinfo.opentsp.dongfeng.system.pojo;

import com.navinfo.opentsp.dongfeng.common.annotation.Report;

import java.math.BigInteger;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-11-28
 **/
public class NonF9VehiclePojo {
    /**
     * 车辆ID
     */
    private BigInteger vehicleId;
    /**
     * 结构号
     */
    @Report(columnNum = 2, header = "车架号")
    private String vin;
    /**
     * 底盘号
     */
    @Report(columnNum = 1, header = "底盘号")
    private String chassisNum;
    /**
     * 车型码
     */
    @Report(columnNum = 3, header = "车型码")
    private String carModelCode;

    /**
     * 车辆类型
     */
    @Report(columnNum = 8, header = "车辆型号")
    private String carTypeStr;
    /**
     * 发动机类型
     */
    @Report(columnNum = 10, header = "车辆型号")
    private String engineTypeStr;
    /**
     * 发动机号
     */
    @Report(columnNum = 9, header = "发动机号")
    private String engineCode;
    /**
     * 发动机功率
     */
    @Report(columnNum = 11, header = "发动机号")
    private String enginePower;

    /**
     * 制造日期
     */
    @Report(columnNum = 4, header = "下线/制造日期")
    private String manufactureDate;
    /**
     * 公告车型
     */
    @Report(columnNum = 12, header = "公告车型")
    private String carModelAnnouncement;
    /**
     * aak销售日期
     */
    @Report(columnNum = 12, header = "AAK日期")
    private String aakSaleDate;
    /**
     * 所属客户
     */
    private BigInteger businessId;
    /**
     * 客户名称
     */
    @Report(columnNum = 5, header = "所属客户")
    private String businessName;
    /**
     * 客户证件号
     */
    @Report(columnNum = 6, header = "客户证件号")
    private String businessCode;
    /**
     * 发票号
     */
    @Report(columnNum = 7, header = "客户发票号")
    private String invoiceNumber;

    public BigInteger getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(BigInteger vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setBusinessId(BigInteger businessId) {
        this.businessId = businessId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarModelCode() {
        return carModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        this.carModelCode = carModelCode;
    }


    public String getCarTypeStr() {
        return carTypeStr;
    }

    public void setCarTypeStr(String carTypeStr) {
        this.carTypeStr = carTypeStr;
    }


    public String getEngineTypeStr() {
        return engineTypeStr;
    }

    public void setEngineTypeStr(String engineTypeStr) {
        this.engineTypeStr = engineTypeStr;
    }


    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getCarModelAnnouncement() {
        return carModelAnnouncement;
    }

    public void setCarModelAnnouncement(String carModelAnnouncement) {
        this.carModelAnnouncement = carModelAnnouncement;
    }

    public String getAakSaleDate() {
        return aakSaleDate;
    }

    public void setAakSaleDate(String aakSaleDate) {
        this.aakSaleDate = aakSaleDate;
    }


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigInteger getBusinessId() {
        return businessId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    @Override
    public String toString() {
        return "NonF9VehiclePojo{" +
                "vehicleId=" + vehicleId +
                ", vin='" + vin + '\'' +
                ", chassisNum='" + chassisNum + '\'' +
                ", carModelCode='" + carModelCode + '\'' +
                ", carTypeStr='" + carTypeStr + '\'' +
                ", engineTypeStr='" + engineTypeStr + '\'' +
                ", engineCode='" + engineCode + '\'' +
                ", enginePower='" + enginePower + '\'' +
                ", manufactureDate='" + manufactureDate + '\'' +
                ", carModelAnnouncement='" + carModelAnnouncement + '\'' +
                ", aakSaleDate='" + aakSaleDate + '\'' +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                '}';
    }
}
