package com.navinfo.opentsp.dongfeng.report.converter.station;

import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.dto.station.QueryStationLoadDto;
import com.navinfo.opentsp.dongfeng.report.pojo.station.StationLoadPojo;

import java.util.ArrayList;
import java.util.List;


public class StationLoadToDto {

    public static List<QueryStationLoadDto> convert(List<StationLoadPojo> datas) {
        List<QueryStationLoadDto> results = new ArrayList();
        if (ListUtil.isEmpty(datas)){
            return  results;
        }

        for (StationLoadPojo station : datas) {
            QueryStationLoadDto dto = new QueryStationLoadDto();
            dto.setStationId(StringUtil.nvl(station.getStationId()));
            dto.setName(station.getName());
            dto.setAddress(station.getAddress());
            dto.setManager(station.getManager());
            dto.setPhone(station.getPhone());
            dto.setReviewsLevel(StringUtil.nvlDef(station.getReviewsLevel()));
            dto.setServiceLevel(StringUtil.nvlDef(station.getServiceLevel()));
            if (!StringUtil.isEmpty(station.getStationEnable())) {
                dto.setStationEnable(Constant.StationEnable.getValue(station.getStationEnable()));
            }
            dto.setActualTrains(StringUtil.nvlDef(station.getActualTrains()));
            results.add(dto);
        }
        return  results;
    }
}
