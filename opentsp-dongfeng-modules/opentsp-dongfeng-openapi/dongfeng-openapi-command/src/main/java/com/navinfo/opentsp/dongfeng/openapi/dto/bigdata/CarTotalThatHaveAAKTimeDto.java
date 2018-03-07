package com.navinfo.opentsp.dongfeng.openapi.dto.bigdata;

/**
 * 
 * @author xltianc.zh
 */
public class CarTotalThatHaveAAKTimeDto {

	private Long total;
	private Long totalByYears;
	private Long totalByMonth;

	public Long getTotal() {
		return total;
	}

	public Long getTotalByYears() {
		return totalByYears;
	}

	public Long getTotalByMonth() {
		return totalByMonth;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setTotalByYears(Long totalByYears) {
		this.totalByYears = totalByYears;
	}

	public void setTotalByMonth(Long totalByMonth) {
		this.totalByMonth = totalByMonth;
	}

}
