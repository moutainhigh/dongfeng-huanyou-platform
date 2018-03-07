package com.navinfo.opentsp.dongfeng.monitor.dto.car;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
public class QueryDealerVehicleDetailDto {
    // 底盘号
    private String chassisNum;
    // 车牌号
    private String plateNum;
    // 车辆类型（数据字典）
    private String carType;
    // 发动机类型
    private String engineType;
    //累计里程(千米)
    private String countMilleage;
    //发动机累计运行(秒)
    private String cumulativeRunningTime;
    // DMS销售时间
    private String dmsSaleDate;
    // 入网时间
    private String accessDate;
    // 末次位置时间
    private String lastLocationDate;
    // 末次位置-经度
    private String lastLon;
    // 末次位置-纬度
    private String lastLat;
    //离库位置
    //防控状态
    private String lockStauts;

    //方向
    private String direction;
    //在线状态
    private String carStatus;

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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCountMilleage() {
        return countMilleage;
    }

    public void setCountMilleage(String countMilleage) {
        this.countMilleage = countMilleage;
    }

    public String getCumulativeRunningTime() {
        return cumulativeRunningTime;
    }

    public void setCumulativeRunningTime(String cumulativeRunningTime) {
        this.cumulativeRunningTime = cumulativeRunningTime;
    }

    public String getDmsSaleDate() {
        return dmsSaleDate;
    }

    public void setDmsSaleDate(String dmsSaleDate) {
        this.dmsSaleDate = dmsSaleDate;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public String getLastLocationDate() {
        return lastLocationDate;
    }

    public void setLastLocationDate(String lastLocationDate) {
        this.lastLocationDate = lastLocationDate;
    }

    public String getLastLon() {
        return lastLon;
    }

    public void setLastLon(String lastLon) {
        this.lastLon = lastLon;
    }

    public String getLastLat() {
        return lastLat;
    }

    public void setLastLat(String lastLat) {
        this.lastLat = lastLat;
    }

    public String getLockStauts() {
        return lockStauts;
    }

    public void setLockStauts(String lockStauts) {
        this.lockStauts = lockStauts;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "QueryDealerVehicleDetailDto{" +
                "chassisNum='" + chassisNum + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", carType='" + carType + '\'' +
                ", engineType='" + engineType + '\'' +
                ", countMilleage='" + countMilleage + '\'' +
                ", cumulativeRunningTime='" + cumulativeRunningTime + '\'' +
                ", dmsSaleDate='" + dmsSaleDate + '\'' +
                ", accessDate='" + accessDate + '\'' +
                ", lastLocationDate='" + lastLocationDate + '\'' +
                ", lastLon='" + lastLon + '\'' +
                ", lastLat='" + lastLat + '\'' +
                ", lockStauts='" + lockStauts + '\'' +
                ", direction='" + direction + '\'' +
                ", carStatus='" + carStatus + '\'' +
                '}';
    }
}
