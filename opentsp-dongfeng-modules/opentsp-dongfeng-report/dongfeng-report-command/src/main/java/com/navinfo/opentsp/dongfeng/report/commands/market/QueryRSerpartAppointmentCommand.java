package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("rawtypes")
public class QueryRSerpartAppointmentCommand extends BaseReportCommand {

	private String chassisNum;// 底盘号
	private String terId;// 终端Id
	private String carTerId;// 终端Id
	private String carCph;// 车牌号
	private String companyName;// 经销商
	private String carOwner;// 客户
	private Integer carType;// 车型
	private Integer engineType;// 车型

	private Integer appoType;// 预约类型
	private String stationName;// 服务站名称
	private Integer cityCode;// 服务站地址（市）
	private Integer povinceCode;// 服务站地址（省）
	private Long bDate;// 开始时间
	private Long eDate;// 结束时间
	private Long bsDate;// 预约开始时间
	private Long esDate;// 预约结束时间
	private String dateStr;// 时间（预约时间）
	private String appointmentNum;// 预约流水号

	public String getCarTerId() {
		return carTerId;
	}

	public void setCarTerId(String carTerId) {
		this.carTerId = setStringValue(carTerId);
	}

	public Integer getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = setIntegerValue(engineType);
	}

	public String getChassisNum() {
		return chassisNum;
	}

	public void setChassisNum(String chassisNum) {
		this.chassisNum = setStringValue(chassisNum);
	}

	public String getTerId() {
		return terId;
	}

	public void setTerId(String terId) {
		this.terId = setStringValue(terId);
	}

	public String getCarCph() {
		return carCph;
	}

	public void setCarCph(String carCph) {
		this.carCph = setStringValue(carCph);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = setStringValue(companyName);
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = setStringValue(carOwner);
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = setIntegerValue(carType);
	}

	public Integer getAppoType() {
		return appoType;
	}

	public void setAppoType(String appoType) {
		this.appoType = setIntegerValue(appoType);
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = setStringValue(stationName);
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		if (cityCode != null && !cityCode.trim().equals("null")) {
			this.cityCode = setIntegerValue(cityCode);
		} else {
			this.cityCode = null;
		}
	}

	public Integer getPovinceCode() {
		return povinceCode;
	}

	public void setPovinceCode(Integer povinceCode) {
		this.povinceCode = povinceCode;
	}

	public Long getBDate() {
		return bDate;
	}

	public void setBDate(String bDate) {
		this.bDate = setTimeValue(bDate);
	}

	public Long getEDate() {
		return eDate;
	}

	public void setEDate(String eDate) {
		this.eDate = setTimeValue(eDate);
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Long getbDate() {
		return bDate;
	}

	public void setbDate(Long bDate) {
		this.bDate = bDate;
	}

	public Long geteDate() {
		return eDate;
	}

	public void seteDate(Long eDate) {
		this.eDate = eDate;
	}

	public String getAppointmentNum() {
		return appointmentNum;
	}

	public void setAppointmentNum(String appointmentNum) {
		this.appointmentNum = appointmentNum;
	}

	public Long getBsDate() {
		return bsDate;
	}

	public void setBsDate(Long bsDate) {
		this.bsDate = bsDate;
	}

	public Long getEsDate() {
		return esDate;
	}

	public void setEsDate(Long esDate) {
		this.esDate = esDate;
	}

	private String setStringValue(String value) {
		return value.trim().equals("") ? null : value;
	}

	private Integer setIntegerValue(String value) {
		Integer temp = -1;
		try {
			temp = Integer.parseInt(value);
		} catch (Exception e) {
			temp = -1;
		}
		if (temp.intValue() == -1) {
			return null;
		}
		return temp;
	}

	private Long setTimeValue(String value) {// 秒级别
		Long date = 0L;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(value).getTime() / 1000;
		} catch (ParseException e) {

		}
		return date;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "QueryArmatureOrdeCommand{" + "chassisNum='" + chassisNum + '\'' + ", appointmentNum='" + appointmentNum
				+ '\'' + ", terId='" + terId + '\'' + ", carTerId='" + carTerId + '\'' + ", carCph='" + carCph + '\''
				+ ", companyName='" + companyName + '\'' + ", carOwner='" + carOwner + '\'' + ", carType='" + carType
				+ '\'' + ", engineType='" + engineType + '\'' + ", appoType='" + appoType + '\'' + ", stationName='"
				+ stationName + '\'' + ", cityCode='" + cityCode + '\'' + ", dateStr='" + dateStr + '\'' + '}';

	}

}
