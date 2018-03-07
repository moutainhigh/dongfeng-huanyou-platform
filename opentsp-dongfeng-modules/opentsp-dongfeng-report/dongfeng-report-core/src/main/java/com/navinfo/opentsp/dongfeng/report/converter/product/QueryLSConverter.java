package com.navinfo.opentsp.dongfeng.report.converter.product;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.report.dto.product.QueryLSDto;
import com.navinfo.opentsp.dongfeng.report.pojo.product.QueryLSPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @wenya
 * @create 2017-03-28 11:32
 **/
public class QueryLSConverter {
    /**
     * pojo转dto
     */
    public static QueryLSDto pojoToDto(QueryLSPojo pojo) {
        QueryLSDto dto = new QueryLSDto();
        if(null!=pojo){
            try {
                CopyPropUtil.copyProp(pojo,dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dto;
    }

    public static List<QueryLSDto> convertToDtoList(List<QueryLSPojo> list) {
        List<QueryLSDto> listDto = new ArrayList<QueryLSDto>();
        if (!list.isEmpty()) {
            for (QueryLSPojo pojo : list) {
                QueryLSDto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }
}
