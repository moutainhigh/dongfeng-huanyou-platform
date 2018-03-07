package com.navinfo.opentsp.dongfeng.report.dto.general;
/**
 * @wenya
 * @create 2017-03-28 18:27
 **/
public class QueryCarCountDto {
    //查询返回
    private int count;  //车次数
    private int govCode; //区域编码
    private String govName;//区域名字
    //导出dto
    private String chassicNum;//底盘号
    private String carNum;//车牌号
    private String carType;//车辆类型
    private String engineType;//发动机类型
    private String structureNum;// 车型结构号
    // 末次有效位置时间
    private Long lastCarDate;
    private String lastCarDateStr;
    // 末次有效位置-经度
    private Double lastLon;
    // 末次有效位置-纬度
    private Double lastLat;
    private String lastLoction;//末次位置信息
    private String govCityName; //省名字

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGovCode() {
        return govCode;
    }

    public void setGovCode(int govCode) {
        this.govCode = govCode;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getChassicNum() {
        return chassicNum;
    }

    public void setChassicNum(String chassicNum) {
        this.chassicNum = chassicNum;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getGovCityName() {
        return govCityName;
    }

    public void setGovCityName(String govCityName) {
        this.govCityName = govCityName;
    }

    public Long getLastCarDate() {
        return lastCarDate;
    }

    public void setLastCarDate(Long lastCarDate) {
        this.lastCarDate = lastCarDate;
    }

    public String getLastCarDateStr() {
        return lastCarDateStr;
    }

    public void setLastCarDateStr(String lastCarDateStr) {
        this.lastCarDateStr = lastCarDateStr;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    public String getLastLoction() {
        return lastLoction;
    }

    public void setLastLoction(String lastLoction) {
        this.lastLoction = lastLoction;
    }

    public Double getLastLon() {
        return lastLon;
    }

    public void setLastLon(Double lastLon) {
        this.lastLon = lastLon;
    }

    public String getStructureNum() {
        return structureNum;
    }

    public void setStructureNum(String structureNum) {
        this.structureNum = structureNum;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }
}
