package com.navinfo.opentsp.dongfeng.report.dto.market;

import com.navinfo.opentsp.dongfeng.common.dto.BaseDto;

import java.io.Serializable;

/**
 * 服务站服务配件预约报表实体类 // *
 * 
 * @author xltianc.zh
 */
public class QueryRSerpartAppointmentDTo extends BaseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8880525954537762673L;

	private Long appointmentId; // 预约id
	private String carId;// 车辆id
	private String chassisNum;// 底盘号c
	private String carCph;// 车牌号c
	private String terId; // 终端IDr
	private String carTerId; // 终端IDr
	private String companyName;// 所属经销商t
	private String carOwner; // 所属客户（企业，车主）d
	private String carType; // 车辆型号c
	private String engineNumber; // 发动机型号d

	private String appoPhone;// 预约联系方式
	private Long stationId;// 服务站id
	private String appoStation;// 预约服务站
	private String appoTime;// 预约时间
	private String appoName;// 预约人
	private String appoService;// 预约服务
	private String appoParts;// 预约配件
	private String appoTool;// 预约提升工具
	private String appoComment;// 预约备注
	private String commTime;// 点评时间
	private String commContent;// 点评内容
	private String commTechLevel;// 技术质量星级
	private String commServiceLevel;// 服务质量星级
	private String commTotalLevel;// 总体评价星级
	private int appointmentType;// 预约类型

	private String inTime;// 入站时间-------位置云再点评信息推送的时候会传入，但是位置云及寰游都未存储
	private String outTime;// 出站时间-------位置云协议中无此数据
	// 预约提交时间
	private String appoSubmitTime;

	// 预约流水号
	private String appointmentNum;
	// 预约状态 0:预约待确认，1:已确认未服务，2:已取消，3:正在服务,4:完成服务待评价,5:完成评价,6:已过期
	private String appointmentStatus;

	public String getAppoTool() {
		return appoTool;
	}

	public void setAppoTool(String appoTool) {
		this.appoTool = appoTool;
	}

	public int getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(int appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getCarTerId() {
		return carTerId;
	}

	public void setCarTerId(String carTerId) {
		this.carTerId = carTerId;
	}

	public String getAppoName() {
		return appoName;
	}

	public void setAppoName(String appoName) {
		this.appoName = appoName;
	}

	public String getAppoSubmitTime() {
		return appoSubmitTime;
	}

	public void setAppoSubmitTime(String appoSubmitTime) {
		this.appoSubmitTime = appoSubmitTime;
	}

	public String getAppoComment() {
		return appoComment;
	}

	public void setAppoComment(String appoComment) {
		this.appoComment = appoComment;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
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

	public String getTerId() {
		return terId;
	}

	public void setTerId(String terId) {
		this.terId = terId;
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

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getAppoPhone() {
		return appoPhone;
	}

	public void setAppoPhone(String appoPhone) {
		this.appoPhone = appoPhone;
	}

	public String getAppoStation() {
		return appoStation;
	}

	public void setAppoStation(String appoStation) {
		this.appoStation = appoStation;
	}

	public String getAppoTime() {
		return appoTime;
	}

	public void setAppoTime(String appoTime) {
		this.appoTime = appoTime;
	}

	public String getAppoService() {
		return appoService;
	}

	public void setAppoService(String appoService) {
		this.appoService = appoService;
	}

	public String getAppoParts() {
		return appoParts;
	}

	public void setAppoParts(String appoParts) {
		this.appoParts = appoParts;
	}

	public String getCommTime() {
		return commTime;
	}

	public void setCommTime(String commTime) {
		this.commTime = commTime;
	}

	public String getCommContent() {
		return commContent;
	}

	public void setCommContent(String commContent) {
		this.commContent = commContent;
	}

	public String getCommTechLevel() {
		return commTechLevel;
	}

	public void setCommTechLevel(String commTechLevel) {
		this.commTechLevel = commTechLevel;
	}

	public String getCommServiceLevel() {
		return commServiceLevel;
	}

	public void setCommServiceLevel(String commServiceLevel) {
		this.commServiceLevel = commServiceLevel;
	}

	public String getCommTotalLevel() {
		return commTotalLevel;
	}

	public void setCommTotalLevel(String commTotalLevel) {
		this.commTotalLevel = commTotalLevel;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getAppointmentNum() {
		return appointmentNum;
	}

	public void setAppointmentNum(String appointmentNum) {
		this.appointmentNum = appointmentNum;
	}

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

}
