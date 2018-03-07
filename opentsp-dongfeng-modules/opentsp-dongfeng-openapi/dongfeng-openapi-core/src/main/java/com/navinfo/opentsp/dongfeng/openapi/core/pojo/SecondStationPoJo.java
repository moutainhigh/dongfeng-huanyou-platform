package com.navinfo.opentsp.dongfeng.openapi.core.pojo;

import java.math.BigInteger;

public class SecondStationPoJo {

	private BigInteger aeraId;
	// 进区域时间
	private BigInteger enterTime;
	// 出区域时间
	private BigInteger outTime;
	// 区域类型(11-一级服务站、12-二级服务站、21-一级经销商、22-二级经销商)
	private Integer aeraType;
	// 区域内停留时间
	private BigInteger stayTime;

	public BigInteger getAeraId() {
		return aeraId;
	}

	public void setAeraId(BigInteger aeraId) {
		this.aeraId = aeraId;
	}

	public BigInteger getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(BigInteger enterTime) {
		this.enterTime = enterTime;
	}

	public BigInteger getOutTime() {
		return outTime;
	}

	public void setOutTime(BigInteger outTime) {
		this.outTime = outTime;
	}

	public Integer getAeraType() {
		return aeraType;
	}

	public void setAeraType(Integer aeraType) {
		this.aeraType = aeraType;
	}

	public BigInteger getStayTime() {
		return stayTime;
	}

	public void setStayTime(BigInteger stayTime) {
		this.stayTime = stayTime;
	}

}
