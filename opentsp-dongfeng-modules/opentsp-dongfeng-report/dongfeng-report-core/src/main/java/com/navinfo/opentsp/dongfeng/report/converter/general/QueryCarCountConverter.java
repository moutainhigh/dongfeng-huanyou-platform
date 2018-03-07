package com.navinfo.opentsp.dongfeng.report.converter.general;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryCarCountDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryCarCountPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @wenya
 * @create 2017-03-28 18:29
 **/
public class QueryCarCountConverter {
    /**
     * pojo转dto
     */
    public static QueryCarCountDto pojoToDto(QueryCarCountPojo pojo) {
        QueryCarCountDto dto = new QueryCarCountDto();
        if(null!=pojo){
            try {
                CopyPropUtil.copyProp(pojo,dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dto;
    }

    public static List<QueryCarCountDto> convertToDtoList(List<QueryCarCountPojo> list) {
        List<QueryCarCountDto> listDto = new ArrayList<QueryCarCountDto>();
        if (!list.isEmpty()) {
            for (QueryCarCountPojo pojo : list) {
                QueryCarCountDto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }
}
