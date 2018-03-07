package com.navinfo.opentsp.dongfeng.report.converter.market;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryNsoodleReportDTo;
import com.navinfo.opentsp.dongfeng.report.pojo.market.NsoodleReportPojo;

import java.util.ArrayList;
import java.util.List;

public class NsoodleReportToDto {

	public static List<QueryNsoodleReportDTo> convert(List<NsoodleReportPojo> list) {
		List<QueryNsoodleReportDTo> nsoodleReportDTos = new ArrayList<QueryNsoodleReportDTo>();
		for (NsoodleReportPojo reportPojo : list) {
			QueryNsoodleReportDTo dTo = new QueryNsoodleReportDTo();
			dTo.setId(reportPojo.getId());
			dTo.setCarId(reportPojo.getCarId());
			dTo.setChassisNum(reportPojo.getChassisNum());
			dTo.setCarCph(reportPojo.getCarCph());
			dTo.setCarType(reportPojo.getCarType());
			dTo.setCarModelCode(reportPojo.getCarModelCode());
			dTo.setTeamId(reportPojo.getTeamId());
			dTo.setTeamDealerCode(reportPojo.getTeamDealerCode());
			dTo.setTeamName(reportPojo.getTeamName());
			dTo.setSecdNameLatlon(reportPojo.getSecdNameLatlon());
			dTo.setWarehouseLog(reportPojo.getWarehouseLog());
			dTo.setWarehouseLat(reportPojo.getWarehouseLat());
			dTo.setWarehouseTime(reportPojo.getWarehouseTime());
			dTo.setOutOfLibraryLog(reportPojo.getOutOfLibraryLog());
			dTo.setOutOfLibraryLat(reportPojo.getOutOfLibraryLat());
			dTo.setOutOfLibraryTime(reportPojo.getOutOfLibraryTime());
			dTo.setCreateTime(reportPojo.getCreateTime());
			dTo.setCarTypeValue(reportPojo.getCarTypeValue());

			if (reportPojo.getWarehouseTime() != null) {
				dTo.setWarehouseTimeStr(DateUtils.formatDate(reportPojo.getWarehouseTime() * 1000));
			}
			if (reportPojo.getOutOfLibraryTime() != null) {
				dTo.setOutOfLibraryTimeStr(DateUtils.formatDate(reportPojo.getOutOfLibraryTime() * 1000));
			}
			LCLocationData.LocationData gpsdata = reportPojo.getData();
			if (gpsdata != null) {
				dTo.setLat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
				dTo.setLog(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
				dTo.setLastLoction(dTo.getLog() + "," + dTo.getLat());// 导出报表使用
			}
			nsoodleReportDTos.add(dTo);
		}
		return nsoodleReportDTos;
	}

}
