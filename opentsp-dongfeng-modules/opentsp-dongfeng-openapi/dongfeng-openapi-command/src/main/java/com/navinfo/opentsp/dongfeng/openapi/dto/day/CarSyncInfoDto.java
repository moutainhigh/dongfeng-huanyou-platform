package com.navinfo.opentsp.dongfeng.openapi.dto.day;

/**
 * 车辆同步信息查询entity
 * 
 * @author xltianc.zh
 */
public class CarSyncInfoDto {

	private Long carId;

	private String stdSalDate;

	private String aakSalDate;

	private String syncDate;

	private String carTypeValue;

	private String businessName;

	private String sim;

	private Integer delflag;

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getStdSalDate() {
		return stdSalDate;
	}

	public void setStdSalDate(String stdSalDate) {
		this.stdSalDate = stdSalDate;
	}

	public String getAakSalDate() {
		return aakSalDate;
	}

	public void setAakSalDate(String aakSalDate) {
		this.aakSalDate = aakSalDate;
	}

	public String getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}

	public String getCarTypeValue() {
		return carTypeValue;
	}

	public void setCarTypeValue(String carTypeValue) {
		this.carTypeValue = carTypeValue;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

}
