package com.navinfo.opentsp.dongfeng.common.util;

public enum ColumnStyle {
	
	NULL("") , DATE("DATE");
	
	private String style;
	
	ColumnStyle(String style) {
		this.style = style;
	}
	
	public String getStyle() {
		return this.style;
	}
}