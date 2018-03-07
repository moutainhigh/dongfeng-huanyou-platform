package com.navinfo.opentsp.dongfeng.monitor.dto.car;

/**
 * 车辆tip
 *
 * @wenya
 * @create 2017-03-09 14:46
 **/
public class QueryCarTipDto {
    //车辆主键
    private Long id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //车辆在线状态
    private Integer carStauts;
    //车辆在线状态
    private Integer online;
    //所属经销商
    private String dealer;
    //所属客户
    private String customer;
    //上报时间
    private String gpstime;
    //平台锁车状态
    private Integer lockStauts;
    //mic
    private Integer terminalMic;
    //摄像头
    private String terminalChannel;
    //车速
    private Integer speed;
    //剩余油量
    private Double resOil;
    //总里程
    private Double totalMilleage;
    //当日里程
    private Double todayMilleage;
    //tip车辆状态
    private String accStauts;
    //当前故障
    private String fault;
    //经度
    private Double lng;
    //纬度
    private Double lat;
    //有效时间
    private String gpstimeVaild;
    //有效经度
    private Double lngValid;
    //有效纬度
    private Double latValid;
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

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getCarStauts() {
        return carStauts;
    }

    public void setCarStauts(Integer carStauts) {
        this.carStauts = carStauts;
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

    public String getGpstime() {
        return gpstime;
    }

    public void setGpstime(String gpstime) {
        this.gpstime = gpstime;
    }

    public Integer getLockStauts() {
        return lockStauts;
    }

    public void setLockStauts(Integer lockStauts) {
        this.lockStauts = lockStauts;
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

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getGpstimeVaild() {
        return gpstimeVaild;
    }

    public void setGpstimeVaild(String gpstimeVaild) {
        this.gpstimeVaild = gpstimeVaild;
    }

    public Double getLngValid() {
        return lngValid;
    }

    public void setLngValid(Double lngValid) {
        this.lngValid = lngValid;
    }

    public Double getLatValid() {
        return latValid;
    }

    public void setLatValid(Double latValid) {
        this.latValid = latValid;
    }

    public String getTerminalChannel() {
        return terminalChannel;
    }

    public void setTerminalChannel(String terminalChannel) {
        this.terminalChannel = terminalChannel;
    }

    public Integer getTerminalMic() {
        return terminalMic;
    }

    public void setTerminalMic(Integer terminalMic) {
        this.terminalMic = terminalMic;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
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
