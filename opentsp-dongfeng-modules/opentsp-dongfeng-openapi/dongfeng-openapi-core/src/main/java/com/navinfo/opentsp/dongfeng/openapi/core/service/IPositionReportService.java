package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.dto.station.StationPositionReportInfo;

/**
 * 位置上报接口
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
public interface IPositionReportService {
    /**
     * 服务站位置上报
     *
     * @param info
     */
    HttpCommandResultWithData stationLocationReport(StationPositionReportInfo info);
}
