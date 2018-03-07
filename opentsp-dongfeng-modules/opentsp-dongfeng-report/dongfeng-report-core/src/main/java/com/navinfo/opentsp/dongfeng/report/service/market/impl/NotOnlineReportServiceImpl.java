package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCLocationData.LocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryNotOnlineReportCommand;
import com.navinfo.opentsp.dongfeng.report.converter.market.NotOnlineReportPojoToDto;
import com.navinfo.opentsp.dongfeng.report.pojo.market.NotOnlineReportPojo;
import com.navinfo.opentsp.dongfeng.report.service.market.INotOnlineReportService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotOnlineReportServiceImpl extends BaseService implements INotOnlineReportService {

	protected static final Logger logger = LoggerFactory.getLogger(NotOnlineReportServiceImpl.class);

	@Autowired
	GpsCache gpsCache;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HttpCommandResultWithData queryReportData(QueryNotOnlineReportCommand command, boolean isExport) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountType", command.getUserInfor().getType());
		paramMap.put("accountName", command.getUserInfor().getUsername());
		paramMap.put("accountId", command.getUserInfor().getUserId());
		paramMap.put("chassis_no", command.getChassis_no());
		paramMap.put("storage_location", command.getStorage_location());
		paramMap.put("flag", command.getFlag());
		PagingInfo<NotOnlineReportPojo> datas = new PagingInfo<NotOnlineReportPojo>();
		if (isExport) {
			List<NotOnlineReportPojo> list = dao.sqlFind("queryWsxReport", paramMap, NotOnlineReportPojo.class);
			datas.setList(list);
			datas.setTotal(list.size());
		} else {
			datas = dao.sqlPagelFind("queryWsxReport", paramMap, NumberUtils.toInt(command.getPage_number()),
					NumberUtils.toInt(command.getPage_size()), NotOnlineReportPojo.class);
		}
		Map<String,LCLocationData.LocationData> lastGpsMap = gpsCache.getAllLastGpsMap();
		for (NotOnlineReportPojo pojo : datas.getList()) {
			LocationData gpsdata = lastGpsMap.get(pojo.getCommId());
			pojo.setGpsdata(gpsdata);
		}

		PagingInfo page = new PagingInfo();
		page.setList(NotOnlineReportPojoToDto.convert(datas.getList()));
		page.setPage_total(datas.getPage_total());
		result.setData(page);
		return result;
	}

}
