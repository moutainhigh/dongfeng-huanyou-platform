package com.navinfo.opentsp.dongfeng.monitor.converter.car;


import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryCarLocDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarLocPojo;

/**
 * @wenya
 * @create 2017-03-09 16:15
 **/
public class QueryCarLocConverter {
    public static QueryCarLocDto CarLocConverter (QueryCarLocPojo carLocPojo) {
        QueryCarLocDto carLocDto = new QueryCarLocDto();
        if (null != carLocPojo) {
            carLocDto.setId(carLocPojo.getId().longValue());
            carLocDto.setChassisNum(carLocPojo.getChassisNum());
            carLocDto.setPlateNum(carLocPojo.getPlateNum());
            carLocDto.setCarStauts(carLocPojo.getCarStauts());
            if(carLocPojo.getDirection()!=null){
                carLocDto.setDirection(carLocPojo.getDirection());
            }
            if (carLocPojo.getGpstime() != null) {
                carLocDto.setGpstime(DateUtil.timeStr(carLocPojo.getGpstime()));
            }
            if(carLocPojo.getLat()!=null){
                carLocDto.setLat(NumberFormatUtil.getDoubleValueData(carLocPojo.getLat() * 0.000001,6));
            }
            if(carLocPojo.getLng()!=null){
                carLocDto.setLng(NumberFormatUtil.getDoubleValueData(carLocPojo.getLng() * 0.000001,6));
            }
        }
        return carLocDto;
    }

}
