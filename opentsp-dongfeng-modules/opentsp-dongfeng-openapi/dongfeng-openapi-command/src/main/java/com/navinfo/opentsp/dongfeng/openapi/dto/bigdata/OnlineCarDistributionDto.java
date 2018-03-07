package com.navinfo.opentsp.dongfeng.openapi.dto.bigdata;

/**
 * 
 * @author xltianc.zh
 */
public class OnlineCarDistributionDto {

	private String district; // 行政区域
	private Long counts;// 车辆数

	public String getDistrict() {
		return district;
	}

	public Long getCounts() {
		return counts;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setCounts(Long counts) {
		this.counts = counts;
	}

}
