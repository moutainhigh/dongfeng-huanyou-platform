package com.navinfo.opentsp.dongfeng.common.util;

public enum TimePrecision {
	
	NULL("") , SECOND("SECOND") , MILLISECOND("MILLISECOND");
	
	private String precision;
	
	TimePrecision(String precision) {
		this.precision = precision;
	}
	
	public String getPrecision(){
		return this.precision;
	}
}
