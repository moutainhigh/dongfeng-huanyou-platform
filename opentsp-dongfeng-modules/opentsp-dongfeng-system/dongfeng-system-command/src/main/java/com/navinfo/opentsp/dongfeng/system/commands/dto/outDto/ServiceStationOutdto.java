package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class ServiceStationOutdto {
	
	/**
	 * 服务站ID
	 */
	private BigInteger id;
	
	/**
	 * 服务站名称
	 */
	private String stationName;
	
	/**
	 * 服务站地址
	 */
	private String stationAddress;
	
	/**
	 * 服务站编码
	 */
	private String serviceCode;
	
	
	/**
	 * 经销商ID
	 */
	private BigInteger teamId;
	
	/**
	 * 经销商名称
	 */
	private String tname;
	
	private Integer cityCode;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationAddress() {
		return stationAddress;
	}

	public void setStationAddress(String stationAddress) {
		this.stationAddress = stationAddress;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public BigInteger getTeamId() {
		return teamId;
	}

	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	
}
