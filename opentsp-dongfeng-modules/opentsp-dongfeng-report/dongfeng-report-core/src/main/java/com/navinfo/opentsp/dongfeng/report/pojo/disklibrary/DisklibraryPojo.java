package com.navinfo.opentsp.dongfeng.report.pojo.disklibrary;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */

public class DisklibraryPojo {
    /**
     * 盘库同步表字段
     */
    //VIN码 jf0502
    private String vin;
    //有效标识 jf0513
    private Character invaFlag;
    //扫码枪代码 jf0500
    private String gunCode;
    //经销商代码 jf0506
    private String dealerCode;
    //经销商名称 jf0507
    private String dealerName;
    //扫描时间 jf0501
    private String scanTime;
    //处理标识
    private String dealSign = "增加";
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
    //网点位置(经纬度)
    private BigInteger netLat;
    private BigInteger netLng;
    //网点位置
    private String netAddress;
    //车辆位置（经纬度）
    private long carLat;
    private long carLng;
    //车辆位置
    private String carAddress;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Character getInvaFlag() {
        return invaFlag;
    }

    public void setInvaFlag(Character invaFlag) {
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

    public String getDealSign() {
        return dealSign;
    }

    public void setDealSign(String dealSign) {
        this.dealSign = dealSign;
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

    public BigInteger getNetLat() {
        return netLat;
    }

    public void setNetLat(BigInteger netLat) {
        this.netLat = netLat;
    }

    public BigInteger getNetLng() {
        return netLng;
    }

    public void setNetLng(BigInteger netLng) {
        this.netLng = netLng;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public long getCarLat() {
        return carLat;
    }

    public void setCarLat(long carLat) {
        this.carLat = carLat;
    }

    public long getCarLng() {
        return carLng;
    }

    public void setCarLng(long carLng) {
        this.carLng = carLng;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCarAddress() {
        return carAddress;
    }

    public void setCarAddress(String carAddress) {
        this.carAddress = carAddress;
    }
}