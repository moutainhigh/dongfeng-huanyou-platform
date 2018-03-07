package com.navinfo.opentsp.dongfeng.monitor.converter.station;

import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.station.QueryStationDetailDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationDetailPojo;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */

public class QueryStationDetailConverter {
    /**
     * JPJO>>DTO
     * @param queryStationDetailPojo
     * @return
     */
    public static QueryStationDetailDto stationDetailConverter(QueryStationDetailPojo queryStationDetailPojo){
        QueryStationDetailDto queryStationDetailDto = new QueryStationDetailDto();
        if(null!=queryStationDetailPojo){
            queryStationDetailDto.setStationName(queryStationDetailPojo.getStationName());
            queryStationDetailDto.setLongitude(NumberFormatUtil.getLatitudeOrLongitude(queryStationDetailPojo.getLongitude().intValue()));
            queryStationDetailDto.setLatitude(NumberFormatUtil.getLatitudeOrLongitude(queryStationDetailPojo.getLatitude().intValue()));
            queryStationDetailDto.setServiceContent(queryStationDetailPojo.getServiceContent());
            queryStationDetailDto.setPartsContent(queryStationDetailPojo.getPartsContent());
            queryStationDetailDto.setPicture(queryStationDetailPojo.getPicture());
            queryStationDetailDto.setRadius(queryStationDetailPojo.getRadius());
            queryStationDetailDto.setServiceRadius(queryStationDetailPojo.getServiceRadius());
            queryStationDetailDto.setServiceManager(queryStationDetailPojo.getServiceManager());
            queryStationDetailDto.setPhone(queryStationDetailPojo.getPhone());
            queryStationDetailDto.setStarLever(queryStationDetailPojo.getStarLever());
            queryStationDetailDto.setCommentCounts(queryStationDetailPojo.getCommentCounts().intValue());
            queryStationDetailDto.setStarLeverCounts(queryStationDetailPojo.getStarLeverCounts().intValue());
            queryStationDetailDto.setAdderss(queryStationDetailPojo.getAdderss());
        }
        return queryStationDetailDto;
    }
}