package com.navinfo.opentsp.dongfeng.report.dto.disklibrary;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */

public class DisklibraryDto {
    /**
     * 盘库同步表字段
     */
    //VIN码 jf0502
    private String vin;
    //有效标识 jf0513
    private String invaFlag;
    //扫码枪代码 jf0500
    private String gunCode;
    //经销商代码 jf0506
    private String dealerCode;
    //经销商名称 jf0507
    private String dealerName;
    //扫描时间 jf0501
    private String scanTime;
    //扫描信息状态  jf0511
    private Character scanStatus;
    //网点代码 jf0519
    private String netCode;
    //备注 jf0518
    private String remark;
    /**
     * 寰游表字段
     */
    //产品代码  根据VIN码在寰游的表中查询出车型码
    private String productCode;
    //省份
    private String province;
    //网点名称
    private String netName;
    //网点位置
    private String netAddress;
    //车辆位置（经纬度）
    private double carLat;
    private double carLng;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getInvaFlag() {
        return invaFlag;
    }

    public void setInvaFlag(String invaFlag) {
        this.invaFlag = invaFlag;
    }

    public String getGunCode() {
        return gunCode;
    }

    public void setGunCode(String gunCode) {
        this.gunCode = gunCode;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public Character getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(Character scanStatus) {
        this.scanStatus = scanStatus;
    }

    public String getNetCode() {
        return netCode;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public double getCarLat() {
        return carLat;
    }

    public void setCarLat(double carLat) {
        this.carLat = carLat;
    }

    public double getCarLng() {
        return carLng;
    }

    public void setCarLng(double carLng) {
        this.carLng = carLng;
    }
}