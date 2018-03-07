package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import java.math.BigInteger;

public class BasicDataIndto {
	
	private BigInteger id;
	
	private String dataCode;
	
	private String dataValue;
	
	private Integer dataType;
	
	private Integer parentCode;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getParentCode() {
		return parentCode;
	}

	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}
	
}
