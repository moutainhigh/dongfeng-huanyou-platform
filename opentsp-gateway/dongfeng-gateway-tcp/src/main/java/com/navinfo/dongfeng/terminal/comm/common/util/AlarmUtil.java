package com.navinfo.dongfeng.terminal.comm.common.util;


import com.lc.core.protocol.common.LCLocationData;

/**
 * 位置数据转换报警类型
 *
 * @wenya
 * @create 2017-03-16 11:10
 **/
public class AlarmUtil {
    public static String changeAlarm(LCLocationData.LocationData gpsdata){
        String st="";
        if((gpsdata.getAlarm()& Math.round(Math.pow(2, 20))) > 0){
            st +="1"+",";   //1:出区域限速
        }
        if(gpsdata.getParkingAdditionList() != null && gpsdata.getParkingAdditionList().size()>0){
            for(LCLocationData.OvertimeParkingAddition key:gpsdata.getParkingAdditionList()){
                st +="2"+",";   //2:滞留超时
            }
        }
        if(gpsdata.getAdditionAlarm() !=null && gpsdata.getAdditionAlarm().size()>7){
            byte alarm7 = gpsdata.getAdditionAlarm().byteAt(6);
            byte alarm8 = gpsdata.getAdditionAlarm().byteAt(7);
            st += (alarm7 & Math.round(Math.pow(2, 7))) > 0 ? "3,":"";   //3:一体机拆出报警
//			st += (alarm8 & Math.round(Math.pow(2, 4))) > 0 ? "4,":"";
        }
        return st;
    }
}
