package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.dataaccess.terminal.LCMileageAndOilDataRes;
import com.navinfo.opentsp.common.messaging.ResultCode;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.CloudUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryLocInfosCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryLocInfosConverter;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryLocInfosDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryLocInfosPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryLocInfosService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/17
 */
@Service
public class QueryLocInfosServiceImpl extends BaseService implements IQueryLocInfosService {

    @Resource
    private GpsInfoCache gpsInfoCache;
    @Resource
    private GpsCache gpsCache;

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Override
    public HttpCommandResultWithData queryLocInfos(QueryLocInfosCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("teamIds", command.getTeamIds());
        params.put("carIds",command.getCarIds());
        params.put("chassisNum",command.getChassisNum());
        PagingInfo<QueryLocInfosPojo> page = dao.sqlPagelFind("selectCarsByTeamIdsOrCarIds", params,Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QueryLocInfosPojo.class);

        PagingInfo<QueryLocInfosDto> pagingInfo = new PagingInfo<QueryLocInfosDto>();
        pagingInfo.setPage_total(page.getPage_total());
        pagingInfo.setTotal(page.getTotal());
        List<QueryLocInfosPojo> list = page.getList();



        List<Long> commIds = new ArrayList<Long>();
        for (QueryLocInfosPojo pojo:list) {
            commIds.add(Long.parseLong(pojo.getCommId()));
            LCLocationData.LocationData locationData = gpsCache.getLastGps(pojo.getCommId()+"");
            if(locationData != null){
                pojo.setDirection(locationData.getDirection());
                pojo.setSpeed(locationData.getSpeed());
                pojo.setAccStauts(CloudUtil.getAccStatus(locationData.getStatus()));
            }else{
                pojo.setDirection(-1);
                pojo.setSpeed(0);
                pojo.setAccStauts("");
            }

            LCLocationData.LocationData locationDataVlid = gpsCache.getLastGpsVlid(pojo.getCommId()+"");
            if(locationDataVlid != null){
                pojo.setLastGpsLat(locationDataVlid.getLatitude()* 0.000001);
                pojo.setLastGpsLng(locationDataVlid.getLongitude()* 0.000001);
                pojo.setLastGpsTime(DateUtil.timeStr(locationDataVlid.getGpsDate()));
            }else{
                pojo.setLastGpsLat(0d);
                pojo.setLastGpsLng(0d);
                pojo.setLastGpsTime("");
            }

            GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(pojo.getCommId()+"");
            if(gpsInfo != null){
                pojo.setTotalMilleage(gpsInfo.getTempMileage().floatValue());
                pojo.setOilwear(gpsInfo.getTempOil().floatValue());
            }else{
                pojo.setTotalMilleage(0f);
                pojo.setOilwear(0f);
            }

        }

        Map<Long,LCMileageAndOilDataRes.MileageAndOilData> map = getMileageAndOilData(commIds);

        for (QueryLocInfosPojo pojo:list) {
            LCMileageAndOilDataRes.MileageAndOilData mo = map.get(Long.parseLong(pojo.getCommId()));
            if(mo != null){
                pojo.setTodayMilleage((float) mo.getMileage());
                pojo.setOil(mo.getOil());
            }else{
                pojo.setTodayMilleage(0f);
                pojo.setOil(0f);
            }
        }

        pagingInfo.setList(QueryLocInfosConverter.convertToDtoList(page.getList()));
        result.setResultCode(ResultCode.OK.code());
        result.setData(pagingInfo);
        return result;
    }

    public Map<Long,LCMileageAndOilDataRes.MileageAndOilData> getMileageAndOilData(List<Long> commIds){
        Map<Long,LCMileageAndOilDataRes.MileageAndOilData> map = new HashMap<Long,LCMileageAndOilDataRes.MileageAndOilData>();
        try {
            DataAnalysisWebService dataAnalysisWebService = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
            byte[] data = dataAnalysisWebService.getMileageAndOilData(commIds);
            if (data != null) {
                LCMileageAndOilDataRes.MileageAndOilDataRes builder = LCMileageAndOilDataRes.MileageAndOilDataRes
                        .parseFrom(data);
                List<LCMileageAndOilDataRes.MileageAndOilData> list = builder.getRecordsList();
                for (LCMileageAndOilDataRes.MileageAndOilData mo:list) {
                    map.put(mo.getTerminalId(),mo);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return map;
    }

}
