package com.navinfo.opentsp.dongfeng.monitor.converter.risk;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.QueryRisksDto;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.QueryRisksPoint;
import com.navinfo.opentsp.dongfeng.monitor.pojo.risk.QueryRisksPojo;

import java.util.*;

/**
 * 防控区域
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/4/11
 */
public class QueryRisksConverter {
    public static Collection<QueryRisksDto> toQueryRisksDto(List<QueryRisksPojo> list) {
        if (StringUtil.isEmpty(list)) {
            return null;
        }
        Map<Long,QueryRisksDto> map = new HashMap<Long,QueryRisksDto>();
        for (QueryRisksPojo pojo:list) {
            QueryRisksDto dto = map.get(pojo.getRegionId().longValue());
            if(dto == null){
                dto = new QueryRisksDto();
                dto.setRegionId(pojo.getRegionId());
                dto.setRegionType(pojo.getRegionType());
                dto.setRegionName(pojo.getRegionName());
                dto.setRadius(pojo.getRadius());
                dto.setPoints(new ArrayList<QueryRisksPoint>());
                map.put(pojo.getRegionId().longValue(),dto);
            }
            dto.getPoints().add(new QueryRisksPoint(pojo.getLat(), pojo.getLng()));
        }

        return map.values();
    }
}
