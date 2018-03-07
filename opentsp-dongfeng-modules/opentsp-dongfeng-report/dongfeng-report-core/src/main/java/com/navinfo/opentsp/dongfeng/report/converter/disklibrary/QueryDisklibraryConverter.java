package com.navinfo.opentsp.dongfeng.report.converter.disklibrary;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.dto.disklibrary.DisklibraryDto;
import com.navinfo.opentsp.dongfeng.report.pojo.disklibrary.DisklibraryPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */
public class QueryDisklibraryConverter {
    public static List<DisklibraryDto> convert(List<DisklibraryPojo> datas) {
        List<DisklibraryDto> results = new ArrayList<DisklibraryDto>();
        if (ListUtil.isEmpty(datas)) {
            return results;
        }
        for (DisklibraryPojo pojo : datas) {
            DisklibraryDto dto = new DisklibraryDto();
            try {
                CopyPropUtil.copyProp(pojo, dto);
                //车辆位置经度纬度处理
                if(StringUtil.isNotEmpty(pojo.getCarAddress())&&pojo.getCarAddress().contains(",")){
                    String[] lnOrLa = pojo.getCarAddress().split(",");
                    dto.setCarLng(Double.valueOf(lnOrLa[0]));
                    dto.setCarLat(Double.valueOf(lnOrLa[1]));
                }else{
                    dto.setCarLng(0D);
                    dto.setCarLat(0D);
                }
                //时间处理
                dto.setScanTime(DateUtil.timeStr(new SimpleDateFormat("yyyyMMddHHmmss").parse(pojo.getScanTime()).getTime() / 1000));
                //有效标示
                if(pojo.getInvaFlag().toString().equals("1")){
                    dto.setInvaFlag("有效");
                }else{
                    dto.setInvaFlag("无效");
                }
                results.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}