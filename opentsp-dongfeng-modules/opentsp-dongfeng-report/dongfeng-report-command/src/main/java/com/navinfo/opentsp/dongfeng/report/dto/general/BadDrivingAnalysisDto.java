package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.math.BigInteger;

/**
 * Created by zhangtiantong on 2018/2/28/028.
 */
public class BadDrivingAnalysisDto {

    /**
     * 车辆ID
     */
    private BigInteger carId;

    /**
     * 底盘号
     */
    private String chassisNum;

    /**
     * 车牌号
     */
    private String plateNum;

    /**
     * 北斗一体机ID
     */
    private String bdTerCode;

    /**
     * FK控制器ID
     */
    private String fkTerCode;

    /**
     * 所属经销商
     */
    private String tName;

    /**
     * 所属客户
     */
    private String businessName;

    /**
     * 车辆型号
     */
    private String carModel;

    /**
     * 发动机型号
     */
    private String engineNumber;

    /**
     * 发动机类型
     */
    private String engineType;

    /**
     * 车架号
     */
    private String structureNum;


    /**
     * 异常驾驶时间
     */
    private String badDrivingDate;

    /**
     * 持续时间
     */
    private String badDrivingDuration;

    /**
     * 终端通讯号
     */
    private BigInteger communicationId;


    /**
     * 车辆VIN码
     */
    private String vin;

    /**
     * 急加速
     */
    private int accelerateCount;

    /**
     * 急减速
     */
    private int decelerateCount;

    /**
     * 急转弯
     */
    private int sharpTurnCount;

    /**
     * 制动里程(米)
     */
    private int brakeMileCount;

    /**
     * 超长怠速
     */
    private int longIdlingCount;

    /**
     * 高档起步
     */
    private int highGearStartCount;

    /**
     * 低档高速
     */
    private int lowGearHighSpeedCount;

    /**
     * 空档滑行
     */
    private int neutralCoastingCount;

    /**
     * 熄火滑行
     */
    private int stallCoastingCount;

    /**
     * 冷启动
     */
    private int coldingStartCount;

    /**
     * 夜间行驶
     */
    private int nightDrivingCount;

    /**
     * 全油门
     */
    private int fullAcceleratorCount;

    /**
     * 大油门
     */
    private int largeAcceleratorCount;

    /**
     * 高速行驶
     */
    private int fastSpeedCount;

    /**
     * 怠速空调
     */
    private int idlingAirConditionCount;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getAccelerateCount() {
        return accelerateCount;
    }

    public void setAccelerateCount(int accelerateCount) {
        this.accelerateCount = accelerateCount;
    }

    public int getDecelerateCount() {
        return decelerateCount;
    }

    public void setDecelerateCount(int decelerateCount) {
        this.decelerateCount = decelerateCount;
    }

    public int getSharpTurnCount() {
        return sharpTurnCount;
    }

    public void setSharpTurnCount(int sharpTurnCount) {
        this.sharpTurnCount = sharpTurnCount;
    }

    public int getBrakeMileCount() {
        return brakeMileCount;
    }

    public void setBrakeMileCount(int brakeMileCount) {
        this.brakeMileCount = brakeMileCount;
    }

    public int getLongIdlingCount() {
        return longIdlingCount;
    }

    public void setLongIdlingCount(int longIdlingCount) {
        this.longIdlingCount = longIdlingCount;
    }

    public int getHighGearStartCount() {
        return highGearStartCount;
    }

    public void setHighGearStartCount(int highGearStartCount) {
        this.highGearStartCount = highGearStartCount;
    }

    public int getLowGearHighSpeedCount() {
        return lowGearHighSpeedCount;
    }

    public void setLowGearHighSpeedCount(int lowGearHighSpeedCount) {
        this.lowGearHighSpeedCount = lowGearHighSpeedCount;
    }

    public int getNeutralCoastingCount() {
        return neutralCoastingCount;
    }

    public void setNeutralCoastingCount(int neutralCoastingCount) {
        this.neutralCoastingCount = neutralCoastingCount;
    }

    public int getStallCoastingCount() {
        return stallCoastingCount;
    }

    public void setStallCoastingCount(int stallCoastingCount) {
        this.stallCoastingCount = stallCoastingCount;
    }

    public int getColdingStartCount() {
        return coldingStartCount;
    }

    public void setColdingStartCount(int coldingStartCount) {
        this.coldingStartCount = coldingStartCount;
    }

    public int getNightDrivingCount() {
        return nightDrivingCount;
    }

    public void setNightDrivingCount(int nightDrivingCount) {
        this.nightDrivingCount = nightDrivingCount;
    }

    public int getFullAcceleratorCount() {
        return fullAcceleratorCount;
    }

    public void setFullAcceleratorCount(int fullAcceleratorCount) {
        this.fullAcceleratorCount = fullAcceleratorCount;
    }

    public int getLargeAcceleratorCount() {
        return largeAcceleratorCount;
    }

    public void setLargeAcceleratorCount(int largeAcceleratorCount) {
        this.largeAcceleratorCount = largeAcceleratorCount;
    }

    public int getFastSpeedCount() {
        return fastSpeedCount;
    }

    public void setFastSpeedCount(int fastSpeedCount) {
        this.fastSpeedCount = fastSpeedCount;
    }

    public int getIdlingAirConditionCount() {
        return idlingAirConditionCount;
    }

    public void setIdlingAirConditionCount(int idlingAirConditionCount) {
        this.idlingAirConditionCount = idlingAirConditionCount;
    }

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

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public void setBdTerCode(String bdTerCode) {
        this.bdTerCode = bdTerCode;
    }

    public void setFkTerCode(String fkTerCode) {
        this.fkTerCode = fkTerCode;
    }

    public String getBdTerCode() {
        return bdTerCode;
    }

    public String getFkTerCode() {
        return fkTerCode;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    public String getBadDrivingDate() {
        return badDrivingDate;
    }

    public void setBadDrivingDate(String badDrivingDate) {
        this.badDrivingDate = badDrivingDate;
    }

    public String getBadDrivingDuration() {
        return badDrivingDuration;
    }

    public void setBadDrivingDuration(String badDrivingDuration) {
        this.badDrivingDuration = badDrivingDuration;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
    }

    @Override
    public String toString() {
        return "BadDrivingAnalysisDto{" +
                "carId=" + carId +
                ", chassisNum='" + chassisNum + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", bdTerCode='" + bdTerCode + '\'' +
                ", fkTerCode='" + fkTerCode + '\'' +
                ", tName='" + tName + '\'' +
                ", businessName='" + businessName + '\'' +
                ", carModel='" + carModel + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", engineType='" + engineType + '\'' +
                ", structureNum='" + structureNum + '\'' +
                ", badDrivingDate='" + badDrivingDate + '\'' +
                ", badDrivingDuration='" + badDrivingDuration + '\'' +
                ", communicationId=" + communicationId +
                ", vin='" + vin + '\'' +
                ", accelerateCount=" + accelerateCount +
                ", decelerateCount=" + decelerateCount +
                ", sharpTurnCount=" + sharpTurnCount +
                ", brakeMileCount=" + brakeMileCount +
                ", longIdlingCount=" + longIdlingCount +
                ", highGearStartCount=" + highGearStartCount +
                ", lowGearHighSpeedCount=" + lowGearHighSpeedCount +
                ", neutralCoastingCount=" + neutralCoastingCount +
                ", stallCoastingCount=" + stallCoastingCount +
                ", coldingStartCount=" + coldingStartCount +
                ", nightDrivingCount=" + nightDrivingCount +
                ", fullAcceleratorCount=" + fullAcceleratorCount +
                ", largeAcceleratorCount=" + largeAcceleratorCount +
                ", fastSpeedCount=" + fastSpeedCount +
                ", idlingAirConditionCount=" + idlingAirConditionCount +
                '}';
    }
}