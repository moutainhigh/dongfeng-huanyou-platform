package com.navinfo.opentsp.dongfeng.common.dto;

/**
 * 类的简单元数据信息
 * @author yangml
 *
 */
public class EntityMetadata {
	
	/**
	 * 类的成员变量名称
	 */
	private String fieldName;
	
	/**
	 * 成员变量的描述
	 */
	private String fieldDescript;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDescript() {
		return fieldDescript;
	}

	public void setFieldDescript(String fieldDescript) {
		this.fieldDescript = fieldDescript;
	}
	
}
