package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

@SuppressWarnings("rawtypes")
public class QueryBreakDownRemindCommand extends BaseReportCommand {

	private String carId;// 车辆ID
	private String spn;// SPN
	private String fmi;// FMI
	private String occurDate;// 故障发生时间
	private String duration;// 故障持续时间
	private String warQueryType;// 故障查询类型(2:查看明细,3:明细中的查询)
	private String dateStr;// 时间
	private String chassisNum;// 底盘号

	private Long beginTime;// 开始时间
	private Long endTime;// 结束时间

	public String getCarId() {
		return carId;
	}

	public String getSpn() {
		return spn;
	}

	public String getFmi() {
		return fmi;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public String getDuration() {
		return duration;
	}

	public String getWarQueryType() {
		return warQueryType;
	}

	public String getDateStr() {
		return dateStr;
	}

	public String getChassisNum() {
		return chassisNum;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public void setSpn(String spn) {
		this.spn = spn;
	}

	public void setFmi(String fmi) {
		this.fmi = fmi;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setWarQueryType(String warQueryType) {
		this.warQueryType = warQueryType;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public void setChassisNum(String chassisNum) {
		this.chassisNum = chassisNum;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "QueryBreakDownRemindCommand{}";
	}

}
