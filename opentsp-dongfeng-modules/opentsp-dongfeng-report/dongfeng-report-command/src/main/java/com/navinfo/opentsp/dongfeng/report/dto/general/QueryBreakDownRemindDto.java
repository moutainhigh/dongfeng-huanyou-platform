package com.navinfo.opentsp.dongfeng.report.dto.general;

public class QueryBreakDownRemindDto {

	private Long carId; // 车辆ID---own
	protected String chassisNum;// 底盘号c
	protected String carCph;// 车牌号c
	protected String terId; // 终端IDr
	protected String carTerId; // 防拆盒IDr
	protected String companyName;// 所属经销商t
	protected String carOwner; // 所属客户（企业，车主）d
	protected String carType; // 车辆型号c
	protected Integer engineType; // 发动机类型
	protected String engineTypeStr; // 发动机类型
	protected String engineNumber; // 发动机型号d

	protected String spnFmi;// 用于拆分成下面两个b
	protected String occurDate;// 故障发生时间
	protected String bLoction;// 起始位置 //需要改显示形式
	protected Double bLog;
	protected Double bLat;
	protected String breakdownDis;//// 故障描述

	//维修方案
	protected String maintenanceSchedule;

	//维修成本
	protected String maintenanceCost;

	public Long getCarId() {
		return carId;
	}

	public String getChassisNum() {
		return chassisNum;
	}

	public String getCarCph() {
		return carCph;
	}

	public String getTerId() {
		return terId;
	}

	public String getCarTerId() {
		return carTerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public String getCarType() {
		return carType;
	}

	public Integer getEngineType() {
		return engineType;
	}

	public String getEngineTypeStr() {
		return engineTypeStr;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public String getSpnFmi() {
		return spnFmi;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public String getbLoction() {
		return bLoction;
	}

	public Double getbLog() {
		return bLog;
	}

	public Double getbLat() {
		return bLat;
	}

	public String getBreakdownDis() {
		return breakdownDis;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public void setChassisNum(String chassisNum) {
		this.chassisNum = chassisNum;
	}

	public void setCarCph(String carCph) {
		this.carCph = carCph;
	}

	public void setTerId(String terId) {
		this.terId = terId;
	}

	public void setCarTerId(String carTerId) {
		this.carTerId = carTerId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}

	public void setEngineTypeStr(String engineTypeStr) {
		this.engineTypeStr = engineTypeStr;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public void setSpnFmi(String spnFmi) {
		this.spnFmi = spnFmi;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public void setbLoction(String bLoction) {
		this.bLoction = bLoction;
	}

	public void setbLog(Double bLog) {
		this.bLog = bLog;
	}

	public void setbLat(Double bLat) {
		this.bLat = bLat;
	}

	public void setBreakdownDis(String breakdownDis) {
		this.breakdownDis = breakdownDis;
	}

	public String getMaintenanceCost() {
		return maintenanceCost;
	}

	public void setMaintenanceCost(String maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}

	public String getMaintenanceSchedule() {
		return maintenanceSchedule;
	}

	public void setMaintenanceSchedule(String maintenanceSchedule) {
		this.maintenanceSchedule = maintenanceSchedule;
	}
}
