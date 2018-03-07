package com.navinfo.opentsp.dongfeng.common.validation.group;

public enum ParameterType {
	
	BEAN(0) , LIST(1);
	
	private int type;
	
	ParameterType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
