package com.navinfo.opentsp.dongfeng.openapi.dto.time;

public class SecondStationDto {

	// 区域Id
	private Long aeraId;
	// 进区域时间
	private Long enterTime;
	// 出区域时间
	private Long outTime;
	// 区域类型(11-一级服务站、12-二级服务站、21-一级经销商、22-二级经销商)
	private Integer aeraType;
	// 区域内停留时间
	private Long stayTime;

	public Long getAeraId() {
		return aeraId;
	}

	public void setAeraId(Long aeraId) {
		this.aeraId = aeraId;
	}

	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	public Long getOutTime() {
		return outTime;
	}

	public void setOutTime(Long outTime) {
		this.outTime = outTime;
	}

	public Integer getAeraType() {
		return aeraType;
	}

	public void setAeraType(Integer aeraType) {
		this.aeraType = aeraType;
	}

	public Long getStayTime() {
		return stayTime;
	}

	public void setStayTime(Long stayTime) {
		this.stayTime = stayTime;
	}

}
