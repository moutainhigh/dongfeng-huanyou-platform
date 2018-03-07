package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import java.math.BigInteger;

public class QueryCarIndto {
	
	private Long id;
	
	/**
	 * 车辆颜色
	 */
	private Integer color;
	/**
	 * 底盘号
	 */
	private String chassisNum;
    
    private BigInteger terminalId;
	
	/**
	 * 车牌号
	 */
	private String carCph;
    
    /**
     * 车辆类型
     */
    private String carType;
    /**
	 * 首次上线时间
	 */
	private String nettingDate;
	
	/**
	 * 注册时间
	 */
	private String registerDate;
	
	/**
	 * 结构号
	 */
	private String structureNum;
	
	/**
	 * 邮箱容量
	 */
	private String oilCapacity;
	
	/**
	 * 出库时间
	 */
	private BigInteger removeTime;
	
	/**
	 * 电池类型
	 */
	private Integer batteryType;
	
	/**
	 * 电池批次
	 */
	private Integer batteryBatch;
	
	/**
	 * 
	 */
	private BigInteger payType;
	
	/**
	 * 录入方式
	 */
	private int autoFlag = 1;
	
	/**
	 * 安装单位名称
	 */
	private String stationName;
	
	/**
	 * 安装类型
	 */
	private Integer instalType;
	
	/**
	 * 下线时间
	 */
	private String offlineTime;
	
	/**
	 * 安装时间
	 */
	private String assembleTime;
	
	/**
	 * STD销售状态
	 */
	private Integer stdStatus;
	
	/**
	 * AAK销售状态
	 */
	private Integer aakStatus;
	
	/**
	 * 正确的下线时间
	 */
	private String online;
	
	/**
	 * 后装安装时间
	 */
	private String carDate;

	/**
	 * 工厂代码
	 */
	private String vfactory;


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

	public String getCarCph() {
		return carCph;
	}

	public void setCarCph(String carCph) {
		this.carCph = carCph;
	}

	public String getNettingDate() {
		return nettingDate;
	}

	public void setNettingDate(String nettingDate) {
		this.nettingDate = nettingDate;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getStructureNum() {
		return structureNum;
	}

	public void setStructureNum(String structureNum) {
		this.structureNum = structureNum;
	}

	public String getOilCapacity() {
		return oilCapacity;
	}

	public void setOilCapacity(String oilCapacity) {
		this.oilCapacity = oilCapacity;
	}

	public BigInteger getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(BigInteger removeTime) {
		this.removeTime = removeTime;
	}

	public Integer getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(Integer batteryType) {
		this.batteryType = batteryType;
	}

	public Integer getBatteryBatch() {
		return batteryBatch;
	}

	public void setBatteryBatch(Integer batteryBatch) {
		this.batteryBatch = batteryBatch;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public BigInteger getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(BigInteger terminalId) {
		this.terminalId = terminalId;
	}

	public BigInteger getPayType() {
		return payType;
	}

	public void setPayType(BigInteger payType) {
		this.payType = payType;
	}

	public int getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(int autoFlag) {
		this.autoFlag = autoFlag;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getInstalType() {
		return instalType;
	}

	public void setInstalType(Integer instalType) {
		this.instalType = instalType;
	}

	public String getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getAssembleTime() {
		return assembleTime;
	}

	public void setAssembleTime(String assembleTime) {
		this.assembleTime = assembleTime;
	}

	public Integer getStdStatus() {
		return stdStatus;
	}

	public void setStdStatus(Integer stdStatus) {
		this.stdStatus = stdStatus;
	}

	public Integer getAakStatus() {
		return aakStatus;
	}

	public void setAakStatus(Integer aakStatus) {
		this.aakStatus = aakStatus;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getCarDate() {
		return carDate;
	}

	public void setCarDate(String carDate) {
		this.carDate = carDate;
	}

	public String getVfactory() {
		return vfactory;
	}

	public void setVfactory(String vfactory) {
		this.vfactory = vfactory;
	}
}
