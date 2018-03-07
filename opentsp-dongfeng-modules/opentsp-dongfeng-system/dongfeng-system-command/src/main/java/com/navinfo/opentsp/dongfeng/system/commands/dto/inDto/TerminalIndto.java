package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import java.math.BigInteger;

public class TerminalIndto {
	
	private java.math.BigInteger id;
	
	private String sim;
	
	private String teleNumber;
	
	private Integer tstyle;
	
	private BigInteger type_id;
	
	private String type_name;
	
	private String tCode;
	
	/**
	 * 经销商ID
	 */
	private BigInteger dealerId;

	public java.math.BigInteger getId() {
		return id;
	}

	public void setId(java.math.BigInteger id) {
		this.id = id;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getTeleNumber() {
		return teleNumber;
	}

	public void setTeleNumber(String teleNumber) {
		this.teleNumber = teleNumber;
	}

	public Integer getTstyle() {
		return tstyle;
	}

	public void setTstyle(Integer tstyle) {
		this.tstyle = tstyle;
	}
	

	public BigInteger getType_id() {
		return type_id;
	}

	public void setType_id(BigInteger type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String gettCode() {
		return tCode;
	}

	public void settCode(String tCode) {
		this.tCode = tCode;
	}

	public BigInteger getDealerId() {
		return dealerId;
	}

	public void setDealerId(BigInteger dealerId) {
		this.dealerId = dealerId;
	}
	
}
