package com.navinfo.opentsp.dongfeng.report.converter.market;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryNotOnlineReportDTo;
import com.navinfo.opentsp.dongfeng.report.pojo.market.NotOnlineReportPojo;

import java.util.ArrayList;
import java.util.List;

public class NotOnlineReportPojoToDto {

	public static List<QueryNotOnlineReportDTo> convert(List<NotOnlineReportPojo> list) {
		List<QueryNotOnlineReportDTo> dToList = new ArrayList<QueryNotOnlineReportDTo>();
		for (NotOnlineReportPojo pojo : list) {
			QueryNotOnlineReportDTo dTo = new QueryNotOnlineReportDTo();
			dTo.setChassis_no(pojo.getChassis_no());
			dTo.setWork_center(pojo.getWork_center());
			dTo.setMaterials_id(pojo.getMaterials_id());
			dTo.setMaterials_name(pojo.getMaterials_name());
			dTo.setPlan_time(pojo.getPlan_time());
			dTo.setInstock_time(pojo.getInstock_time());
			dTo.setQualified_time(pojo.getQualified_time());
			dTo.setRemoval_time(pojo.getRemoval_time());
			dTo.setStorage_location(pojo.getStorage_location());
			dTo.setSale_id(pojo.getSale_id());
			dTo.setXx_sj(pojo.getXx_sj());
			dTo.setZx_sj(pojo.getZx_sj());
			dTo.setNetting_time(pojo.getNetting_time());
			dTo.setNetting_log(pojo.getNetting_log());
			dTo.setNetting_lat(pojo.getNetting_lat());
			
			if (pojo.getCarId() != null) {
				LCLocationData.LocationData gpsdata = pojo.getGpsdata();
				if (gpsdata != null) {
					dTo.setLast_time(String.valueOf(gpsdata.getGpsDate()));
					if (gpsdata.getLatitude() != 0 && gpsdata.getLongitude() != 0) {
						dTo.setLast_location(String
								.valueOf(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude())) + ","
								+ String.valueOf(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude())));
					} else {
						if (pojo.getNetting_time() != null) {
							dTo.setLast_time(String.valueOf(pojo.getNetting_time()));
							dTo.setLast_location(String.valueOf(
									NumberFormatUtil.getLatitudeOrLongitude(Integer.valueOf(pojo.getNetting_lat())))
									+ "," + String.valueOf(NumberFormatUtil
											.getLatitudeOrLongitude(Integer.valueOf(pojo.getNetting_log()))));
						}
					}
				} else {
					if (pojo.getNetting_time() != null) {
						dTo.setLast_time(String.valueOf(pojo.getNetting_time()));
						dTo.setLast_location(String.valueOf(
								NumberFormatUtil.getLatitudeOrLongitude(Integer.valueOf(pojo.getNetting_lat()))) + ","
								+ String.valueOf(NumberFormatUtil
										.getLatitudeOrLongitude(Integer.valueOf(pojo.getNetting_log()))));
					}
				}
			}
			dToList.add(dTo);
		}
		return dToList;
	}
}
