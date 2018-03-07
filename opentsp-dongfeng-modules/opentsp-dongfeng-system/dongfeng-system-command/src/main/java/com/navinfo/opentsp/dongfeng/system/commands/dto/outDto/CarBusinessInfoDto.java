package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class CarBusinessInfoDto {
	
	/**
	 * 客户ID
	 */
	private BigInteger id;
	
	/**
	 * 客户名称
	 */
	private String name;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
