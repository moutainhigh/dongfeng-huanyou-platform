package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/17
 */
public class QueryLocInfosPojo{
    //车辆ID
    private BigInteger carId;
    //车牌号
    private String carCph;
    //底盘号
    private String chassisNum;
    //终端通信号
    private String commId;
    //SIM卡
    private String sim;
    //经销商
    private String dealer;
    //所属客户
    private String customer;
    //最近上报时间
    private String lastGpsTime;
    //速度
    private Integer speed;
    //方向
    private Integer direction;
    //当前里程
    private Float totalMilleage;
    //剩余油量
    private Float oil;
    //当日里程
    private Float todayMilleage;
    //当日油耗
    private Float oilwear;
    //状态
    private String accStauts;
    //末次有效位置经度
    private Double lastGpsLng;
    //末次有效位置纬度
    private Double lastGpsLat;

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getLastGpsTime() {
        return lastGpsTime;
    }

    public void setLastGpsTime(String lastGpsTime) {
        this.lastGpsTime = lastGpsTime;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Float getTotalMilleage() {
        return totalMilleage;
    }

    public void setTotalMilleage(Float totalMilleage) {
        this.totalMilleage = totalMilleage;
    }

    public Float getOil() {
        return oil;
    }

    public void setOil(Float oil) {
        this.oil = oil;
    }

    public Float getTodayMilleage() {
        return todayMilleage;
    }

    public void setTodayMilleage(Float todayMilleage) {
        this.todayMilleage = todayMilleage;
    }

    public Float getOilwear() {
        return oilwear;
    }

    public void setOilwear(Float oilwear) {
        this.oilwear = oilwear;
    }

    public String getAccStauts() {
        return accStauts;
    }

    public void setAccStauts(String accStauts) {
        this.accStauts = accStauts;
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
}
