package com.navinfo.opentsp.dongfeng.report.service.saleremoval.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.constant.QueryConstants;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.saleremoval.QuerySaleRemovalCommand;
import com.navinfo.opentsp.dongfeng.report.converter.saleremoval.QuerySaleRemovalConverter;
import com.navinfo.opentsp.dongfeng.report.dto.saleremoval.QuerySaleRemovalDto;
import com.navinfo.opentsp.dongfeng.report.pojo.saleremoval.QuerySaleRemovalPojo;
import com.navinfo.opentsp.dongfeng.report.service.saleremoval.IQuerySaleRemovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QuerySaleRemovalServiceImpl extends BaseService implements IQuerySaleRemovalService {

    @Autowired
    private GpsCache gpsCache;

    @Override
    public HttpCommandResultWithData querySaleRemoval(QuerySaleRemovalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String check = checkTimeValid(command);
        if (StringUtil.isNotEmpty(check)) {
            result.setResultCode(ReturnCode.SERVER_ERROR.code());
            result.setMessage(check);
            return result;
        }
        //组装条件
        Map<String, Object> conMap = setConditon(command);
        PagingInfo<QuerySaleRemovalPojo> datas = dao.sqlPagelFind("querySaleRemovalReport", conMap, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QuerySaleRemovalPojo.class);
        //处理首次末次时间及位置
        List<QuerySaleRemovalDto> list = QuerySaleRemovalConverter.convert(datas.getList());
        for (QuerySaleRemovalDto removalDto : list) {
            String commId = "";
            BigInteger bdCom = removalDto.getBdCommunicationId();
            BigInteger fkCom = removalDto.getFkCommunicationId();
            if (null == bdCom && null == fkCom) {
                continue;
            } else {
                commId = ((null != fkCom && fkCom.intValue() != 0) ? fkCom.toString() : bdCom.toString());
            }
            if (StringUtil.isEmpty(commId) || commId.equals("0")) {
                commId = removalDto.getBdCommunicationId().toString();
            }
            LCLocationData.LocationData gpsdata = gpsCache.getLastGpsVlid(commId);
            if (null != gpsdata) {
                removalDto.setElat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                removalDto.setElog(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
                removalDto.setLastTime(DateUtil.timeStr(gpsdata.getGpsDate()));
            } else {
                removalDto.setElat(0);
                removalDto.setElog(0);
            }
        }
        PagingInfo page = new PagingInfo();
        page.setList(list);
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        return result;
    }

    @Override
    public List<QuerySaleRemovalPojo> querySaleRemovalForExport(QuerySaleRemovalCommand command) {
        //组装条件
        Map<String, Object> conMap = setConditon(command);
        PagingInfo<QuerySaleRemovalPojo> datas = dao.sqlPagelFind("querySaleRemovalReport", conMap, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QuerySaleRemovalPojo.class);
        //处理首次末次时间及位置
        List<QuerySaleRemovalPojo> list = setLocation(datas.getList());
        return list;
    }

    @Override
    public List<QuerySaleRemovalPojo> queryAllSaleRemovalForExport(QuerySaleRemovalCommand command) {
        //组装条件
        Map<String, Object> conMap = setConditon(command);
        List<QuerySaleRemovalPojo> datas = (List<QuerySaleRemovalPojo>) dao.sqlFind("querySaleRemovalReport", conMap, QuerySaleRemovalPojo.class);
        //处理首次末次时间及位置
        List<QuerySaleRemovalPojo> list = setLocation(datas);
        return list;
    }

    @Override
    public String checkTimeValid(QuerySaleRemovalCommand command) {
        String result = "";
        if (StringUtil.isNotEmpty(command.getBeginRemovalTime()) && StringUtil.isNotEmpty(command.getEndRemovalTime())) {
            long beginRemovalTime = DateUtil.strTimeChangeLong(command.getBeginRemovalTime() + DateUtil.start);
            long endRemovalTime = DateUtil.strTimeChangeLong(command.getEndRemovalTime() + DateUtil.end);
            if (beginRemovalTime > endRemovalTime) {
                result = "出库开始时间大于结束时间";
                return result;
            }
        }
        if (StringUtil.isNotEmpty(command.getBeginOfflineTime()) && StringUtil.isNotEmpty(command.getEndOfflineTime())) {

            long beginOfflineTime = DateUtil.strTimeChangeLong(command.getBeginOfflineTime() + DateUtil.start);
            long endOfflineTime = DateUtil.strTimeChangeLong(command.getEndOfflineTime() + DateUtil.end);
            if (beginOfflineTime > endOfflineTime) {
                result = "下线开始时间大于结束时间";
                return result;
            }
        }
        return result;
    }

    /**
     * 查询条件组装
     *
     * @param command
     * @return
     */
    public Map<String, Object> setConditon(QuerySaleRemovalCommand command) {
        //组装条件
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("accountId", new BigInteger(command.getUserInfor().getUserId().toString()));//用户ID
        conMap.put("accountType", command.getUserInfor().getType());
        if (StringUtil.isNotEmpty(command.getChassisNum())) {//底盘号
            conMap.put("chassisNum", command.getChassisNum());
        }
        if (StringUtil.isNotEmpty(command.getCarOwner())) {//所属客户
            conMap.put("carOwner", command.getCarOwner());
        }
        if (StringUtil.isNotEmpty(command.getCarType()) && !command.getCarType().equals(QueryConstants.CONDITION_ALL)) {//车辆型号
            conMap.put("carType", command.getCarType());
        }
        if (StringUtil.isNotEmpty(command.getCarCph())) {//车牌号
            conMap.put("carCph", command.getCarCph());
        }
        if (StringUtil.isNotEmpty(command.getTeamName())) {//经销商
            conMap.put("teamName", command.getTeamName());
        }
        if (StringUtil.isNotEmpty(command.getBeginRemovalTime())) {//出库时间-开始
            conMap.put("beginRemovalTime", DateUtil.strTimeChangeLong(command.getBeginRemovalTime() + DateUtil.start));
        }
        if (StringUtil.isNotEmpty(command.getEndRemovalTime())) {//出库时间-结束
            conMap.put("endRemovalTime", DateUtil.strTimeChangeLong(command.getEndRemovalTime() + DateUtil.end));
        }
        if (StringUtil.isNotEmpty(command.getTerminalFKCode())) {//防拆盒
            conMap.put("terminalFKCode", command.getTerminalFKCode());
        }
        if (StringUtil.isNotEmpty(command.getEngType()) && !command.getEngType().equals(QueryConstants.CONDITION_ALL)) {//发动机类型
            conMap.put("engType", command.getEngType());
        }
        if (StringUtil.isNotEmpty(command.getBeginOfflineTime())) {//下线时间-开始
            conMap.put("beginOfflineTime", DateUtil.strTimeChangeLong(command.getBeginOfflineTime() + DateUtil.start));
        }
        if (StringUtil.isNotEmpty(command.getEndOfflineTime())) {//下线时间-结束
            conMap.put("endOfflineTime", DateUtil.strTimeChangeLong(command.getEndOfflineTime() + DateUtil.end));
        }
        if (StringUtil.isNotEmpty(command.getTerminalBDCode())) {//北斗
            conMap.put("terminalBDCode", command.getTerminalBDCode());
        }
        return conMap;
    }

    /**
     * 处理位置数据
     *
     * @param list
     * @return
     */
    public List<QuerySaleRemovalPojo> setLocation(List<QuerySaleRemovalPojo> list) {
        Map<String,LCLocationData.LocationData> lastGpsMap = gpsCache.getAllLastGpsMap();
        for (QuerySaleRemovalPojo removalPojo : list) {
            //销售时间
            if (null != removalPojo.getSaleDate()) {
                removalPojo.setSaleDateStr(DateUtil.timeStr(removalPojo.getSaleDate().longValue()));
            }
            //出库时间
            if (null != removalPojo.getRemovalTime()) {
                removalPojo.setRemovalTimeStr(DateUtil.timeStr(removalPojo.getRemovalTime().longValue()));
            }
            BigInteger commId = removalPojo.getFkCommunicationId();
            if (StringUtil.isEmpty(commId) || commId.equals("0")) {
                commId = removalPojo.getBdCommunicationId();
            }
            if (null != removalPojo.getNettingLat() && null != removalPojo.getNettingLog() && removalPojo.getNettingLat().intValue() != 0 && removalPojo.getNettingLog().intValue() != 0) {
                removalPojo.setFirstLocation(NumberFormatUtil.getLatitudeOrLongitude(removalPojo.getNettingLog().intValue()) + ";" + NumberFormatUtil.getLatitudeOrLongitude(removalPojo.getNettingLat().intValue()));
                if (null != removalPojo.getNettingTime()) {
                    removalPojo.setFirstTime(DateUtil.timeStr(removalPojo.getNettingTime().longValue()));
                }
            } else {
                removalPojo.setNettingLat(new BigInteger("0"));
                removalPojo.setNettingLog(new BigInteger("0"));
                removalPojo.setFirstLocation(CloudConstants.LOCATION_DEFAULT);
            }
            LCLocationData.LocationData gpsdata = lastGpsMap.get(String.valueOf(commId));
            if (null != gpsdata) {
                removalPojo.setElat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                removalPojo.setElog(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
                removalPojo.setLastLocation(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()) + ";" + NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                removalPojo.setLastTime(DateUtil.timeStr(gpsdata.getGpsDate()));
            } else {
                removalPojo.setElat(0);
                removalPojo.setElog(0);
                removalPojo.setLastLocation(CloudConstants.LOCATION_DEFAULT);
            }
        }
        return list;
    }
}
