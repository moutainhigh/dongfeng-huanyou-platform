package com.navinfo.opentsp.dongfeng.report.converter.salestate;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.report.dto.salestate.QuerySaleStateDto;
import com.navinfo.opentsp.dongfeng.report.pojo.salestate.QuerySaleStatePojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-31
 * @modify
 * @copyright Navi Tsp
 */

public class QuerySaleStateConverter {
    public static List<QuerySaleStateDto> convert(List<QuerySaleStatePojo> datas) {
        List<QuerySaleStateDto> results = new ArrayList<>();
        if (ListUtil.isEmpty(datas)){
            return  results;
        }
        for (QuerySaleStatePojo statePojo : datas) {
            QuerySaleStateDto dto = new QuerySaleStateDto();
            try {
                CopyPropUtil.copyProp(statePojo,dto);
                //经度纬度处理
                if(null!=statePojo.getNettingLog()){
                    dto.setLog(NumberFormatUtil.getLatitudeOrLongitude(statePojo.getNettingLog().intValue()));
                }else{
                    dto.setLog(0);
                }
                if(null!=statePojo.getNettingLat()){
                    dto.setLat(NumberFormatUtil.getLatitudeOrLongitude(statePojo.getNettingLat().intValue()));
                }else{
                    dto.setLat(0);
                }
                //日期处理
                if(null!=statePojo.getSaleDate()){
                    dto.setSaleDate(DateUtil.timeStr(statePojo.getSaleDate().longValue()));
                }
                if(null!=statePojo.getRegisterTime()){
                    dto.setRegisterTime(DateUtil.timeStr(statePojo.getRegisterTime().longValue()));
                }
                if(null!=statePojo.getNettingTime()){
                    dto.setNettingTime(DateUtil.timeStr(statePojo.getNettingTime().longValue()));
                }
                results.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  results;
    }
}