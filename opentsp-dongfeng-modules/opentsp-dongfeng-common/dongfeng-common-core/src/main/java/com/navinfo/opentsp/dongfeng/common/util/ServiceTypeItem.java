package com.navinfo.opentsp.dongfeng.common.util;

public class ServiceTypeItem {
    private String code;
    private String name;
    private int checkedCount; //用户选择参数
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCheckedCount() {
		return checkedCount;
	}
	public void setCheckedCount(int checkedCount) {
		this.checkedCount = checkedCount;
	}
    
}