package com.navinfo.opentsp.dongfeng.common.util;

public enum FormatStyle {
	
	DATE("yyyy-MM-dd") , TIME("yyyy-MM-dd HH:mm:ss");
	private String format;
	
	FormatStyle(String format) {
		this.format = format;
	}
	
	public String getFormat() {
		return this.format;
	}
}
