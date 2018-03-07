package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import java.math.BigInteger;

public class BusinessIndto {
	
	/**
	 * 所属客户ID
	 */
	private BigInteger id;
	
	/**
	 * 企业许可证号
	 */
	private String businessCode;
	
	/**
	 * 所属客户名称
	 */
	private String businessName;
	
	/**
	 * 区域ID
	 */
	private String addressCode;
	
	/**
	 * 区域名称
	 */
	private String addressName;
	
	/**
	 * 所属省市
	 */
	private String addressCommon;
	
	/**
	 * 联系方式
	 */
	private String linkTelpone;
	
	private String govName;
	
	/**
	 * 证件类型
	 */
	private Short ctype;
	
	/**
	 * 驾照号
	 */
	private String drivingLicense;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressCommon() {
		return addressCommon;
	}

	public void setAddressCommon(String addressCommon) {
		this.addressCommon = addressCommon;
	}

	public String getLinkTelpone() {
		return linkTelpone;
	}

	public void setLinkTelpone(String linkTelpone) {
		this.linkTelpone = linkTelpone;
	}

	public String getGovName() {
		return govName;
	}

	public void setGovName(String govName) {
		this.govName = govName;
	}

	public Short getCtype() {
		return ctype;
	}

	public void setCtype(Short ctype) {
		this.ctype = ctype;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getCardNo() {
		
		switch (ctype) {
		case 1: {
			return businessCode;
		}
		case 2: {
			return drivingLicense;
		}
		default : {
			return null;
		}
		}
	}

}
