package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryRemoteInfoDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryRemoteInfoPojo;

/**
 * 远程诊断
 *
 * @wenya
 * @create 2017-03-13 15:09
 **/
public class QueryRemoteInfoConverter {
    public static QueryRemoteInfoDto queryRemoteInfoConverter(QueryRemoteInfoPojo queryRemoteInfoPojo){
        QueryRemoteInfoDto queryRemoteInfoDto = new QueryRemoteInfoDto();
        if(null!=queryRemoteInfoPojo){
            try {
                CopyPropUtil.copyProp(queryRemoteInfoPojo,queryRemoteInfoDto);
                queryRemoteInfoDto.setId(queryRemoteInfoPojo.getCarId().longValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return queryRemoteInfoDto;
    }
}
