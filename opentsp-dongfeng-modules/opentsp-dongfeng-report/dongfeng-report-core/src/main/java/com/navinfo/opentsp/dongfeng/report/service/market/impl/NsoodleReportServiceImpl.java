package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateFormat;
import com.navinfo.opentsp.dongfeng.common.util.DateFormat.Format;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryNsoodleReportCommand;
import com.navinfo.opentsp.dongfeng.report.converter.market.NsoodleReportToDto;
import com.navinfo.opentsp.dongfeng.report.pojo.market.NsoodleReportPojo;
import com.navinfo.opentsp.dongfeng.report.service.market.INsoodleReportService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NsoodleReportServiceImpl extends BaseService implements INsoodleReportService {

	@Autowired
	GpsCache gpsCache;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HttpCommandResultWithData queryReportData(QueryNsoodleReportCommand command, boolean isExport) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("accountName", command.getUserInfor().getUsername());
			paramMap.put("accountType", command.getUserInfor().getType());
			paramMap.put("accountId", command.getUserInfor().getUserId());
			paramMap.put("teamDealerCode", command.getTeamDealerCode());
			paramMap.put("teamName", command.getTeamName());
			if (null != command.getOutOfLibraryTimeStr() && !command.getOutOfLibraryTimeStr().trim().equals("")) {
				String startDateStr = command.getOutOfLibraryTimeStr().substring(0, 10) + " 00:00:00";
				String endDateStr = command.getOutOfLibraryTimeStr().substring(13, 23) + " 23:59:59";
				SimpleDateFormat sdf = DateFormat.format(Format.YY_YY_MM_DD_HH_MM_SS);
				paramMap.put("startDate", sdf.parse(startDateStr).getTime() / 1000);
				paramMap.put("endDate", sdf.parse(endDateStr).getTime() / 1000);
			}
			PagingInfo<NsoodleReportPojo> datas = new PagingInfo<NsoodleReportPojo>();
			if (!isExport) {
				datas = dao.sqlPagelFind("queryNsoodleReport", paramMap, NumberUtils.toInt(command.getPage_number()),
						NumberUtils.toInt(command.getPage_size()), NsoodleReportPojo.class);
			} else {
				List<NsoodleReportPojo> lists = new ArrayList<NsoodleReportPojo>();
				lists = dao.sqlFind("queryNsoodleReport", paramMap, NsoodleReportPojo.class);
				datas.setList(lists);
				datas.setTotal(lists.size());
			}
			Map<String,LCLocationData.LocationData> lastGpsMap = gpsCache.getAllLastGpsMap();
			for (NsoodleReportPojo pojo : datas.getList()) {
				LCLocationData.LocationData gpsdata = lastGpsMap.get(pojo.getCommId());
				pojo.setData(gpsdata);
			}
			PagingInfo page = new PagingInfo();
			page.setList(NsoodleReportToDto.convert(datas.getList()));
			page.setPage_total(datas.getPage_total());
			result.setData(page);
		} catch (Exception e) {
			logger.error("Error : " , e);
		}
		return result;
	}
}
