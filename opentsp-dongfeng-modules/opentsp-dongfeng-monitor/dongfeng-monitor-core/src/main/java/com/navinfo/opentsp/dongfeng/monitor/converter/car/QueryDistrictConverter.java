package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryDistrictDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryDistrictPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 所属销售区域弹窗
 *
 * @wenya
 * @create 2017-03-24 17:53
 **/
public class QueryDistrictConverter {
        /**
         * pojo转dto
         */
        public static QueryDistrictDto pojoToDto(QueryDistrictPojo pojo) {
            QueryDistrictDto dto = new QueryDistrictDto();
            dto.setId(pojo.getId().longValue());
            dto.setDistrictName(pojo.getDistrictName());
            dto.setPdistrictName(pojo.getPdistrictName());
            dto.setLinkMan(pojo.getLinkMan());
            dto.setPid(pojo.getPid().longValue());
            return dto;
        }

        public static List<QueryDistrictDto> convertToDtoList(List<QueryDistrictPojo> list) {
            List<QueryDistrictDto> listDto = new ArrayList<QueryDistrictDto>();
            if (!list.isEmpty()) {
                for (QueryDistrictPojo pojo : list) {
                    QueryDistrictDto dto = pojoToDto(pojo);
                    listDto.add(dto);
                }
            }
            return listDto;
        }
}
