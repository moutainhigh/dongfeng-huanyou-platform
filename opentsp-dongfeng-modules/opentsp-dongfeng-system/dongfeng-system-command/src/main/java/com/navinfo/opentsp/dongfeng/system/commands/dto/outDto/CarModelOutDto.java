package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class CarModelOutDto {
	
	private BigInteger dataId;
	
	private String dataValue;

	public BigInteger getDataId() {
		return dataId;
	}

	public void setDataId(BigInteger dataId) {
		this.dataId = dataId;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
