package com.navinfo.opentsp.dongfeng.openapi.core.pojo;

/**
 * 
 * @author xltianc.zh
 *
 */
public class F9ReportInfoPojo {

	private String receiveDate;// 接收时间
	private String gpsDate;// GPS时间
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String height;// 高程
	private String direction;// 方向
	private Integer speed;// 速度
	private String accStatus;// 车辆状态
	private String canmileage;// 整车里程
	private String mileage;// 终端里程
	private String oilValue;// 剩余油量
	private String vehicleBreakdown;// 故障
	private String tripDistance;// 小计里程
	private String rotation;// 发动机转速
	private String cumulativeTurningNumber;// 发动机累计转数
	private String cumulativeRunningTime;// 发动机累计运行时长
	private String coolingWaterTem;// 冷却液温度
	private String totalFuelConsumption;// 油量总消耗
	private String fuelConsumptionRate;// 发动机燃油消耗率
	private String averageFuelConsumption;// 平均燃油消耗率
	private String engineInstantaneousFuelRate;// 瞬时油耗率
	private String engineTorMode;// 转矩控制模式
	private String driverEnginePercentTor;// 驾驶指令百分比扭矩
	private String actualEnginePercentTor;// 发动机实际扭矩百分比
	private String accPedalLowIdleSwitch;// 加速踏板低怠速开关
	private String accPedalKickdownSwitch;// 加速踏板Kickdown开关
	private String accPedalPos;// 加速踏板开度
	private String percentLoadAtCurrentSpd;// 当前速度负载百分比
	private String nominalFrictionPercentTrq;// 名义摩擦力矩百分比
	private String parkingBrakeSwitch;// 驻车制动器开关
	private String wheelBasedVehicleSpd;// 车轮车速
	private String cruiseCtrlActive;// 巡航控制开关状态
	private String brakeSwitch;// 制动开关
	private String clutchSwitch;// 离合器开关
	private String cruiseCtrlSetSwitch;// 巡航控制设置开关
	private String cruiseCtrlCoastSwitch;// 巡航控制减速开关
	private String cruiseCtrlResumeSwitch;// 巡航控制恢复开关
	private String cruiseCtrlAccSwitch;// 巡航控制加速开关
	private String cruiseCtrlSetSpd;// 巡航控制设置速度
	private String cruiseCtrlStates;// 巡航控制状态
	private String outPutRoateSpeed;// 转出轴转速

	public String getReceiveDate() {
		return receiveDate;
	}

	public String getGpsDate() {
		return gpsDate;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public String getHeight() {
		return height;
	}

	public String getDirection() {
		return direction;
	}

	public Integer getSpeed() {
		return speed;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public String getCanmileage() {
		return canmileage;
	}

	public String getMileage() {
		return mileage;
	}

	public String getOilValue() {
		return oilValue;
	}

	public String getVehicleBreakdown() {
		return vehicleBreakdown;
	}

	public String getTripDistance() {
		return tripDistance;
	}

	public String getRotation() {
		return rotation;
	}

	public String getCumulativeTurningNumber() {
		return cumulativeTurningNumber;
	}

	public String getCumulativeRunningTime() {
		return cumulativeRunningTime;
	}

	public String getCoolingWaterTem() {
		return coolingWaterTem;
	}

	public String getTotalFuelConsumption() {
		return totalFuelConsumption;
	}

	public String getFuelConsumptionRate() {
		return fuelConsumptionRate;
	}

	public String getAverageFuelConsumption() {
		return averageFuelConsumption;
	}

	public String getEngineInstantaneousFuelRate() {
		return engineInstantaneousFuelRate;
	}

	public String getEngineTorMode() {
		return engineTorMode;
	}

	public String getDriverEnginePercentTor() {
		return driverEnginePercentTor;
	}

	public String getActualEnginePercentTor() {
		return actualEnginePercentTor;
	}

	public String getAccPedalLowIdleSwitch() {
		return accPedalLowIdleSwitch;
	}

	public String getAccPedalKickdownSwitch() {
		return accPedalKickdownSwitch;
	}

	public String getAccPedalPos() {
		return accPedalPos;
	}

	public String getPercentLoadAtCurrentSpd() {
		return percentLoadAtCurrentSpd;
	}

	public String getNominalFrictionPercentTrq() {
		return nominalFrictionPercentTrq;
	}

	public String getParkingBrakeSwitch() {
		return parkingBrakeSwitch;
	}

	public String getWheelBasedVehicleSpd() {
		return wheelBasedVehicleSpd;
	}

	public String getCruiseCtrlActive() {
		return cruiseCtrlActive;
	}

	public String getBrakeSwitch() {
		return brakeSwitch;
	}

	public String getClutchSwitch() {
		return clutchSwitch;
	}

	public String getCruiseCtrlSetSwitch() {
		return cruiseCtrlSetSwitch;
	}

	public String getCruiseCtrlCoastSwitch() {
		return cruiseCtrlCoastSwitch;
	}

	public String getCruiseCtrlResumeSwitch() {
		return cruiseCtrlResumeSwitch;
	}

	public String getCruiseCtrlAccSwitch() {
		return cruiseCtrlAccSwitch;
	}

	public String getCruiseCtrlSetSpd() {
		return cruiseCtrlSetSpd;
	}

	public String getCruiseCtrlStates() {
		return cruiseCtrlStates;
	}

	public String getOutPutRoateSpeed() {
		return outPutRoateSpeed;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setGpsDate(String gpsDate) {
		this.gpsDate = gpsDate;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public void setCanmileage(String canmileage) {
		this.canmileage = canmileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public void setOilValue(String oilValue) {
		this.oilValue = oilValue;
	}

	public void setVehicleBreakdown(String vehicleBreakdown) {
		this.vehicleBreakdown = vehicleBreakdown;
	}

	public void setTripDistance(String tripDistance) {
		this.tripDistance = tripDistance;
	}

	public void setRotation(String rotation) {
		this.rotation = rotation;
	}

	public void setCumulativeTurningNumber(String cumulativeTurningNumber) {
		this.cumulativeTurningNumber = cumulativeTurningNumber;
	}

	public void setCumulativeRunningTime(String cumulativeRunningTime) {
		this.cumulativeRunningTime = cumulativeRunningTime;
	}

	public void setCoolingWaterTem(String coolingWaterTem) {
		this.coolingWaterTem = coolingWaterTem;
	}

	public void setTotalFuelConsumption(String totalFuelConsumption) {
		this.totalFuelConsumption = totalFuelConsumption;
	}

	public void setFuelConsumptionRate(String fuelConsumptionRate) {
		this.fuelConsumptionRate = fuelConsumptionRate;
	}

	public void setAverageFuelConsumption(String averageFuelConsumption) {
		this.averageFuelConsumption = averageFuelConsumption;
	}

	public void setEngineInstantaneousFuelRate(String engineInstantaneousFuelRate) {
		this.engineInstantaneousFuelRate = engineInstantaneousFuelRate;
	}

	public void setEngineTorMode(String engineTorMode) {
		this.engineTorMode = engineTorMode;
	}

	public void setDriverEnginePercentTor(String driverEnginePercentTor) {
		this.driverEnginePercentTor = driverEnginePercentTor;
	}

	public void setActualEnginePercentTor(String actualEnginePercentTor) {
		this.actualEnginePercentTor = actualEnginePercentTor;
	}

	public void setAccPedalLowIdleSwitch(String accPedalLowIdleSwitch) {
		this.accPedalLowIdleSwitch = accPedalLowIdleSwitch;
	}

	public void setAccPedalKickdownSwitch(String accPedalKickdownSwitch) {
		this.accPedalKickdownSwitch = accPedalKickdownSwitch;
	}

	public void setAccPedalPos(String accPedalPos) {
		this.accPedalPos = accPedalPos;
	}

	public void setPercentLoadAtCurrentSpd(String percentLoadAtCurrentSpd) {
		this.percentLoadAtCurrentSpd = percentLoadAtCurrentSpd;
	}

	public void setNominalFrictionPercentTrq(String nominalFrictionPercentTrq) {
		this.nominalFrictionPercentTrq = nominalFrictionPercentTrq;
	}

	public void setParkingBrakeSwitch(String parkingBrakeSwitch) {
		this.parkingBrakeSwitch = parkingBrakeSwitch;
	}

	public void setWheelBasedVehicleSpd(String wheelBasedVehicleSpd) {
		this.wheelBasedVehicleSpd = wheelBasedVehicleSpd;
	}

	public void setCruiseCtrlActive(String cruiseCtrlActive) {
		this.cruiseCtrlActive = cruiseCtrlActive;
	}

	public void setBrakeSwitch(String brakeSwitch) {
		this.brakeSwitch = brakeSwitch;
	}

	public void setClutchSwitch(String clutchSwitch) {
		this.clutchSwitch = clutchSwitch;
	}

	public void setCruiseCtrlSetSwitch(String cruiseCtrlSetSwitch) {
		this.cruiseCtrlSetSwitch = cruiseCtrlSetSwitch;
	}

	public void setCruiseCtrlCoastSwitch(String cruiseCtrlCoastSwitch) {
		this.cruiseCtrlCoastSwitch = cruiseCtrlCoastSwitch;
	}

	public void setCruiseCtrlResumeSwitch(String cruiseCtrlResumeSwitch) {
		this.cruiseCtrlResumeSwitch = cruiseCtrlResumeSwitch;
	}

	public void setCruiseCtrlAccSwitch(String cruiseCtrlAccSwitch) {
		this.cruiseCtrlAccSwitch = cruiseCtrlAccSwitch;
	}

	public void setCruiseCtrlSetSpd(String cruiseCtrlSetSpd) {
		this.cruiseCtrlSetSpd = cruiseCtrlSetSpd;
	}

	public void setCruiseCtrlStates(String cruiseCtrlStates) {
		this.cruiseCtrlStates = cruiseCtrlStates;
	}

	public void setOutPutRoateSpeed(String outPutRoateSpeed) {
		this.outPutRoateSpeed = outPutRoateSpeed;
	}

}
