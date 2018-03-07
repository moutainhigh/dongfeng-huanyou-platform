package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class CarTerminalInfoDto {
	
	/**
	 * 终端ID
	 */
	private BigInteger id;
	
	/**
	 * 终端类型
	 */
	private Integer type;
	
	/**
	 * 终端自编号
	 */
	private String tcode;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
}
