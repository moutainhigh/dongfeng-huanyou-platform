package com.navinfo.dongfeng.terminal.comm.model.system.vehicle;

import java.io.Serializable;

public class ReportWsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8004608940331412546L;
	
	private long carId;
	private long terminalId;
	private String carCph;
	private long cphColor;
	private long teamId;
	private String teamName;
	
	public long getCarId() {
		return carId;
	}
	public void setCarId(long carId) {
		this.carId = carId;
	}
	public long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(long terminalId) {
		this.terminalId = terminalId;
	}
	public String getCarCph() {
		return carCph;
	}
	public void setCarCph(String carCph) {
		this.carCph = carCph;
	}
	public long getCphColor() {
		return cphColor;
	}
	public void setCphColor(long cphColor) {
		this.cphColor = cphColor;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
