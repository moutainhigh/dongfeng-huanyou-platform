package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * Created by wenya on 2017/3/8.
 * 车辆tip对象
 */
public class QueryCarTipPojo {
    //车辆主键
    private BigInteger id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //所属经销商
    private String dealer;
    //所属客户
    private String customer;
    //平台锁车状态
    private BigInteger lockStauts;
    //发动机类型
    private String engineType;
    //绑定终端mic 影响PC及手机端监听功能的显示与否（是1，否0）
    private Integer terminalMic;
    //绑定终端摄像头
    private String terminalChannel;
    //邮箱容量
    private String oilCapacity;
    //通信号
    private BigInteger commId;
    //上报时间
    private Long gpstime;
    //车辆在线状态
    private Integer carStauts;
    //车速
    private Integer speed;
    //剩余油量
    private Double resOil;
    //总里程
    private Double totalMilleage;
    //当日里程
    private Double todayMilleage;
    //acc状态
    private String accStauts;
    //当前故障
    private String fault;
    //经度
    private Integer lng;
    //纬度
    private Integer lat;
    //有效时间
    private Long gpstimeVaild;
    //有效经度
    private Integer lngVaild;
    //有效纬度
    private Integer latValid;
    //STD销售状态
    private String stdSalesStatus;
    //AAK销售状态
    private String aakSalesStatus;
    //方向
    private Integer direction;
    //天然气or柴油车：0表示燃气，1表示燃油
    private Integer fuel;
    //整车载重
    private String carLoad;

    public String getStdSalesStatus() {
        return stdSalesStatus;
    }

    public void setStdSalesStatus(String stdSalesStatus) {
        this.stdSalesStatus = stdSalesStatus;
    }

    public String getAakSalesStatus() {
        return aakSalesStatus;
    }

    public void setAakSalesStatus(String aakSalesStatus) {
        this.aakSalesStatus = aakSalesStatus;
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

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigInteger getLockStauts() {
        return lockStauts;
    }

    public void setLockStauts(BigInteger lockStauts) {
        this.lockStauts = lockStauts;
    }

    public Long getGpstime() {
        return gpstime;
    }

    public void setGpstime(Long gpstime) {
        this.gpstime = gpstime;
    }

    public Integer getCarStauts() {
        return carStauts;
    }

    public void setCarStauts(Integer carStauts) {
        this.carStauts = carStauts;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Double getTodayMilleage() {
        return todayMilleage;
    }

    public void setTodayMilleage(Double todayMilleage) {
        this.todayMilleage = todayMilleage;
    }

    public Double getTotalMilleage() {
        return totalMilleage;
    }

    public void setTotalMilleage(Double totalMilleage) {
        this.totalMilleage = totalMilleage;
    }

    public String getAccStauts() {
        return accStauts;
    }

    public void setAccStauts(String accStauts) {
        this.accStauts = accStauts;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public Long getGpstimeVaild() {
        return gpstimeVaild;
    }

    public void setGpstimeVaild(Long gpstimeVaild) {
        this.gpstimeVaild = gpstimeVaild;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLngVaild() {
        return lngVaild;
    }

    public void setLngVaild(Integer lngVaild) {
        this.lngVaild = lngVaild;
    }

    public Integer getLatValid() {
        return latValid;
    }

    public void setLatValid(Integer latValid) {
        this.latValid = latValid;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Integer getTerminalMic() {
        return terminalMic;
    }

    public void setTerminalMic(Integer terminalMic) {
        this.terminalMic = terminalMic;
    }

    public String getTerminalChannel() {
        return terminalChannel;
    }

    public void setTerminalChannel(String terminalChannel) {
        this.terminalChannel = terminalChannel;
    }

    public String getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(String oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public Double getResOil() {
        return resOil;
    }

    public void setResOil(Double resOil) {
        this.resOil = resOil;
    }

    public Integer getDirection() {
        return direction;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

	public String getCarLoad() {
		return carLoad;
	}

	public void setCarLoad(String carLoad) {
		this.carLoad = carLoad;
	}
    
}
