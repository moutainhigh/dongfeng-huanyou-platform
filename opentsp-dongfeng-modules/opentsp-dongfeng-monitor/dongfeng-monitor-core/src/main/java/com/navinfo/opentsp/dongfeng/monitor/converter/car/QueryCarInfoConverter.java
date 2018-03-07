package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryCarInfoDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarInfoPojo;

/**
 * 车辆详情
 *
 * @wenya
 * @create 2017-03-14 11:02
 **/
public class QueryCarInfoConverter {
    public static QueryCarInfoDto queryCarInfoConverter(QueryCarInfoPojo queryCarInfoPojo){
        QueryCarInfoDto queryCarInfoDto = new QueryCarInfoDto();
        if(null!=queryCarInfoPojo){
            try {
                CopyPropUtil.copyProp(queryCarInfoPojo,queryCarInfoDto);

                queryCarInfoDto.setFkCommNum(queryCarInfoPojo.getFkCommNum().toString());

                if(queryCarInfoPojo.getCarStauts()==null){
                    queryCarInfoDto.setCarStauts(0);
                }else{
                    queryCarInfoDto.setCarStauts(queryCarInfoPojo.getCarStauts().intValue()&1);
                }
                if(queryCarInfoPojo.getMaintainTimeL()!=null&&queryCarInfoPojo.getMaintainTimeL().longValue()!=0){
                    queryCarInfoDto.setMaintainTime(DateUtil.timeStr(queryCarInfoPojo.getMaintainTimeL().longValue()));
                }
                if(queryCarInfoPojo.getRemovalTimeL()!=null&&queryCarInfoPojo.getRemovalTimeL().longValue()!=0){
                    queryCarInfoDto.setRemovalTime(DateUtil.timeStr2(queryCarInfoPojo.getRemovalTimeL().longValue()));
                }

                if(queryCarInfoPojo.getGpstimeVaildL()!=null&&queryCarInfoPojo.getGpstimeVaildL().longValue()!=0){
                    queryCarInfoDto.setGpstimeVaild(DateUtil.timeStr(queryCarInfoPojo.getGpstimeVaildL()));
                }
                if(queryCarInfoPojo.getStdSalesStatus() != null){
                    queryCarInfoDto.setStdSalesStatus(queryCarInfoPojo.getStdSalesStatus());
                }

                if(queryCarInfoPojo.getStdSalesTimeL()!=null&&queryCarInfoPojo.getStdSalesTimeL().longValue()!=0){
                    queryCarInfoDto.setStdSalesTime(DateUtil.timeStr2(queryCarInfoPojo.getStdSalesTimeL().longValue()));
                }

                if(queryCarInfoPojo.getAakSalesStatus() != null){
                    queryCarInfoDto.setAakSalesStatus(queryCarInfoPojo.getAakSalesStatus());
                }

                if(queryCarInfoPojo.getAakSalesTimeL()!=null&&queryCarInfoPojo.getAakSalesTimeL().longValue()!=0){
                    queryCarInfoDto.setAakSalesTime(DateUtil.timeStr2(queryCarInfoPojo.getAakSalesTimeL().longValue()));
                }

                if(queryCarInfoPojo.getLatValidI()!=null){
                    queryCarInfoDto.setLatValid(NumberFormatUtil.getDoubleValueData(queryCarInfoPojo.getLatValidI()*0.000001,6));
                }else{
                    queryCarInfoDto.setLatValid(0.0);
                }
                if(queryCarInfoPojo.getLngVaildI()!=null){
                    queryCarInfoDto.setLngValid(NumberFormatUtil.getDoubleValueData(queryCarInfoPojo.getLngVaildI()*0.000001,6));
                }else{
                    queryCarInfoDto.setLngValid(0.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return queryCarInfoDto;
    }
}
