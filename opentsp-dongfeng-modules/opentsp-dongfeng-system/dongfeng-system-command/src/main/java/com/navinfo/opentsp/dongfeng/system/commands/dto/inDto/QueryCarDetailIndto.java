package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

public class QueryCarDetailIndto {
	
	/**
	 * 与车辆ID相同
	 */
	private long id;
	
	/**
	 * 发动机类型
	 */
	private String engineType;
	
	/**
	 * 发动机号
	 */
	private String engineNumber;
	
	/**
	 * 发动机型号
	 */
	private String engineTypeRear;

	/**
	 * 动力类型
	 */
	private String fuel;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getEngineTypeRear() {
		return engineTypeRear;
	}

	public void setEngineTypeRear(String engineTypeRear) {
		this.engineTypeRear = engineTypeRear;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
}
