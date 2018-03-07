package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

import javax.validation.constraints.NotNull;

public class QueryBasicDataCommand extends BaseCommand<HttpCommandResultWithData>{

	private String carType;
	
	@NotNull(message = "serverVersion不能为空")
	private Integer serverVersion;
	
	private String engineType;
	
	private String battery;
	
	private String batteryBatch;
	
	private String autoFlag;
	
	private int instalType = 1;
	
	private int carTypeFlag = 1;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public Integer getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(Integer serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getBatteryBatch() {
		return batteryBatch;
	}

	public void setBatteryBatch(String batteryBatch) {
		this.batteryBatch = batteryBatch;
	}

	public String getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}

	public int getInstalType() {
		return instalType;
	}

	public void setInstalType(int instalType) {
		this.instalType = instalType;
	}

	public int getCarTypeFlag() {
		return carTypeFlag;
	}

	public void setCarTypeFlag(int carTypeFlag) {
		this.carTypeFlag = carTypeFlag;
	}
	
}
