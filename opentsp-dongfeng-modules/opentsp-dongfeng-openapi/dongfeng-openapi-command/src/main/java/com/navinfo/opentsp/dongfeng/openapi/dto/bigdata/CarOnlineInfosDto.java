package com.navinfo.opentsp.dongfeng.openapi.dto.bigdata;

/**
 * 
 * @author xltianc.zh
 */
public class CarOnlineInfosDto {

	private String time;
	private Long currentCounts;
	private Long yesterdayCounts;

	public String getTime() {
		return time;
	}

	public Long getCurrentCounts() {
		return currentCounts;
	}

	public Long getYesterdayCounts() {
		return yesterdayCounts;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setCurrentCounts(Long currentCounts) {
		this.currentCounts = currentCounts;
	}

	public void setYesterdayCounts(Long yesterdayCounts) {
		this.yesterdayCounts = yesterdayCounts;
	}

}
