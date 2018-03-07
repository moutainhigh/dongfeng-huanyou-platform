package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 故障汇总报表
 * 
 * @Date 2017/3/27
 */
@SuppressWarnings("rawtypes")
public class QueryFaultSummaryCommand extends BaseReportCommand {

	private String chassisNum; // 底盘号
	private String terId;// 终端Id
	private String carTerId;// 防拆盒Id
	private String plateNum;// 车牌号
	private String companyName;// 经销商
	private String carOwner;// 客户
	private String carType;// 车型
	private String engineType;// 发动机类型
	private String spn;// SPN
	private String fmi;// FMI
	private Long beginTime;// 开始时间
	private Long endTime;// 结束时间
	private String dateStr;// 时间
	private String structureNum; // 结构号
	
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

	public String getTerId() {
		return terId;
	}

	public void setTerId(String terId) {
		this.terId = terId;
	}

	public String getCarTerId() {
		return carTerId;
	}

	public void setCarTerId(String carTerId) {
		this.carTerId = carTerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
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

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getStructureNum() {
		return structureNum;
	}

	public void setStructureNum(String structureNum) {
		this.structureNum = structureNum;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "QueryCarOperateCommand{}";
	}
}
