package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.AuditStationPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IPositionReportService;
import com.navinfo.opentsp.dongfeng.openapi.dto.station.StationPositionReportInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class PositionReportService extends BaseService implements IPositionReportService {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @Transactional
    public HttpCommandResultWithData stationLocationReport(StationPositionReportInfo info) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        AuditStationPojo pojo = toAuditStationPojo(info);
        dao.executeUpdate("addStationAudit", pojo);
        return result;

    }

    private AuditStationPojo toAuditStationPojo(StationPositionReportInfo info) {
        AuditStationPojo pojo = new AuditStationPojo();
        pojo.setStationId(StringUtil.toBigInteger(info.getId()));
        pojo.setAddress(info.getAddress());
        pojo.setStationType(Integer.valueOf(info.getLv()));
        pojo.setLongitude(StringUtil.toBigInteger(info.getLon()));
        pojo.setLatitude(StringUtil.toBigInteger(info.getLat()));
        pojo.setAccountId(StringUtil.toBigInteger(info.getUserId()));
        pojo.setCreateTime(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
        return pojo;
    }
}
