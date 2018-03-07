package com.navinfo.opentsp.dongfeng.report.pojo.market;


import com.lc.core.protocol.common.LCLocationData;

import java.math.BigInteger;

public class NsoodleReportPojo {
	
	private BigInteger id;//主键
	private BigInteger carId;//车辆Id
	private String chassisNum;//底盘号
	private String carCph;//车牌号
	private Integer carType;//车辆类型
	private String carModelCode;//车型码
	private BigInteger teamId;//经销商Id
	private String teamDealerCode;//经销商编码
	private String teamName;//经销商名称
	private String secdNameLatlon;//二级经销商信息
	private Long warehouseLog;//入库位置-经度
	private Long warehouseLat;//入库位置-纬度
	private Long warehouseTime;//入库时间
	private Long outOfLibraryLog;//出库位置-经度
	private Long outOfLibraryLat;//出库位置-纬度
	private Long outOfLibraryTime;//出库时间
	private Long createTime;//创建时间
	private String carTypeValue;
	private BigInteger commId;
	
	private LCLocationData.LocationData data;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getCarId() {
		return carId;
	}

	public void setCarId(BigInteger carId) {
		this.carId = carId;
	}

	public String getCarModelCode() {
		return carModelCode;
	}

	public void setCarModelCode(String carModelCode) {
		this.carModelCode = carModelCode;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public String getTeamDealerCode() {
		return teamDealerCode;
	}

	public void setTeamDealerCode(String teamDealerCode) {
		this.teamDealerCode = teamDealerCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSecdNameLatlon() {
		return secdNameLatlon;
	}

	public void setSecdNameLatlon(String secdNameLatlon) {
		this.secdNameLatlon = secdNameLatlon;
	}

	public Long getWarehouseLog() {
		return warehouseLog;
	}

	public void setWarehouseLog(Long warehouseLog) {
		this.warehouseLog = warehouseLog;
	}

	public Long getWarehouseLat() {
		return warehouseLat;
	}

	public void setWarehouseLat(Long warehouseLat) {
		this.warehouseLat = warehouseLat;
	}

	public Long getWarehouseTime() {
		return warehouseTime;
	}

	public void setWarehouseTime(Long warehouseTime) {
		this.warehouseTime = warehouseTime;
	}

	public Long getOutOfLibraryLog() {
		return outOfLibraryLog;
	}

	public void setOutOfLibraryLog(Long outOfLibraryLog) {
		this.outOfLibraryLog = outOfLibraryLog;
	}

	public Long getOutOfLibraryLat() {
		return outOfLibraryLat;
	}

	public void setOutOfLibraryLat(Long outOfLibraryLat) {
		this.outOfLibraryLat = outOfLibraryLat;
	}

	public Long getOutOfLibraryTime() {
		return outOfLibraryTime;
	}

	public void setOutOfLibraryTime(Long outOfLibraryTime) {
		this.outOfLibraryTime = outOfLibraryTime;
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

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCarTypeValue() {
		return carTypeValue;
	}

	public void setCarTypeValue(String carTypeValue) {
		this.carTypeValue = carTypeValue;
	}

	public BigInteger getCommId() {
		return commId;
	}

	public void setCommId(BigInteger commId) {
		this.commId = commId;
	}

	public LCLocationData.LocationData getData() {
		return data;
	}

	public void setData(LCLocationData.LocationData data) {
		this.data = data;
	}
	
}
