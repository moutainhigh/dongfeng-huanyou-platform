package com.navinfo.opentsp.dongfeng.report.pojo.general;

import com.navinfo.opentsp.dongfeng.report.pojo.market.ImportScanCodePojo;

import java.math.BigInteger;

/**
 * 运营车况查看
 * QueryCarOperatePojo
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryCarOperatePojo {
    //车辆主键
    private BigInteger id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //同步时间
    private String syncTime;
    //发动机类型
    private String engineType;
    //点火状态
    private String accStatus;
    //最近上报时间
    private String lastGpsTime;
    //末次有效位置经度
    private Double lastGpsLng;
    //末次有效位置纬度
    private Double lastGpsLat;
    //GPS通信状态
    private String gpsCommu;
    //GPS电源状态
    private String gpsPower;
    //GPS天线状态
    private String gpsWire;
    //GPS定位状态:0未定位1定位
    private String gpsLocation;
    //整车里程（Km）:can里程
    private String mileage;
    //发动机累计运行时间
    private String cumuRunningTime;
    //当前油量（%）
    private String oilValue;
    //当前转速
    private String rotation;
    //终端通信号
    private BigInteger commId;
    //邮箱总容量
    private String oilCapacity;
    //加速踏板开度(5.3.2.9-车辆状态附加信息)
    private String accPedalPos;
    //标准里程
    private String gpsMileage;
    //标准油耗
    private String totalFuelConsumption;
    //天然气or柴油车：0表示燃气，1表示燃油
    private Integer fuel;
    //仪表里程
    private String mileageDD;
    //积分油耗
    private String integralFuelConsumption;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getLastGpsTime() {
        return lastGpsTime;
    }

    public void setLastGpsTime(String lastGpsTime) {
        this.lastGpsTime = lastGpsTime;
    }

    public Double getLastGpsLng() {
        return lastGpsLng;
    }

    public void setLastGpsLng(Double lastGpsLng) {
        this.lastGpsLng = lastGpsLng;
    }

    public Double getLastGpsLat() {
        return lastGpsLat;
    }

    public void setLastGpsLat(Double lastGpsLat) {
        this.lastGpsLat = lastGpsLat;
    }

    public String getGpsCommu() {
        return gpsCommu;
    }

    public void setGpsCommu(String gpsCommu) {
        this.gpsCommu = gpsCommu;
    }

    public String getGpsPower() {
        return gpsPower;
    }

    public void setGpsPower(String gpsPower) {
        this.gpsPower = gpsPower;
    }

    public String getGpsWire() {
        return gpsWire;
    }

    public void setGpsWire(String gpsWire) {
        this.gpsWire = gpsWire;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCumuRunningTime() {
        return cumuRunningTime;
    }

    public void setCumuRunningTime(String cumuRunningTime) {
        this.cumuRunningTime = cumuRunningTime;
    }

    public String getOilValue() {
        return oilValue;
    }

    public void setOilValue(String oilValue) {
        this.oilValue = oilValue;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public String getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(String oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    public String getAccPedalPos() {
        return accPedalPos;
    }

    public void setAccPedalPos(String accPedalPos) {
        this.accPedalPos = accPedalPos;
    }

    public String getGpsMileage() {
        return gpsMileage;
    }

    public void setGpsMileage(String gpsMileage) {
        this.gpsMileage = gpsMileage;
    }

    public String getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public void setTotalFuelConsumption(String totalFuelConsumption) {
        this.totalFuelConsumption = totalFuelConsumption;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public String getMileageDD() {
        return mileageDD;
    }

    public void setMileageDD(String mileageDD) {
        this.mileageDD = mileageDD;
    }

    public String getIntegralFuelConsumption() {
        return integralFuelConsumption;
    }

    public void setIntegralFuelConsumption(String integralFuelConsumption) {
        this.integralFuelConsumption = integralFuelConsumption;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImportScanCodePojo)) {
            return false;
        }

        final QueryCarOperatePojo carOperatePojo = (QueryCarOperatePojo) obj;
        return this.getId() == carOperatePojo.getId();
    }
}
