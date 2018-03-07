package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 远程诊断对象
 *
 * @wenya
 * @create 2017-03-11 16:25
 **/
public class QueryRemoteInfoPojo {
    private int index;//当前序列号  用于上一条，下一条
    private int total;//用于总页数
    //车辆基本内容
    private BigInteger carId;//车辆主键
    private String chassisNum;//底盘号
    private String engineType;//发动机类型
    private BigInteger commId;//通讯号
    private String oilCapacity;//邮箱容量
    /*仪表盘*/
    private String nearLight;//近光灯信号
    private String farLight;//远光灯信号
    private String fogLight;//雾灯
    private String airConditioning;//空调状态
    private String neutral;//空挡信号
    private String leftTurningLight;//左转向灯
    private String rightTurningLight;//右转向灯信号
    private String retarder;//缓速器工作
    private String absWorking;//ABS工作
    private String heaterWorking;//加热器工作
    private String clutchStatus;//离合器状态
    private String speaker;//喇叭信号
    private String markerLight;//示廓灯（7位）
    private String braking;//制动 P
    private String reverse;//倒挡 R
    //冷启动灯和油中有水 在StatuAddition中
    private String lampStatus;//1正常,0异常
    private String waterInOilStatus;//油中有水 1正常,0异常
    private String oilValue;//车辆当前真实油量
    private String speed;//终端速度：45KM/h
    private String rotation;//车辆当前转速
    private String coolingWaterTem;//发动机冷却水温度
    /*行驶统计车况数据*/
    private String realTimeOilConsumption;//瞬时油耗
    private String dailyMileage;//当日里程 小计里程
    private String dayFuelConsumption;//每天燃油消耗(升)  --- 小计里程总燃油消耗量
    private String fuelConsumptionRate;//发动机燃油消耗率（毫升/小时）
    private String mileage;//整车里程
    private String totalOil;//总油耗
    private String averageFuelConsumption;//平均燃油消耗率（千米/升）
    private String cumulativeTurningNumber;//发动机累计转数（单位:1000rpm）
    private String cumulativeRunningTimeStr;//发动机累计运行时间（秒）
    private String tripDistanceDD;//仪表的小计里程
    private String mileageDD;//仪表总里程
    private String lynCylinder1TotalCapacity;  // LNG气瓶1 总容量
    private String lynCylinder1ResidualCapacity; // LNG 气瓶1 剩余容量
    private String lynCylinder2TotalCapacity;  // LNG气瓶2 总容量
    private String lynCylinder2ResidualCapacity; // LNG 气瓶2 剩余容量
    private String lynCylinder3TotalCapacity;  // LNG气瓶3 总容量
    private String lynCylinder3ResidualCapacity; // LNG 气瓶3 剩余容量
    private String lynCylinder4TotalCapacity;  // LNG气瓶4 总容量
    private String lynCylinder4ResidualCapacity; // LNG 气瓶4 剩余容量
    private String carLoad; //整车载重

    // 发动机配置车况数据  engineConfiguration
    private String fuelPressure ;//Fuel Deliver y Pressure/燃油压力
    private String engineInterTemper ;//Extended Crankcase Blow-by Pressure
    private String oilLevel ;//Engine Oil Level/机油液位
    private String oilPressure ;//Engine Oil Pressure/机油压力
    private String coolantLevel;//Engine Oil Pressure/冷却液液位
    //Engine Coolant Temperature/发动机冷却水温度 同private double coolingWaterTem
    private String fuelTem ;//Fuel Temperature/燃油温度
    private String oilTem ;//Engine Oil Temperature/机油温度
    private String truboOilTem ;//Turbo Oil Temperature/增压器机油温度
    private String p2HighestSpeed;//Engine Intercooler Temperature
    private String ureaTankLiquidLevel ;//Turbo Oil Temperature/尿素箱液位
    private String ureaTankTem ;//Turbo Oil Temperature/尿素箱温度
    private String ureaConcentration; // 尿素浓度
    private String batteryPotInput1 ;//Battery Potential/Power Input 1
    private String batteryPot ;//	39	Battery Potential（SPN 158）
    private String engineProtection ;//	3A	Engine protection system has
    private String p1Speed ;	//	3B	Point 1-engine speed at idle/怠速转速
    private String p1Torque ;//	3C	Point 1-percent torque at idle/怠速扭矩
    private String p2PercentTorSpeed ;//Point 2-percent torque at highest speed
    private String p3Speed ; //	3D	Point 3-engine speed
    private String p3Torque ;//	3E	Point 3-percent torque
    private String p4Speed ;  //	3F	Point 4-engine speed
    private String p4Troque ;//	40	Point 4-percent torque
    private String p5Speed ;//	41	Point 5-engine speed
    private String p5Torque ;//	42	Point 5-percent torque
    private String p6Speed ;//	43	Point 6-engine speed
    private String gainSpeed ;//	44	Byte of gain of end-speed governor
    private String refEngineTorque ;//	45	Reference engine torque/参考扭矩
    private String maxSpeedLimit ;//	46	Point 7-Byte of max momentary engine override speed limit
    private String maxTimeLimit ;//	47	Maximum momentary engine override time limit
    private String spUpperLimit ;//	49	Engine Requested speed control Range Upper limit
    private String torLowerLimit;	//	4A	Engine Requested Torque control Range Lower limit
    private String momentIner ;//	4D	Engine Moment of Inertia
    private String relEngOilPressure ;//	4E	Relative Engine oil pressure
    private String engOilPreLow ;//	4F	Engine Oil Pressure Low
    private String engCoolTem ;//	50	Engine Over Coolant Temperature
    private String startHeartStat ;	//	51	Cold Start Heater Status
    private String obDLampStatus;//	52	OBD Lamp Status
    private String exOutput ;//	53	Exhaust flap valve output
    private String esFanSp ;//	54	Estimated percent Fan speed
    private String fanDriState ;	//	55	Fan Drive state
    private String fanSpeed ;//	Fan speed
    //进气系统车况数据 airIntakeSystem
    private String dpfPressure ; //Particulate trap inlet pressure/颗粒捕集器进气压力
    private String relativePressure ;//Boost pressure-atmosphere pressure/相对增压压力
    private String absolutePressure;//Air inlet pressure (absolute boost pressure)/绝对增压压力
    private String airFilterPre;//	57	Air filter 1 differential pressure/空滤器压差
    private String exhaustTem ;//Exhaust gas temperature/排气温度
    private String coolDifferTem ;//	58	Coolant filter differential temperature/冷却器温差
    private String atmosphericPressure ;//Barometric pressure/大气压力
    private String engineTem ;//Cab interior temperatur/发动机舱内部温度
    private String atmosphericTem ;//Ambient air temperature/大气温度
    private String airInTem;//	59	Air inlet temperature/进气温度
    private String pavementTem;//Road surface temperature/路面温度
    /*发动机控制器EEC engineController*/
    private String perTorResolution;//	5A	Actual engine - percent torque high resolution
    private String engineTorMode ;//Engine Torque Mode发动机扭矩模式 0 低怠速调节器/无请求（默认模式）1 加速踏板2 巡航控制3 PTO调节器4 车速调节器5 ASR控制6 变速器控制7 ABS控制 8 转矩限定9 高速调节器10 制动系统11 遥控加速器
    private String driverEnginePercentTor ;//Driver's Demand Engine - Percent Torque 驾驶指令百分比扭矩
    private String actualEnginePercentTor;//Actual Engine - Percent Torque
    //Engine Speed 发动机转速 同 rotation（车辆当前转速）
    private String addControlDevice ;//	5B	Source Address of Controlling Device for Engine Control/控制发动机设备源地址
    private String engineStartMode ;//	5C	Engine Starter Mode/起动机模式
    private String engineDePerTor ;//	5D	Engine Demand - Percent Torque/发动机需求扭矩百分比
    private String speedLimitStatus ;//	5E	Road speed limit status/速度限制状态
    private String accPedalKickdownSwitch ;//Accelerator pedal kick-down switch 踏板开关 0 被动 1 主动
    private String accPedalLowIdleSwitch ;//Accelerator pedal low idle switch 加速踏板低怠速开关
    private String accPedalPos ;//Accelerator pedal position 踏板位置
    private String percentLoadAtCurrentSpd ;//percent load at current speed 当前转速下负荷百分比
    private String reAccPosition;//	5F	Remote accelerator position
    private String reAccPosition2 ;//	60	Remote accelerator position2
    private String maxAvailableEngPerTor;//	61	Actual maximum available engine percentage torque
    private String nominalFrictionPercentTrq ;//nominal friction percent torque 摩擦扭矩百分比
    private String desirOperaSpeed ;//	62	engine's desired operating speed/发动机目标运行速度
    private String engAsyAdjust ;//	63	engine's operating speed asymmetry adjustment/发动机平稳调整
    private String estimaParLoss ;//	64	Estimated Engine Parasitic Losses
    private String exhGasMassflow ;//	65	Exhaust gas mass flow
    private String af1Intake ;//	66	Aftertreatment 1 Intake Dew Point
    private String af1Exhaust ;//	67	Aftertreatment 1 Exhaust Dew Point
    private String af2Intake;//	68	Aftertreatment 2 Intake Dew Point
    private String af2Exhaust;//	69	Aftertreatment 2 Exhaust Dew Point
    /*巡航控制CCVS cruiseControl*/
    private String cruPauseSwitch;//	6A	Cruise control Pause Switch
    private String brakeSwitch ;//Status of parking brake 刹车状态
    private String wheelBasedVehicleSpd ;//Wheel based car Speed 车速
    private String clutchSwitch ;//Status of clutch switch 离合开关状态 0 未分离  1 分离
    private String parkingBrakeSwitch ;//Status of brake switch 刹车开关状态 0 未操作  1 操作
    private String cruiControlEnable ;//	6B	Cruise control enable/巡航控制使能
    private String cruiseControlActive;//Cruise control active 巡航控制激活状态标识
    private String cruiseCtrlAccSwitch ;//Cruise Control Accelerate Switch  0 未按下  1 按下
    private String cruiseCtrlResumeSwitch ;//Cruise Control Resume Switch 0 未按下 1 按下
    private String cruiseCtrlCoastSwitch ;//Cruise Control Coast(Decelerate)  0 未按下 1 按下
    private String cruiseCtrlSetSwitch;//Cruise Control Set Switch  0 无按钮按下 1 退出巡
    private String cruiseCtrlSetSpd ;//Cruise control set speed
    private String cruiseCtrlStates ;//Cruise control state  0 关闭/禁止 1 保持 2 加速
    private String ptoState ;//	6C	PTO state/PTO 状态
    private String engShutOverSwitch ;//	6D	Engine shut down override switch : 	00:Off 01:On
    private String engIdleDecSwitch ;//	6F	Engine Idle decrement Switch.同上
    private String engIdleIncSwitch ;//	70	Engine Idle increment switch
    private String outPutRoateSpeed ;//输出轴转速
    private String tachographVehicleSpeed ;//车速
    /*电控缓速器DT_Engine*/
    private String retarTorMode ;//	71	Retarder Torque Mode/缓速器扭矩模式
    private String retBrakeAssSwitch;//	72	Retarder Enable - Brake Assist Switch/刹车辅助开关
    private String retShiftAssSwitch ;//	73	Retarder Enable - Shift Assist Switch/换挡辅助开关
    private String actRetTorPer;//	74	Actual Retarder Torque Percentage//缓速器实际扭矩百分比
    private String intRetPerTor ;//	75	Intended Retarder percent Torque/缓速器需求扭矩
    private String coolLoadIncrease ;//	76	Coolant Load Increase
    private String retReqBrakeLight ;//	77	Retarder Requesting Brake Light/缓速器需求灯
    private String addOfControDevice ;//	78	Source Address of controlling device for retarder control/缓速器控制单元地址
    private String demRetPerTor;//	79	Driver’s Demand Retarder Percent Torque/缓速器需求扭矩百分比
    private String retSwitchPerTor ;//	7A	Retarder Switch Percent Torque/缓速器开关扭矩百分比
    private String actMaxAvailableRetPerTor;//	7B	Actual Maximum Available retarder Percent Torque/实际最大缓速器扭矩百分比
    private String retarderLocation ;//	7C	Retarder Location/缓速器位置
    private String retarderType ;//	7D	Retarder Type/缓速器类型
    private String retarderConMethod ;//	7E	Retarder Control Method/缓速器控制方式
    private String retarderSpIdlePoint1 ;//	7F	Retarder Speed at Idle - Point 1/缓速器低怠速
    private String retarderPerTorIdle ;//	80	Retarder Percent Torque Idle/缓速器怠速扭矩百分比
    private String maxRetSpeedPoint2;//	81	Maximum Retarder Speed Point 2/最大速度2
    private String retPerTorMaxSpeedPoint2 ;//	82	Retarder Percent Torque at Maximum Speed, Point 2/转速2缓速器扭矩百分比
    private String engSpeedPoint3 ;//	83	Engine Speed Point 3/转速3
    private String retarderPerTorPoint3 ;//	84	Retarder Percent Torque - Point 3/转速3扭矩百分比
    private String engSpeedPoint4 ;//	85	Engine Speed Point 4/转速4
    private String retarderPerTorPoint4 ;//	86	Retarder Percent Torque, Point 4/转速4扭矩百分比
    private String retSpeedTorPoint5 ;//	87	Retarder Speed at peak torque Point 5/最大扭矩转速5
    private String refRetarTorque ;//	88	Reference Retarder Torque/缓速器参考扭矩
    private String retarderPerTorPoint5 ;//	89	Retarder Percent Torque - Point 5/转速5扭矩百分比
    //其他车况信息
    private String cruiseCtrlActive;//巡航控制开关状态：0 未激活 1 激活
    private String breakdown;//故障码
    private String gpsMileage;//GPS总里程
    //天然气or柴油车：0表示燃气，1表示燃油
    private Integer fuel;
    //积分油耗
    private String integralFuelConsumption;
    private String gpstime;//GPS时间
    public BigInteger getCarId() {
        return carId;
    }

    public String getMileageDD() {
        return mileageDD;
    }

    public void setMileageDD(String mileageDD) {
        this.mileageDD = mileageDD;
    }

    public String getTripDistanceDD() {
        return tripDistanceDD;
    }

    public void setTripDistanceDD(String tripDistanceDD) {
        this.tripDistanceDD = tripDistanceDD;
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

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
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

    public String getNearLight() {
        return nearLight;
    }

    public void setNearLight(String nearLight) {
        this.nearLight = nearLight;
    }

    public String getFarLight() {
        return farLight;
    }

    public void setFarLight(String farLight) {
        this.farLight = farLight;
    }

    public String getFogLight() {
        return fogLight;
    }

    public void setFogLight(String fogLight) {
        this.fogLight = fogLight;
    }

    public String getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(String airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getNeutral() {
        return neutral;
    }

    public void setNeutral(String neutral) {
        this.neutral = neutral;
    }

    public String getLeftTurningLight() {
        return leftTurningLight;
    }

    public void setLeftTurningLight(String leftTurningLight) {
        this.leftTurningLight = leftTurningLight;
    }

    public String getRightTurningLight() {
        return rightTurningLight;
    }

    public void setRightTurningLight(String rightTurningLight) {
        this.rightTurningLight = rightTurningLight;
    }

    public String getRetarder() {
        return retarder;
    }

    public void setRetarder(String retarder) {
        this.retarder = retarder;
    }

    public String getAbsWorking() {
        return absWorking;
    }

    public void setAbsWorking(String absWorking) {
        this.absWorking = absWorking;
    }

    public String getHeaterWorking() {
        return heaterWorking;
    }

    public void setHeaterWorking(String heaterWorking) {
        this.heaterWorking = heaterWorking;
    }

    public String getClutchStatus() {
        return clutchStatus;
    }

    public void setClutchStatus(String clutchStatus) {
        this.clutchStatus = clutchStatus;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getMarkerLight() {
        return markerLight;
    }

    public void setMarkerLight(String markerLight) {
        this.markerLight = markerLight;
    }

    public String getLampStatus() {
        return lampStatus;
    }

    public void setLampStatus(String lampStatus) {
        this.lampStatus = lampStatus;
    }

    public String getWaterInOilStatus() {
        return waterInOilStatus;
    }

    public void setWaterInOilStatus(String waterInOilStatus) {
        this.waterInOilStatus = waterInOilStatus;
    }

    public String getBraking() {
        return braking;
    }

    public void setBraking(String braking) {
        this.braking = braking;
    }

    public String getReverse() {
        return reverse;
    }

    public void setReverse(String reverse) {
        this.reverse = reverse;
    }

    public String getOilValue() {
        return oilValue;
    }

    public void setOilValue(String oilValue) {
        this.oilValue = oilValue;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getCoolingWaterTem() {
        return coolingWaterTem;
    }

    public void setCoolingWaterTem(String coolingWaterTem) {
        this.coolingWaterTem = coolingWaterTem;
    }

    public String getRealTimeOilConsumption() {
        return realTimeOilConsumption;
    }

    public void setRealTimeOilConsumption(String realTimeOilConsumption) {
        this.realTimeOilConsumption = realTimeOilConsumption;
    }

    public String getDailyMileage() {
        return dailyMileage;
    }

    public void setDailyMileage(String dailyMileage) {
        this.dailyMileage = dailyMileage;
    }

    public String getDayFuelConsumption() {
        return dayFuelConsumption;
    }

    public void setDayFuelConsumption(String dayFuelConsumption) {
        this.dayFuelConsumption = dayFuelConsumption;
    }

    public String getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(String fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getTotalOil() {
        return totalOil;
    }

    public void setTotalOil(String totalOil) {
        this.totalOil = totalOil;
    }

    public String getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(String averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public String getCumulativeTurningNumber() {
        return cumulativeTurningNumber;
    }

    public void setCumulativeTurningNumber(String cumulativeTurningNumber) {
        this.cumulativeTurningNumber = cumulativeTurningNumber;
    }

    public String getCumulativeRunningTimeStr() {
        return cumulativeRunningTimeStr;
    }

    public void setCumulativeRunningTimeStr(String cumulativeRunningTimeStr) {
        this.cumulativeRunningTimeStr = cumulativeRunningTimeStr;
    }

    public String getFuelPressure() {
        return fuelPressure;
    }

    public void setFuelPressure(String fuelPressure) {
        this.fuelPressure = fuelPressure;
    }

    public String getEngineInterTemper() {
        return engineInterTemper;
    }

    public void setEngineInterTemper(String engineInterTemper) {
        this.engineInterTemper = engineInterTemper;
    }

    public String getOilLevel() {
        return oilLevel;
    }

    public void setOilLevel(String oilLevel) {
        this.oilLevel = oilLevel;
    }

    public String getOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(String oilPressure) {
        this.oilPressure = oilPressure;
    }

    public String getCoolantLevel() {
        return coolantLevel;
    }

    public void setCoolantLevel(String coolantLevel) {
        this.coolantLevel = coolantLevel;
    }

    public String getFuelTem() {
        return fuelTem;
    }

    public void setFuelTem(String fuelTem) {
        this.fuelTem = fuelTem;
    }

    public String getOilTem() {
        return oilTem;
    }

    public void setOilTem(String oilTem) {
        this.oilTem = oilTem;
    }

    public String getTruboOilTem() {
        return truboOilTem;
    }

    public void setTruboOilTem(String truboOilTem) {
        this.truboOilTem = truboOilTem;
    }

    public String getP2HighestSpeed() {
        return p2HighestSpeed;
    }

    public void setP2HighestSpeed(String p2HighestSpeed) {
        this.p2HighestSpeed = p2HighestSpeed;
    }

    public String getUreaTankLiquidLevel() {
        return ureaTankLiquidLevel;
    }

    public void setUreaTankLiquidLevel(String ureaTankLiquidLevel) {
        this.ureaTankLiquidLevel = ureaTankLiquidLevel;
    }

    public String getUreaTankTem() {
        return ureaTankTem;
    }

    public void setUreaTankTem(String ureaTankTem) {
        this.ureaTankTem = ureaTankTem;
    }

    public String getBatteryPotInput1() {
        return batteryPotInput1;
    }

    public void setBatteryPotInput1(String batteryPotInput1) {
        this.batteryPotInput1 = batteryPotInput1;
    }

    public String getBatteryPot() {
        return batteryPot;
    }

    public void setBatteryPot(String batteryPot) {
        this.batteryPot = batteryPot;
    }

    public String getEngineProtection() {
        return engineProtection;
    }

    public void setEngineProtection(String engineProtection) {
        this.engineProtection = engineProtection;
    }

    public String getP1Speed() {
        return p1Speed;
    }

    public void setP1Speed(String p1Speed) {
        this.p1Speed = p1Speed;
    }

    public String getP1Torque() {
        return p1Torque;
    }

    public void setP1Torque(String p1Torque) {
        this.p1Torque = p1Torque;
    }

    public String getP2PercentTorSpeed() {
        return p2PercentTorSpeed;
    }

    public void setP2PercentTorSpeed(String p2PercentTorSpeed) {
        this.p2PercentTorSpeed = p2PercentTorSpeed;
    }

    public String getP3Speed() {
        return p3Speed;
    }

    public void setP3Speed(String p3Speed) {
        this.p3Speed = p3Speed;
    }

    public String getP3Torque() {
        return p3Torque;
    }

    public void setP3Torque(String p3Torque) {
        this.p3Torque = p3Torque;
    }

    public String getP4Speed() {
        return p4Speed;
    }

    public void setP4Speed(String p4Speed) {
        this.p4Speed = p4Speed;
    }

    public String getP4Troque() {
        return p4Troque;
    }

    public void setP4Troque(String p4Troque) {
        this.p4Troque = p4Troque;
    }

    public String getP5Speed() {
        return p5Speed;
    }

    public void setP5Speed(String p5Speed) {
        this.p5Speed = p5Speed;
    }

    public String getP5Torque() {
        return p5Torque;
    }

    public void setP5Torque(String p5Torque) {
        this.p5Torque = p5Torque;
    }

    public String getP6Speed() {
        return p6Speed;
    }

    public void setP6Speed(String p6Speed) {
        this.p6Speed = p6Speed;
    }

    public String getGainSpeed() {
        return gainSpeed;
    }

    public void setGainSpeed(String gainSpeed) {
        this.gainSpeed = gainSpeed;
    }

    public String getRefEngineTorque() {
        return refEngineTorque;
    }

    public void setRefEngineTorque(String refEngineTorque) {
        this.refEngineTorque = refEngineTorque;
    }

    public String getMaxSpeedLimit() {
        return maxSpeedLimit;
    }

    public void setMaxSpeedLimit(String maxSpeedLimit) {
        this.maxSpeedLimit = maxSpeedLimit;
    }

    public String getMaxTimeLimit() {
        return maxTimeLimit;
    }

    public void setMaxTimeLimit(String maxTimeLimit) {
        this.maxTimeLimit = maxTimeLimit;
    }

    public String getSpUpperLimit() {
        return spUpperLimit;
    }

    public void setSpUpperLimit(String spUpperLimit) {
        this.spUpperLimit = spUpperLimit;
    }

    public String getTorLowerLimit() {
        return torLowerLimit;
    }

    public void setTorLowerLimit(String torLowerLimit) {
        this.torLowerLimit = torLowerLimit;
    }

    public String getMomentIner() {
        return momentIner;
    }

    public void setMomentIner(String momentIner) {
        this.momentIner = momentIner;
    }

    public String getRelEngOilPressure() {
        return relEngOilPressure;
    }

    public void setRelEngOilPressure(String relEngOilPressure) {
        this.relEngOilPressure = relEngOilPressure;
    }

    public String getEngOilPreLow() {
        return engOilPreLow;
    }

    public void setEngOilPreLow(String engOilPreLow) {
        this.engOilPreLow = engOilPreLow;
    }

    public String getEngCoolTem() {
        return engCoolTem;
    }

    public void setEngCoolTem(String engCoolTem) {
        this.engCoolTem = engCoolTem;
    }

    public String getStartHeartStat() {
        return startHeartStat;
    }

    public void setStartHeartStat(String startHeartStat) {
        this.startHeartStat = startHeartStat;
    }

    public String getObDLampStatus() {
        return obDLampStatus;
    }

    public void setObDLampStatus(String obDLampStatus) {
        this.obDLampStatus = obDLampStatus;
    }

    public String getExOutput() {
        return exOutput;
    }

    public void setExOutput(String exOutput) {
        this.exOutput = exOutput;
    }

    public String getEsFanSp() {
        return esFanSp;
    }

    public void setEsFanSp(String esFanSp) {
        this.esFanSp = esFanSp;
    }

    public String getFanDriState() {
        return fanDriState;
    }

    public void setFanDriState(String fanDriState) {
        this.fanDriState = fanDriState;
    }

    public String getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(String fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public String getDpfPressure() {
        return dpfPressure;
    }

    public void setDpfPressure(String dpfPressure) {
        this.dpfPressure = dpfPressure;
    }

    public String getRelativePressure() {
        return relativePressure;
    }

    public void setRelativePressure(String relativePressure) {
        this.relativePressure = relativePressure;
    }

    public String getAbsolutePressure() {
        return absolutePressure;
    }

    public void setAbsolutePressure(String absolutePressure) {
        this.absolutePressure = absolutePressure;
    }

    public String getAirFilterPre() {
        return airFilterPre;
    }

    public void setAirFilterPre(String airFilterPre) {
        this.airFilterPre = airFilterPre;
    }

    public String getExhaustTem() {
        return exhaustTem;
    }

    public void setExhaustTem(String exhaustTem) {
        this.exhaustTem = exhaustTem;
    }

    public String getCoolDifferTem() {
        return coolDifferTem;
    }

    public void setCoolDifferTem(String coolDifferTem) {
        this.coolDifferTem = coolDifferTem;
    }

    public String getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(String atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }

    public String getEngineTem() {
        return engineTem;
    }

    public void setEngineTem(String engineTem) {
        this.engineTem = engineTem;
    }

    public String getAtmosphericTem() {
        return atmosphericTem;
    }

    public void setAtmosphericTem(String atmosphericTem) {
        this.atmosphericTem = atmosphericTem;
    }

    public String getAirInTem() {
        return airInTem;
    }

    public void setAirInTem(String airInTem) {
        this.airInTem = airInTem;
    }

    public String getPavementTem() {
        return pavementTem;
    }

    public void setPavementTem(String pavementTem) {
        this.pavementTem = pavementTem;
    }

    public String getPerTorResolution() {
        return perTorResolution;
    }

    public void setPerTorResolution(String perTorResolution) {
        this.perTorResolution = perTorResolution;
    }

    public String getEngineTorMode() {
        return engineTorMode;
    }

    public void setEngineTorMode(String engineTorMode) {
        this.engineTorMode = engineTorMode;
    }

    public String getDriverEnginePercentTor() {
        return driverEnginePercentTor;
    }

    public void setDriverEnginePercentTor(String driverEnginePercentTor) {
        this.driverEnginePercentTor = driverEnginePercentTor;
    }

    public String getActualEnginePercentTor() {
        return actualEnginePercentTor;
    }

    public void setActualEnginePercentTor(String actualEnginePercentTor) {
        this.actualEnginePercentTor = actualEnginePercentTor;
    }

    public String getAddControlDevice() {
        return addControlDevice;
    }

    public void setAddControlDevice(String addControlDevice) {
        this.addControlDevice = addControlDevice;
    }

    public String getEngineStartMode() {
        return engineStartMode;
    }

    public void setEngineStartMode(String engineStartMode) {
        this.engineStartMode = engineStartMode;
    }

    public String getEngineDePerTor() {
        return engineDePerTor;
    }

    public void setEngineDePerTor(String engineDePerTor) {
        this.engineDePerTor = engineDePerTor;
    }

    public String getSpeedLimitStatus() {
        return speedLimitStatus;
    }

    public void setSpeedLimitStatus(String speedLimitStatus) {
        this.speedLimitStatus = speedLimitStatus;
    }

    public String getAccPedalKickdownSwitch() {
        return accPedalKickdownSwitch;
    }

    public void setAccPedalKickdownSwitch(String accPedalKickdownSwitch) {
        this.accPedalKickdownSwitch = accPedalKickdownSwitch;
    }

    public String getAccPedalLowIdleSwitch() {
        return accPedalLowIdleSwitch;
    }

    public void setAccPedalLowIdleSwitch(String accPedalLowIdleSwitch) {
        this.accPedalLowIdleSwitch = accPedalLowIdleSwitch;
    }

    public String getAccPedalPos() {
        return accPedalPos;
    }

    public void setAccPedalPos(String accPedalPos) {
        this.accPedalPos = accPedalPos;
    }

    public String getPercentLoadAtCurrentSpd() {
        return percentLoadAtCurrentSpd;
    }

    public void setPercentLoadAtCurrentSpd(String percentLoadAtCurrentSpd) {
        this.percentLoadAtCurrentSpd = percentLoadAtCurrentSpd;
    }

    public String getReAccPosition() {
        return reAccPosition;
    }

    public void setReAccPosition(String reAccPosition) {
        this.reAccPosition = reAccPosition;
    }

    public String getReAccPosition2() {
        return reAccPosition2;
    }

    public void setReAccPosition2(String reAccPosition2) {
        this.reAccPosition2 = reAccPosition2;
    }

    public String getMaxAvailableEngPerTor() {
        return maxAvailableEngPerTor;
    }

    public void setMaxAvailableEngPerTor(String maxAvailableEngPerTor) {
        this.maxAvailableEngPerTor = maxAvailableEngPerTor;
    }

    public String getNominalFrictionPercentTrq() {
        return nominalFrictionPercentTrq;
    }

    public void setNominalFrictionPercentTrq(String nominalFrictionPercentTrq) {
        this.nominalFrictionPercentTrq = nominalFrictionPercentTrq;
    }

    public String getDesirOperaSpeed() {
        return desirOperaSpeed;
    }

    public void setDesirOperaSpeed(String desirOperaSpeed) {
        this.desirOperaSpeed = desirOperaSpeed;
    }

    public String getEngAsyAdjust() {
        return engAsyAdjust;
    }

    public void setEngAsyAdjust(String engAsyAdjust) {
        this.engAsyAdjust = engAsyAdjust;
    }

    public String getEstimaParLoss() {
        return estimaParLoss;
    }

    public void setEstimaParLoss(String estimaParLoss) {
        this.estimaParLoss = estimaParLoss;
    }

    public String getExhGasMassflow() {
        return exhGasMassflow;
    }

    public void setExhGasMassflow(String exhGasMassflow) {
        this.exhGasMassflow = exhGasMassflow;
    }

    public String getAf1Intake() {
        return af1Intake;
    }

    public void setAf1Intake(String af1Intake) {
        this.af1Intake = af1Intake;
    }

    public String getAf1Exhaust() {
        return af1Exhaust;
    }

    public void setAf1Exhaust(String af1Exhaust) {
        this.af1Exhaust = af1Exhaust;
    }

    public String getAf2Intake() {
        return af2Intake;
    }

    public void setAf2Intake(String af2Intake) {
        this.af2Intake = af2Intake;
    }

    public String getAf2Exhaust() {
        return af2Exhaust;
    }

    public void setAf2Exhaust(String af2Exhaust) {
        this.af2Exhaust = af2Exhaust;
    }

    public String getCruPauseSwitch() {
        return cruPauseSwitch;
    }

    public void setCruPauseSwitch(String cruPauseSwitch) {
        this.cruPauseSwitch = cruPauseSwitch;
    }

    public String getBrakeSwitch() {
        return brakeSwitch;
    }

    public void setBrakeSwitch(String brakeSwitch) {
        this.brakeSwitch = brakeSwitch;
    }

    public String getWheelBasedVehicleSpd() {
        return wheelBasedVehicleSpd;
    }

    public void setWheelBasedVehicleSpd(String wheelBasedVehicleSpd) {
        this.wheelBasedVehicleSpd = wheelBasedVehicleSpd;
    }

    public String getClutchSwitch() {
        return clutchSwitch;
    }

    public void setClutchSwitch(String clutchSwitch) {
        this.clutchSwitch = clutchSwitch;
    }

    public String getParkingBrakeSwitch() {
        return parkingBrakeSwitch;
    }

    public void setParkingBrakeSwitch(String parkingBrakeSwitch) {
        this.parkingBrakeSwitch = parkingBrakeSwitch;
    }

    public String getCruiControlEnable() {
        return cruiControlEnable;
    }

    public void setCruiControlEnable(String cruiControlEnable) {
        this.cruiControlEnable = cruiControlEnable;
    }

    public String getCruiseControlActive() {
        return cruiseControlActive;
    }

    public void setCruiseControlActive(String cruiseControlActive) {
        this.cruiseControlActive = cruiseControlActive;
    }

    public String getCruiseCtrlAccSwitch() {
        return cruiseCtrlAccSwitch;
    }

    public void setCruiseCtrlAccSwitch(String cruiseCtrlAccSwitch) {
        this.cruiseCtrlAccSwitch = cruiseCtrlAccSwitch;
    }

    public String getCruiseCtrlResumeSwitch() {
        return cruiseCtrlResumeSwitch;
    }

    public void setCruiseCtrlResumeSwitch(String cruiseCtrlResumeSwitch) {
        this.cruiseCtrlResumeSwitch = cruiseCtrlResumeSwitch;
    }

    public String getCruiseCtrlCoastSwitch() {
        return cruiseCtrlCoastSwitch;
    }

    public void setCruiseCtrlCoastSwitch(String cruiseCtrlCoastSwitch) {
        this.cruiseCtrlCoastSwitch = cruiseCtrlCoastSwitch;
    }

    public String getCruiseCtrlSetSwitch() {
        return cruiseCtrlSetSwitch;
    }

    public void setCruiseCtrlSetSwitch(String cruiseCtrlSetSwitch) {
        this.cruiseCtrlSetSwitch = cruiseCtrlSetSwitch;
    }

    public String getCruiseCtrlSetSpd() {
        return cruiseCtrlSetSpd;
    }

    public void setCruiseCtrlSetSpd(String cruiseCtrlSetSpd) {
        this.cruiseCtrlSetSpd = cruiseCtrlSetSpd;
    }

    public String getCruiseCtrlStates() {
        return cruiseCtrlStates;
    }

    public void setCruiseCtrlStates(String cruiseCtrlStates) {
        this.cruiseCtrlStates = cruiseCtrlStates;
    }

    public String getPtoState() {
        return ptoState;
    }

    public void setPtoState(String ptoState) {
        this.ptoState = ptoState;
    }

    public String getEngShutOverSwitch() {
        return engShutOverSwitch;
    }

    public void setEngShutOverSwitch(String engShutOverSwitch) {
        this.engShutOverSwitch = engShutOverSwitch;
    }

    public String getEngIdleDecSwitch() {
        return engIdleDecSwitch;
    }

    public void setEngIdleDecSwitch(String engIdleDecSwitch) {
        this.engIdleDecSwitch = engIdleDecSwitch;
    }

    public String getEngIdleIncSwitch() {
        return engIdleIncSwitch;
    }

    public void setEngIdleIncSwitch(String engIdleIncSwitch) {
        this.engIdleIncSwitch = engIdleIncSwitch;
    }

    public String getOutPutRoateSpeed() {
        return outPutRoateSpeed;
    }

    public void setOutPutRoateSpeed(String outPutRoateSpeed) {
        this.outPutRoateSpeed = outPutRoateSpeed;
    }

    public String getTachographVehicleSpeed() {
        return tachographVehicleSpeed;
    }

    public void setTachographVehicleSpeed(String tachographVehicleSpeed) {
        this.tachographVehicleSpeed = tachographVehicleSpeed;
    }

    public String getRetarTorMode() {
        return retarTorMode;
    }

    public void setRetarTorMode(String retarTorMode) {
        this.retarTorMode = retarTorMode;
    }

    public String getRetBrakeAssSwitch() {
        return retBrakeAssSwitch;
    }

    public void setRetBrakeAssSwitch(String retBrakeAssSwitch) {
        this.retBrakeAssSwitch = retBrakeAssSwitch;
    }

    public String getRetShiftAssSwitch() {
        return retShiftAssSwitch;
    }

    public void setRetShiftAssSwitch(String retShiftAssSwitch) {
        this.retShiftAssSwitch = retShiftAssSwitch;
    }

    public String getActRetTorPer() {
        return actRetTorPer;
    }

    public void setActRetTorPer(String actRetTorPer) {
        this.actRetTorPer = actRetTorPer;
    }

    public String getIntRetPerTor() {
        return intRetPerTor;
    }

    public void setIntRetPerTor(String intRetPerTor) {
        this.intRetPerTor = intRetPerTor;
    }

    public String getCoolLoadIncrease() {
        return coolLoadIncrease;
    }

    public void setCoolLoadIncrease(String coolLoadIncrease) {
        this.coolLoadIncrease = coolLoadIncrease;
    }

    public String getRetReqBrakeLight() {
        return retReqBrakeLight;
    }

    public void setRetReqBrakeLight(String retReqBrakeLight) {
        this.retReqBrakeLight = retReqBrakeLight;
    }

    public String getAddOfControDevice() {
        return addOfControDevice;
    }

    public void setAddOfControDevice(String addOfControDevice) {
        this.addOfControDevice = addOfControDevice;
    }

    public String getDemRetPerTor() {
        return demRetPerTor;
    }

    public void setDemRetPerTor(String demRetPerTor) {
        this.demRetPerTor = demRetPerTor;
    }

    public String getRetSwitchPerTor() {
        return retSwitchPerTor;
    }

    public void setRetSwitchPerTor(String retSwitchPerTor) {
        this.retSwitchPerTor = retSwitchPerTor;
    }

    public String getActMaxAvailableRetPerTor() {
        return actMaxAvailableRetPerTor;
    }

    public void setActMaxAvailableRetPerTor(String actMaxAvailableRetPerTor) {
        this.actMaxAvailableRetPerTor = actMaxAvailableRetPerTor;
    }

    public String getRetarderLocation() {
        return retarderLocation;
    }

    public void setRetarderLocation(String retarderLocation) {
        this.retarderLocation = retarderLocation;
    }

    public String getRetarderType() {
        return retarderType;
    }

    public void setRetarderType(String retarderType) {
        this.retarderType = retarderType;
    }

    public String getRetarderConMethod() {
        return retarderConMethod;
    }

    public void setRetarderConMethod(String retarderConMethod) {
        this.retarderConMethod = retarderConMethod;
    }

    public String getRetarderSpIdlePoint1() {
        return retarderSpIdlePoint1;
    }

    public void setRetarderSpIdlePoint1(String retarderSpIdlePoint1) {
        this.retarderSpIdlePoint1 = retarderSpIdlePoint1;
    }

    public String getRetarderPerTorIdle() {
        return retarderPerTorIdle;
    }

    public void setRetarderPerTorIdle(String retarderPerTorIdle) {
        this.retarderPerTorIdle = retarderPerTorIdle;
    }

    public String getMaxRetSpeedPoint2() {
        return maxRetSpeedPoint2;
    }

    public void setMaxRetSpeedPoint2(String maxRetSpeedPoint2) {
        this.maxRetSpeedPoint2 = maxRetSpeedPoint2;
    }

    public String getRetPerTorMaxSpeedPoint2() {
        return retPerTorMaxSpeedPoint2;
    }

    public void setRetPerTorMaxSpeedPoint2(String retPerTorMaxSpeedPoint2) {
        this.retPerTorMaxSpeedPoint2 = retPerTorMaxSpeedPoint2;
    }

    public String getEngSpeedPoint3() {
        return engSpeedPoint3;
    }

    public void setEngSpeedPoint3(String engSpeedPoint3) {
        this.engSpeedPoint3 = engSpeedPoint3;
    }

    public String getRetarderPerTorPoint3() {
        return retarderPerTorPoint3;
    }

    public void setRetarderPerTorPoint3(String retarderPerTorPoint3) {
        this.retarderPerTorPoint3 = retarderPerTorPoint3;
    }

    public String getEngSpeedPoint4() {
        return engSpeedPoint4;
    }

    public void setEngSpeedPoint4(String engSpeedPoint4) {
        this.engSpeedPoint4 = engSpeedPoint4;
    }

    public String getRetarderPerTorPoint4() {
        return retarderPerTorPoint4;
    }

    public void setRetarderPerTorPoint4(String retarderPerTorPoint4) {
        this.retarderPerTorPoint4 = retarderPerTorPoint4;
    }

    public String getRetSpeedTorPoint5() {
        return retSpeedTorPoint5;
    }

    public void setRetSpeedTorPoint5(String retSpeedTorPoint5) {
        this.retSpeedTorPoint5 = retSpeedTorPoint5;
    }

    public String getRefRetarTorque() {
        return refRetarTorque;
    }

    public void setRefRetarTorque(String refRetarTorque) {
        this.refRetarTorque = refRetarTorque;
    }

    public String getRetarderPerTorPoint5() {
        return retarderPerTorPoint5;
    }

    public void setRetarderPerTorPoint5(String retarderPerTorPoint5) {
        this.retarderPerTorPoint5 = retarderPerTorPoint5;
    }

    public String getCruiseCtrlActive() {
        return cruiseCtrlActive;
    }

    public void setCruiseCtrlActive(String cruiseCtrlActive) {
        this.cruiseCtrlActive = cruiseCtrlActive;
    }

    public String getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(String breakdown) {
        this.breakdown = breakdown;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getGpsMileage() {
        return gpsMileage;
    }

    public void setGpsMileage(String gpsMileage) {
        this.gpsMileage = gpsMileage;
    }

    public String getLynCylinder1TotalCapacity() {
        return lynCylinder1TotalCapacity;
    }

    public void setLynCylinder1TotalCapacity(String lynCylinder1TotalCapacity) {
        this.lynCylinder1TotalCapacity = lynCylinder1TotalCapacity;
    }

    public String getLynCylinder1ResidualCapacity() {
        return lynCylinder1ResidualCapacity;
    }

    public void setLynCylinder1ResidualCapacity(String lynCylinder1ResidualCapacity) {
        this.lynCylinder1ResidualCapacity = lynCylinder1ResidualCapacity;
    }

    public String getLynCylinder2TotalCapacity() {
        return lynCylinder2TotalCapacity;
    }

    public void setLynCylinder2TotalCapacity(String lynCylinder2TotalCapacity) {
        this.lynCylinder2TotalCapacity = lynCylinder2TotalCapacity;
    }

    public String getLynCylinder2ResidualCapacity() {
        return lynCylinder2ResidualCapacity;
    }

    public void setLynCylinder2ResidualCapacity(String lynCylinder2ResidualCapacity) {
        this.lynCylinder2ResidualCapacity = lynCylinder2ResidualCapacity;
    }

    public String getLynCylinder3TotalCapacity() {
        return lynCylinder3TotalCapacity;
    }

    public void setLynCylinder3TotalCapacity(String lynCylinder3TotalCapacity) {
        this.lynCylinder3TotalCapacity = lynCylinder3TotalCapacity;
    }

    public String getLynCylinder3ResidualCapacity() {
        return lynCylinder3ResidualCapacity;
    }

    public void setLynCylinder3ResidualCapacity(String lynCylinder3ResidualCapacity) {
        this.lynCylinder3ResidualCapacity = lynCylinder3ResidualCapacity;
    }

    public String getLynCylinder4TotalCapacity() {
        return lynCylinder4TotalCapacity;
    }

    public void setLynCylinder4TotalCapacity(String lynCylinder4TotalCapacity) {
        this.lynCylinder4TotalCapacity = lynCylinder4TotalCapacity;
    }

    public String getLynCylinder4ResidualCapacity() {
        return lynCylinder4ResidualCapacity;
    }

    public void setLynCylinder4ResidualCapacity(String lynCylinder4ResidualCapacity) {
        this.lynCylinder4ResidualCapacity = lynCylinder4ResidualCapacity;
    }

    public String getUreaConcentration() {
        return ureaConcentration;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public void setUreaConcentration(String ureaConcentration) {
        this.ureaConcentration = ureaConcentration;
    }

    public String getIntegralFuelConsumption() {
        return integralFuelConsumption;
    }

    public void setIntegralFuelConsumption(String integralFuelConsumption) {
        this.integralFuelConsumption = integralFuelConsumption;
    }

    public String getGpstime() {
        return gpstime;
    }

    public void setGpstime(String gpstime) {
        this.gpstime = gpstime;
    }

	public String getCarLoad() {
		return carLoad;
	}

	public void setCarLoad(String carLoad) {
		this.carLoad = carLoad;
	}
    
}
