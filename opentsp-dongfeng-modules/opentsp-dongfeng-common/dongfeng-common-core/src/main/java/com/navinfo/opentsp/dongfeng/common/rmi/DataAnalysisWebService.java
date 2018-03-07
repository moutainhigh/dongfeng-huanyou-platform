package com.navinfo.opentsp.dongfeng.common.rmi;


import com.lc.rp.webservice.service.CommonParameter;

import java.util.List;

public interface DataAnalysisWebService {
	
	public byte[] getGridCrossCounts(List<Long> terminalIds,
			List<Long> tileIds, long startDate, long endDate);
	
	public byte[] getVehiclePassTimesBytileId(List<Long> titlids, long startDate, long endDate);
	
	public byte[] getVehiclePassInArea(List<Integer> districtCodes, int type, long startDate, long endDate);
	
	public byte[] getVehiclePassTimesRecords(int districtCode, long startDate, long endDate);
	
	public byte[] getLastestVehiclePassInArea(List<Integer> districtCodes);
	
	public byte[] getMileageAndOilData(List<Long> terminalIds);
	
	public byte[] getMileageConsumptionRecords(List<Long> terminalIds, long startDate, long endDate,
			CommonParameter commonParameter);
	
	public byte[] getOvertimeParkRecords(List<Long> terminalID, List<Long> areaId, long startDate, long endDate,
			CommonParameter commonParameter);
	
	public byte[] delOvertimeParkRecords(Long accessTocken, String id, Long recordDate);
	
	public byte[] getFaultCodeRecords(List<Long> terminalIds, int spn, int fmi, long beginDate, long endDate,
			CommonParameter commonParameter);
	
	public byte[] getStagnationTimeoutRecords(List<Long> terminalIds, Long startDate, Long endDate,
			CommonParameter commonParameter);
	
	public byte[] StagnationTimeoutCancelOrNot(String id_, boolean isCancel, Long recordDate);

	byte[] calculateMileageConsumption(Long terminalId, Long startDate, Long endDate);

	public byte[] getTest(CommonParameter commonParameter);


}
