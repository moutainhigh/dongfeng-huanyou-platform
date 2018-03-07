package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryLocInfosDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryLocInfosPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/17
 */
public class QueryLocInfosConverter {
    public static List<QueryLocInfosDto> convertToDtoList(List<QueryLocInfosPojo> entityList) {
        List<QueryLocInfosDto> result = new ArrayList<QueryLocInfosDto>();
        for (QueryLocInfosPojo entity : entityList) {
            QueryLocInfosDto dto = new QueryLocInfosDto();
            try {
                CopyPropUtil.copyProp(entity,dto);
                dto.setPlateNum(entity.getCarCph());
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.add(dto);
        }
        return result;
    }

}
