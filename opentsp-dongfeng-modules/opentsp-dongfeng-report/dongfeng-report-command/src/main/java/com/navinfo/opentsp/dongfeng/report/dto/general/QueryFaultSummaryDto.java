package com.navinfo.opentsp.dongfeng.report.dto.general;

import com.navinfo.opentsp.dongfeng.common.dto.BaseDto;

import java.io.Serializable;

public class QueryFaultSummaryDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 7083343868261360392L;
	// 车辆主键
	private Long id;
	// 底盘号
	private String chassisNum;
	// 车牌号
	private String plateNum;
	// 北斗一体机ID
	private String bdTerCode;
	// FK控制器ID
	private String fkTerCode;
	// 所属经销商
	private String tName;
	// 所属客户
	private String businessName;
	// 车辆型号
	private String carModel;
	// 发动机型号
	private String engineNumber;
	// 发动机类型
	private String engineType;
	// 车架号
	private String structureNum;
	// 故障类型
	private String breakdownDis;
	// SPN
	private String spn;
	// FMI
	private String fmi;
	// 故障发生时间
	private String occurDate;
	// 持续时长
	private String duration;
	// 起始位置
	private String bLoction;
	// 结束位置
	private String eLoction;

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

	public String getBdTerCode() {
		return bdTerCode;
	}

	public void setBdTerCode(String bdTerCode) {
		this.bdTerCode = bdTerCode;
	}

	public String getFkTerCode() {
		return fkTerCode;
	}

	public void setFkTerCode(String fkTerCode) {
		this.fkTerCode = fkTerCode;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getStructureNum() {
		return structureNum;
	}

	public void setStructureNum(String structureNum) {
		this.structureNum = structureNum;
	}

	public String getBreakdownDis() {
		return breakdownDis;
	}

	public void setBreakdownDis(String breakdownDis) {
		this.breakdownDis = breakdownDis;
	}

	public String getSpn() {
		return spn;
	}

	public void setSpn(String spn) {
		this.spn = spn;
	}

	public String getFmi() {
		return fmi;
	}

	public void setFmi(String fmi) {
		this.fmi = fmi;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getbLoction() {
		return bLoction;
	}

	public void setbLoction(String bLoction) {
		this.bLoction = bLoction;
	}

	public String geteLoction() {
		return eLoction;
	}

	public void seteLoction(String eLoction) {
		this.eLoction = eLoction;
	}
}
