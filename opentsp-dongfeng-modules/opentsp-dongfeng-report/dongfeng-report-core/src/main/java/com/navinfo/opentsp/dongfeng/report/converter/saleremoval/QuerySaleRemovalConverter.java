package com.navinfo.opentsp.dongfeng.report.converter.saleremoval;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.report.dto.saleremoval.QuerySaleRemovalDto;
import com.navinfo.opentsp.dongfeng.report.pojo.saleremoval.QuerySaleRemovalPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-31
 * @modify
 * @copyright Navi Tsp
 */

public class QuerySaleRemovalConverter {
    public static List<QuerySaleRemovalDto> convert(List<QuerySaleRemovalPojo> datas) {
        List<QuerySaleRemovalDto> results = new ArrayList<QuerySaleRemovalDto>();
        if (ListUtil.isEmpty(datas)) {
            return results;
        }
        for (QuerySaleRemovalPojo removalPojo : datas) {
            QuerySaleRemovalDto dto = new QuerySaleRemovalDto();
            try {
                CopyPropUtil.copyProp(removalPojo, dto);
                //经度纬度处理
                if(null!=removalPojo.getNettingLog()){
                    dto.setLog(NumberFormatUtil.getLatitudeOrLongitude(removalPojo.getNettingLog().intValue()));
                }else{
                    dto.setLog(0);
                }
                if(null!=removalPojo.getNettingLat()){
                    dto.setLat(NumberFormatUtil.getLatitudeOrLongitude(removalPojo.getNettingLat().intValue()));
                }else{
                    dto.setLat(0);
                }
                //日期处理
                if (null != removalPojo.getSaleDate()) {
                    dto.setSaleDate(DateUtil.timeStr(removalPojo.getSaleDate().longValue()));
                }
                if (null != removalPojo.getRemovalTime()) {
                    dto.setRemovalTime(DateUtil.timeStr(removalPojo.getRemovalTime().longValue()));
                }
                if (null != removalPojo.getNettingTime()) {
                    dto.setNettingTime(DateUtil.timeStr(removalPojo.getNettingTime().longValue()));
                }
                results.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}