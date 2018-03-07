package com.navinfo.opentsp.dongfeng.monitor.converter.station;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.station.QueryStationCommentsDto;
import com.navinfo.opentsp.dongfeng.monitor.dto.station.StationCommentsDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationCommentsPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.StationCommentsPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */

public class QueryStationCommentsConverter {
    /**
     * JPJO>>DTO
     * @param queryStationCommentsPojo
     * @return
     */
    public static QueryStationCommentsDto stationDetailConverter(QueryStationCommentsPojo queryStationCommentsPojo){
        QueryStationCommentsDto queryDto = new QueryStationCommentsDto();
        List<StationCommentsDto> dtoList = new ArrayList<StationCommentsDto>();
        List<StationCommentsPojo> queryStationCommentsList = queryStationCommentsPojo.getPagingInfo().getList();
        for (StationCommentsPojo entity : queryStationCommentsList) {
            StationCommentsDto dto = new StationCommentsDto();
            try {
                CopyPropUtil.copyProp(entity,dto);
                dto.setCommentTime(DateUtil.timeStr(entity.getCommentTime().intValue()));
                dtoList.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            CopyPropUtil.copyProp(queryStationCommentsPojo,queryDto);
            queryDto.getPagingInfo().setList(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryDto;
    }
}