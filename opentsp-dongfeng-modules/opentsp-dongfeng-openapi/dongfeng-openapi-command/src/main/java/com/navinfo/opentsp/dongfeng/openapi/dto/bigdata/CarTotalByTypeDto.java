package com.navinfo.opentsp.dongfeng.openapi.dto.bigdata;

/**
 * 
 * @author xltianc.zh
 */
public class CarTotalByTypeDto {
	
	private String carType;
	private Long carTotal;

	public String getCarType() {
		return carType;
	}

	public Long getCarTotal() {
		return carTotal;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public void setCarTotal(Long carTotal) {
		this.carTotal = carTotal;
	}

}
