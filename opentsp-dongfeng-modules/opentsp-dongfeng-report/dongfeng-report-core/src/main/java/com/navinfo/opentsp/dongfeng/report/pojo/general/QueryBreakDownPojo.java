package com.navinfo.opentsp.dongfeng.report.pojo.general;

import java.math.BigInteger;

/**
 * QueryFaultSummaryPojo
 *
 * @Author
 * @Date 2017/3/24
 */
public class QueryBreakDownPojo implements Cloneable {

    // 车辆主键
    private BigInteger id;
    // 底盘号
    private String chassisNum;
    // 车牌号
    private String plateNum;
    // 北斗一体机ID
    private String bdTerCode;
    // FK控制器ID
    private String fkTerCode;
    // 所属经销商
    private String tName;
    // 所属客户
    private String businessName;
    // 车辆型号
    private String carModel;
    // 发动机型号
    private String engineNumber;

    private String eType;
    // 发动机类型
    private String engineType;
    // 车架号
    private String structureNum;
    // 终端通信号
    private BigInteger commId;
    // 故障类型
    private String breakdownDis;
    // SPN
    private String spn;
    // FMI
    private String fmi;
    // 故障发生时间
    private String occurDate;
    // 持续时长
    private String duration;

    //维修方案
    private String maintenanceSchedule;

    //维修成本
    private String maintenanceCost;

    protected String spnFmi;// 用于拆分成下面两个b

    protected Double bLog;

    protected Double bLat;

    protected String bLoction;// 起始位置

    public Double getbLog() {
        return bLog;
    }

    public Double getbLat() {
        return bLat;
    }

    public void setbLog(Double bLog) {
        this.bLog = bLog;
    }

    public void setbLat(Double bLat) {
        this.bLat = bLat;
    }

    public String getSpnFmi() {
        return spnFmi;
    }

    public void setSpnFmi(String spnFmi) {
        this.spnFmi = spnFmi;
    }

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

    public String getBdTerCode() {
        return bdTerCode;
    }

    public void setBdTerCode(String bdTerCode) {
        this.bdTerCode = bdTerCode;
    }

    public String getFkTerCode() {
        return fkTerCode;
    }

    public void setFkTerCode(String fkTerCode) {
        this.fkTerCode = fkTerCode;
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

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
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

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public String getBreakdownDis() {
        return breakdownDis;
    }

    public void setBreakdownDis(String breakdownDis) {
        this.breakdownDis = breakdownDis;
    }

    public String getSpn() {
        return spn;
    }

    public void setSpn(String spn) {
        this.spn = spn;
    }

    public String getFmi() {
        return fmi;
    }

    public void setFmi(String fmi) {
        this.fmi = fmi;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getbLoction() {
        return bLoction;
    }

    public void setbLoction(String bLoction) {
        this.bLoction = bLoction;
    }

    public String getMaintenanceSchedule() {
        return maintenanceSchedule;
    }

    public void setMaintenanceSchedule(String maintenanceSchedule) {
        this.maintenanceSchedule = maintenanceSchedule;
    }

    public String getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(String maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
