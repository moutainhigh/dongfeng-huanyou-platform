package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

/**
 * 轨迹回放: 轨迹点  DTO前台接收类
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTracesPojo {
    //地址
    private String address;
    //报警
    private String alarm;
    //故障
    private String fault;
    //GPS时间
    private String gpsdate;
    //经度
    private  double lat;
    //纬度
    private double lng;
    //剩余油量
    private String oilwear;
    //状态
    private String statue;
    //整车油耗
    private String totalFuelConsumption;
    //整车里程
    private String totolmileage;
    //速度
    private String velocity;
    //接收时间
    private String receiveDate;
    //方向
    private String direction;
    //终端里程
    private String mileage;
    //导出明细增加字段
    private double height;//高程
    private String dailyMileage;//当日里程 小计里程
    private double rotation;//车辆当前转速
    private double cumulativeTurningNumber;//发动机累计转数（单位:1000rpm）
    private long cumulativeRunningTime;//累计运行时间 秒
    private String cumulativeRunningTimeStr;//累计运行时间 秒
    private double coolingWaterTem;//发动机冷却水温度
    private double totalOil;//总油耗
    private double fuelConsumptionRate;//发动机燃油消耗率（毫升/小时）
    private double averageFuelConsumption;//平均燃油消耗率（千米/升）
    private double realTimeOilConsumption;//瞬时油耗
    private String engineTorMode;//转矩控制模式 0 低怠速调节器/无请求（默认模式）1 加速踏板2 巡航控制3 PTO调节器4 车速调节器5 ASR控制6 变速器控制7 ABS控制 8 转矩限定9 高速调节器10 制动系统11 遥控加速器
    private double driverEnginePercentTor;//驾驶员需求发动机转矩百分比
    private double actualEnginePercentTor;//实际发动机转矩百分比
    private String accPedalLowIdleSwitch;//加速踏板低怠速开关
    private String accPedalKickdownSwitch;//加速踏板Kickdown开关：0 被动 1 主动
    private String accPedalPos;//加速踏板开度
    private double percentLoadAtCurrentSpd;//当前速度下，负载百分比
    private double nominalFrictionPercentTrq;//名义摩擦力矩百分比
    private String parkingBrakeSwitch;//驻车制动器开关：0 未操作  1 操作
    private double wheelBasedVehicleSpd;//车轮车速
    private String cruiseCtrlActive;//巡航控制开关状态：0 未激活 1 激活
    private String brakeSwitch;//制动开关：0 未操作 1 操作
    private String clutchSwitch;//离合器开关：0 未分离  1 分离
    private String cruiseCtrlSetSwitch;//巡航控制设置开关：0 无按钮按下 1 退出巡航
    private String cruiseCtrlCoastSwitch;//巡航控制减速开关：0 未按下 1 按下
    private String cruiseCtrlResumeSwitch;//巡航控制恢复开关：0 未按下 1 按下
    private String cruiseCtrlAccSwitch;//巡航控制加速开关：0 未按下  1 按下
    private double cruiseCtrlSetSpd;//巡航控制设置速度
    private String cruiseCtrlStates;//巡航控制状态： 0 关闭/禁止 1 保持 2 加速 3 减速/滑行  4 恢复 5 设置  6 加速踏板取代
    private int outPutRoateSpeed;//轴转速

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getGpsdate() {
        return gpsdate;
    }

    public void setGpsdate(String gpsdate) {
        this.gpsdate = gpsdate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getOilwear() {
        return oilwear;
    }

    public void setOilwear(String oilwear) {
        this.oilwear = oilwear;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public void setTotalFuelConsumption(String totalFuelConsumption) {
        this.totalFuelConsumption = totalFuelConsumption;
    }

    public String getTotolmileage() {
        return totolmileage;
    }

    public void setTotolmileage(String totolmileage) {
        this.totolmileage = totolmileage;
    }

    public String getVelocity() {
        return velocity;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDailyMileage() {
        return dailyMileage;
    }

    public void setDailyMileage(String dailyMileage) {
        this.dailyMileage = dailyMileage;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getCumulativeTurningNumber() {
        return cumulativeTurningNumber;
    }

    public void setCumulativeTurningNumber(double cumulativeTurningNumber) {
        this.cumulativeTurningNumber = cumulativeTurningNumber;
    }

    public long getCumulativeRunningTime() {
        return cumulativeRunningTime;
    }

    public void setCumulativeRunningTime(long cumulativeRunningTime) {
        this.cumulativeRunningTime = cumulativeRunningTime;
    }

    public String getCumulativeRunningTimeStr() {
        return cumulativeRunningTimeStr;
    }

    public void setCumulativeRunningTimeStr(String cumulativeRunningTimeStr) {
        this.cumulativeRunningTimeStr = cumulativeRunningTimeStr;
    }

    public double getCoolingWaterTem() {
        return coolingWaterTem;
    }

    public void setCoolingWaterTem(double coolingWaterTem) {
        this.coolingWaterTem = coolingWaterTem;
    }

    public double getTotalOil() {
        return totalOil;
    }

    public void setTotalOil(double totalOil) {
        this.totalOil = totalOil;
    }

    public double getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(double fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(double averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public double getRealTimeOilConsumption() {
        return realTimeOilConsumption;
    }

    public void setRealTimeOilConsumption(double realTimeOilConsumption) {
        this.realTimeOilConsumption = realTimeOilConsumption;
    }

    public String getEngineTorMode() {
        return engineTorMode;
    }

    public void setEngineTorMode(String engineTorMode) {
        this.engineTorMode = engineTorMode;
    }

    public double getDriverEnginePercentTor() {
        return driverEnginePercentTor;
    }

    public void setDriverEnginePercentTor(double driverEnginePercentTor) {
        this.driverEnginePercentTor = driverEnginePercentTor;
    }

    public double getActualEnginePercentTor() {
        return actualEnginePercentTor;
    }

    public void setActualEnginePercentTor(double actualEnginePercentTor) {
        this.actualEnginePercentTor = actualEnginePercentTor;
    }

    public String getAccPedalLowIdleSwitch() {
        return accPedalLowIdleSwitch;
    }

    public void setAccPedalLowIdleSwitch(String accPedalLowIdleSwitch) {
        this.accPedalLowIdleSwitch = accPedalLowIdleSwitch;
    }

    public String getAccPedalKickdownSwitch() {
        return accPedalKickdownSwitch;
    }

    public void setAccPedalKickdownSwitch(String accPedalKickdownSwitch) {
        this.accPedalKickdownSwitch = accPedalKickdownSwitch;
    }

    public String getAccPedalPos() {
        return accPedalPos;
    }

    public void setAccPedalPos(String accPedalPos) {
        this.accPedalPos = accPedalPos;
    }

    public double getPercentLoadAtCurrentSpd() {
        return percentLoadAtCurrentSpd;
    }

    public void setPercentLoadAtCurrentSpd(double percentLoadAtCurrentSpd) {
        this.percentLoadAtCurrentSpd = percentLoadAtCurrentSpd;
    }

    public double getNominalFrictionPercentTrq() {
        return nominalFrictionPercentTrq;
    }

    public void setNominalFrictionPercentTrq(double nominalFrictionPercentTrq) {
        this.nominalFrictionPercentTrq = nominalFrictionPercentTrq;
    }

    public String getParkingBrakeSwitch() {
        return parkingBrakeSwitch;
    }

    public void setParkingBrakeSwitch(String parkingBrakeSwitch) {
        this.parkingBrakeSwitch = parkingBrakeSwitch;
    }

    public double getWheelBasedVehicleSpd() {
        return wheelBasedVehicleSpd;
    }

    public void setWheelBasedVehicleSpd(double wheelBasedVehicleSpd) {
        this.wheelBasedVehicleSpd = wheelBasedVehicleSpd;
    }

    public String getCruiseCtrlActive() {
        return cruiseCtrlActive;
    }

    public void setCruiseCtrlActive(String cruiseCtrlActive) {
        this.cruiseCtrlActive = cruiseCtrlActive;
    }

    public String getBrakeSwitch() {
        return brakeSwitch;
    }

    public void setBrakeSwitch(String brakeSwitch) {
        this.brakeSwitch = brakeSwitch;
    }

    public String getClutchSwitch() {
        return clutchSwitch;
    }

    public void setClutchSwitch(String clutchSwitch) {
        this.clutchSwitch = clutchSwitch;
    }

    public String getCruiseCtrlSetSwitch() {
        return cruiseCtrlSetSwitch;
    }

    public void setCruiseCtrlSetSwitch(String cruiseCtrlSetSwitch) {
        this.cruiseCtrlSetSwitch = cruiseCtrlSetSwitch;
    }

    public String getCruiseCtrlCoastSwitch() {
        return cruiseCtrlCoastSwitch;
    }

    public void setCruiseCtrlCoastSwitch(String cruiseCtrlCoastSwitch) {
        this.cruiseCtrlCoastSwitch = cruiseCtrlCoastSwitch;
    }

    public String getCruiseCtrlResumeSwitch() {
        return cruiseCtrlResumeSwitch;
    }

    public void setCruiseCtrlResumeSwitch(String cruiseCtrlResumeSwitch) {
        this.cruiseCtrlResumeSwitch = cruiseCtrlResumeSwitch;
    }

    public String getCruiseCtrlAccSwitch() {
        return cruiseCtrlAccSwitch;
    }

    public void setCruiseCtrlAccSwitch(String cruiseCtrlAccSwitch) {
        this.cruiseCtrlAccSwitch = cruiseCtrlAccSwitch;
    }

    public double getCruiseCtrlSetSpd() {
        return cruiseCtrlSetSpd;
    }

    public void setCruiseCtrlSetSpd(double cruiseCtrlSetSpd) {
        this.cruiseCtrlSetSpd = cruiseCtrlSetSpd;
    }

    public String getCruiseCtrlStates() {
        return cruiseCtrlStates;
    }

    public void setCruiseCtrlStates(String cruiseCtrlStates) {
        this.cruiseCtrlStates = cruiseCtrlStates;
    }

    public int getOutPutRoateSpeed() {
        return outPutRoateSpeed;
    }

    public void setOutPutRoateSpeed(int outPutRoateSpeed) {
        this.outPutRoateSpeed = outPutRoateSpeed;
    }
}