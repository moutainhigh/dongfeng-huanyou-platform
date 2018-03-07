package com.navinfo.opentsp.dongfeng.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.pojo.eletag.SyncServiceStationParam;

/**
 * 同步服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-26
 * @modify
 * @copyright Navi Tsp
 */
public interface ISyncService {
    /**
     * 同步同步服务站
     *
     * @param stationId    服务站ID
     * @param provinceCode 省编码
     * @param cityCode     市编码
     * @param operateType  操作类型
     */
    HttpCommandResultWithData syncStation(String stationId, String provinceCode, String cityCode, int operateType);

    /**
     * 异步同步服务站
     *
     * @param stationId
     * @param provinceCode
     * @param cityCode
     * @param operateType
     * @return
     */
    HttpCommandResultWithData asyncStation(String stationId, String provinceCode, String cityCode, int operateType);

    /**
     * 同步经销商
     *
     * @param tId          经销商ID
     * @param provinceCode 省编码
     * @param cityCode     市编码
     * @param operateType  操作类型
     */
    HttpCommandResultWithData syncDealer(String tId, String provinceCode, String cityCode, int operateType);
    /**
     * 同步经销商
     *
     * @param tId          经销商ID
     * @param provinceCode 省编码
     * @param cityCode     市编码
     * @param operateType  操作类型
     */
    HttpCommandResultWithData asyncDealer(String tId, String provinceCode, String cityCode, int operateType);
    /**
     * 同步服务站信息到电子标签系统
     *
     * @param param 同步参数
     */
    HttpCommandResultWithData syncStationToSign(SyncServiceStationParam param) throws JsonProcessingException;

    /**
     * 同步经销商二级网点地址到电子标签系统
     *
     * @param param 同步参数
     */
    HttpCommandResultWithData syncDealerToSign(Object param);
}
