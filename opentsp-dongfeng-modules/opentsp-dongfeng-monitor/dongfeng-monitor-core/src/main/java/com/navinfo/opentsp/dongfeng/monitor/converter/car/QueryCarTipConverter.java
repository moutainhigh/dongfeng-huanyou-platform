package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryCarTipDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarTipPojo;

/**
 * POJO>>DTO
 * 车辆tip
 * @wenya
 * @create 2017-03-09 14:59
 **/
public class QueryCarTipConverter {
    public static QueryCarTipDto CarTipConverter (QueryCarTipPojo carTipPojo){
        QueryCarTipDto carTipDto = new QueryCarTipDto();
        if(null!=carTipPojo){
            carTipDto.setChassisNum(carTipPojo.getChassisNum());
            carTipDto.setId(carTipPojo.getId().longValue());
            carTipDto.setDealer(carTipPojo.getDealer());
            carTipDto.setPlateNum(carTipPojo.getPlateNum());
            carTipDto.setCustomer(carTipPojo.getCustomer());
            carTipDto.setAccStauts(carTipPojo.getAccStauts());
            carTipDto.setOnline(carTipPojo.getCarStauts()==null?0:(carTipPojo.getCarStauts().intValue()&1));
            carTipDto.setCarStauts(carTipPojo.getCarStauts()==null?0:carTipPojo.getCarStauts());
            carTipDto.setFault(carTipPojo.getFault());
            if(carTipPojo.getGpstime()!=null){
                carTipDto.setGpstime(DateUtil.timeStr(carTipPojo.getGpstime()));
            }
            if(carTipPojo.getGpstimeVaild()!=null){
                carTipDto.setGpstimeVaild(DateUtil.timeStr(carTipPojo.getGpstimeVaild()));
            }
            if(carTipPojo.getLat()!=null){
                carTipDto.setLat(NumberFormatUtil.getDoubleValueData(carTipPojo.getLat()*0.000001,6));
            }else{
                carTipDto.setLat(0.0);
            }
            if(carTipPojo.getLng()!=null){
                carTipDto.setLng(NumberFormatUtil.getDoubleValueData(carTipPojo.getLng()*0.000001,6));
            }else{
                carTipDto.setLng(0.0);
            }
            if(carTipPojo.getLatValid()!=null){
                carTipDto.setLatValid(NumberFormatUtil.getDoubleValueData(carTipPojo.getLatValid()*0.000001,6));
            }else{
                carTipDto.setLatValid(0.0);
            }
            if(carTipPojo.getLngVaild()!=null){
                carTipDto.setLngValid(NumberFormatUtil.getDoubleValueData(carTipPojo.getLngVaild()*0.000001,6));
            }else{
                carTipDto.setLngValid(0.0);
            }
            if(carTipPojo.getTodayMilleage()!=null){
                carTipDto.setTodayMilleage(carTipPojo.getTodayMilleage());
            }
            carTipDto.setLockStauts(carTipPojo.getLockStauts().intValue());
            carTipDto.setTerminalMic(carTipPojo.getTerminalMic()==null?0:carTipPojo.getTerminalMic());
            carTipDto.setTerminalChannel(carTipPojo.getTerminalChannel());
            carTipDto.setResOil(carTipPojo.getResOil()==null?0.0:carTipPojo.getResOil());
            carTipDto.setSpeed(carTipPojo.getSpeed());
            carTipDto.setAccStauts(carTipPojo.getAccStauts());
            carTipDto.setStdSalesStatus(carTipPojo.getStdSalesStatus());
            carTipDto.setAakSalesStatus(carTipPojo.getAakSalesStatus());
            carTipDto.setTotalMilleage(carTipPojo.getTotalMilleage()==null?0.0:carTipPojo.getTotalMilleage());
            carTipDto.setDirection(carTipPojo.getDirection());
            carTipDto.setFuel(carTipPojo.getFuel());
            carTipDto.setCarLoad(StringUtil.nvlDef(carTipPojo.getCarLoad()));
        }
        return carTipDto;
    }
}
