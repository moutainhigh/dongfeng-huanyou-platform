package com.navinfo.opentsp.dongfeng.report.pojo;

import java.math.BigInteger;

/**
 * Created by cz on 2017/3/11.
 * 信息同步上线结果对象
 */
public class StatisticsOnlinePojo {
    private BigInteger carId;           //车辆ID

    private String chassisNum;  //底盘号
    private String carCph;      //车牌号
    private Integer carType;   //车辆类型
    private BigInteger  carDate;     //出库时间
    private BigInteger nettingTime;  //首次上线时间
    private BigInteger carTeamId;    //车组id
    private BigInteger carTerminal;  //一体机id
    private BigInteger  carTerminalId; //防拆盒id
    private String oilCapacity;  //油箱容量
    private BigInteger nettingLog;     //入网位置-经度
    private BigInteger nettingLat;     //入网位置-纬度
    private BigInteger offlineTime;   //生产下线时间
    private BigInteger removalTime;  //出库时间
    private BigInteger registerTime; //末次注册时间
    private Integer batteryType; //电池类型
    private Integer batteryBatches; //电池批次
    private String engineNumber;   //车辆号
    private String carOwner;      //所属客户
    private String engineType;  //发动机编码
    private BigInteger salesDate;     //销售日期
    private String structureNum; //结构号
    private String invoiceNumber;//发票号
    private String businessCode; //企业许可证号
    private Integer autoFlag;     //同步标识
    private Integer salesStatus;  //销售状态
    
    private String carOwnerStr;      //所属客户
    
    private String tname; // 经销商

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

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public BigInteger getCarDate() {
        return carDate;
    }

    public void setCarDate(BigInteger carDate) {
        this.carDate = carDate;
    }

    public BigInteger getNettingTime() {
        return nettingTime;
    }

    public void setNettingTime(BigInteger nettingTime) {
        this.nettingTime = nettingTime;
    }

    public BigInteger getCarTeamId() {
        return carTeamId;
    }

    public void setCarTeamId(BigInteger carTeamId) {
        this.carTeamId = carTeamId;
    }

    public BigInteger getCarTerminal() {
        return carTerminal;
    }

    public void setCarTerminal(BigInteger carTerminal) {
        this.carTerminal = carTerminal;
    }

    public BigInteger getCarTerminalId() {
        return carTerminalId;
    }

    public void setCarTerminalId(BigInteger carTerminalId) {
        this.carTerminalId = carTerminalId;
    }

    public String getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(String oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    public BigInteger getNettingLog() {
        return nettingLog;
    }

    public void setNettingLog(BigInteger nettingLog) {
        this.nettingLog = nettingLog;
    }

    public BigInteger getNettingLat() {
        return nettingLat;
    }

    public void setNettingLat(BigInteger nettingLat) {
        this.nettingLat = nettingLat;
    }

    public BigInteger getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(BigInteger offlineTime) {
        this.offlineTime = offlineTime;
    }

    public BigInteger getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(BigInteger removalTime) {
        this.removalTime = removalTime;
    }

    public BigInteger getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(BigInteger registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(Integer batteryType) {
        this.batteryType = batteryType;
    }

    public Integer getBatteryBatches() {
        return batteryBatches;
    }

    public void setBatteryBatches(Integer batteryBatches) {
        this.batteryBatches = batteryBatches;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public BigInteger getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(BigInteger salesDate) {
        this.salesDate = salesDate;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Integer getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(Integer autoFlag) {
        this.autoFlag = autoFlag;
    }

    public Integer getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(Integer salesStatus) {
        this.salesStatus = salesStatus;
    }
    
    public String getCarOwnerStr() {
		return carOwnerStr;
	}

	public void setCarOwnerStr(String carOwnerStr) {
		this.carOwnerStr = carOwnerStr;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Override
    public String toString() {
        return "StatisticsOnlinePojo{" +
                "carId=" + carId +
                ", chassisNum='" + chassisNum + '\'' +
                ", carCph='" + carCph + '\'' +
                ", carType=" + carType +
                ", carDate=" + carDate +
                ", nettingTime=" + nettingTime +
                ", carTeamId=" + carTeamId +
                ", carTerminal=" + carTerminal +
                ", carTerminalId=" + carTerminalId +
                ", oilCapacity='" + oilCapacity + '\'' +
                ", nettingLog=" + nettingLog +
                ", nettingLat=" + nettingLat +
                ", offlineTime=" + offlineTime +
                ", removalTime=" + removalTime +
                ", registerTime=" + registerTime +
                ", batteryType=" + batteryType +
                ", batteryBatches=" + batteryBatches +
                ", engineNumber='" + engineNumber + '\'' +
                ", carOwner=" + carOwner +
                ", engineType='" + engineType + '\'' +
                ", salesDate=" + salesDate +
                ", structureNum='" + structureNum + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", businessCode='" + businessCode + '\'' +
                '}';
    }
}
