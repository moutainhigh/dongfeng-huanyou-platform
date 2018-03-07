package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class CarAssembleInfoDto {
	
	/**
	 * 安装单位ID(服务站ID)
	 */
	private BigInteger id;
	
	/**
	 * 服务站编码
	 */
	private String code;
	
	/**
	 * 服务站地址
	 */
	private String address;
	
	/**
	 * 经办人
	 */
	private String handler;
	
	/**
	 * 安装时间
	 */
	private BigInteger assembleTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public BigInteger getAssembleTime() {
		return assembleTime;
	}

	public void setAssembleTime(BigInteger assembleTime) {
		this.assembleTime = assembleTime;
	}
	
}
