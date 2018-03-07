package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

public class CheckTerminalOutDto {
	
	private AccDataOutDto accData;
	
	private CanDataOutDto canData;
	
	private TerminalDataOutDto terminalData;
	
	private GpsDataOutDto gpsData;

	public AccDataOutDto getAccData() {
		return accData;
	}

	public void setAccData(AccDataOutDto accData) {
		this.accData = accData;
	}

	public CanDataOutDto getCanData() {
		return canData;
	}

	public void setCanData(CanDataOutDto canData) {
		this.canData = canData;
	}

	public TerminalDataOutDto getTerminalData() {
		return terminalData;
	}

	public void setTerminalData(TerminalDataOutDto terminalData) {
		this.terminalData = terminalData;
	}

	public GpsDataOutDto getGpsData() {
		return gpsData;
	}

	public void setGpsData(GpsDataOutDto gpsData) {
		this.gpsData = gpsData;
	}
	
}
