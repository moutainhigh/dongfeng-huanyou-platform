package com.navinfo.opentsp.dongfeng.openapi.core.pojo;

import java.math.BigInteger;

/**
 * 
 * @author xltianc.zh
 *
 */
public class F9SimCodePojo {

	private String terSim; // 终端sim卡
	private String terCode; // 终端ID
	private String terType; // 终端类型
	private BigInteger commId;
	private String engineType;

	public String getTerSim() {
		return terSim;
	}

	public String getTerCode() {
		return terCode;
	}

	public String getTerType() {
		return terType;
	}

	public BigInteger getCommId() {
		return commId;
	}

	public void setTerSim(String terSim) {
		this.terSim = terSim;
	}

	public void setTerCode(String terCode) {
		this.terCode = terCode;
	}

	public void setTerType(String terType) {
		this.terType = terType;
	}

	public void setCommId(BigInteger commId) {
		this.commId = commId;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

}
