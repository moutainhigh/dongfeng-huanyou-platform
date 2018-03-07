package com.navinfo.opentsp.dongfeng.report.service.salestate.impl;

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
import com.navinfo.opentsp.dongfeng.report.commands.salestate.QuerySaleStateCommand;
import com.navinfo.opentsp.dongfeng.report.converter.salestate.QuerySaleStateConverter;
import com.navinfo.opentsp.dongfeng.report.dto.salestate.QuerySaleStateDto;
import com.navinfo.opentsp.dongfeng.report.pojo.salestate.QuerySaleStatePojo;
import com.navinfo.opentsp.dongfeng.report.service.salestate.IQuerySaleStateService;
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
public class QuerySaleStateServiceImpl extends BaseService implements IQuerySaleStateService {

    @Autowired
    private GpsCache gpsCache;

    @Override
    public HttpCommandResultWithData querySaleState(QuerySaleStateCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String check = checkTimeValid(command);
        if (StringUtil.isNotEmpty(check)) {
            result.setResultCode(ReturnCode.SERVER_ERROR.code());
            result.setMessage(check);
            return result;
        }
        //组装条件
        Map<String, Object> conMap = setConditon(command);
        PagingInfo<QuerySaleStatePojo> datas = dao.sqlPagelFind("querySaleStateReport", conMap, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QuerySaleStatePojo.class);
        //处理首次末次时间及位置
        List<QuerySaleStateDto> list = QuerySaleStateConverter.convert(datas.getList());
        for (QuerySaleStateDto saleStateDto : list) {
            String commId = "";
            BigInteger bdCom = saleStateDto.getBdCommunicationId();
            BigInteger fkCom = saleStateDto.getFkCommunicationId();
            if(null==bdCom&&null==fkCom){
                continue;
            }else{
                commId = ((null!=fkCom&&fkCom.intValue()!=0)?fkCom.toString():bdCom.toString());
            }
            if (StringUtil.isEmpty(commId) || commId.equals("0")) {
                commId = saleStateDto.getBdCommunicationId().toString();
            }
            LCLocationData.LocationData gpsdata=gpsCache.getLastGpsVlid(commId);
            if(null!=gpsdata){
                saleStateDto.setElat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                saleStateDto.setElog(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
                saleStateDto.setLastTime(DateUtil.timeStr(gpsdata.getGpsDate()));
            }else{
                saleStateDto.setElat(0);
                saleStateDto.setElog(0);
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
    public List<QuerySaleStatePojo> querySaleStateForExport(QuerySaleStateCommand command) {

        //组装条件
        Map<String, Object> conMap =  setConditon(command);
        PagingInfo<QuerySaleStatePojo> datas = dao.sqlPagelFind("querySaleStateReport", conMap, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), QuerySaleStatePojo.class);
        //处理首次末次时间及位置
        List<QuerySaleStatePojo> list = setLocation(datas.getList());
        return list;
    }

    @Override
    public List<QuerySaleStatePojo> queryAllSaleStateForExport(QuerySaleStateCommand command) {
        //组装条件
        Map<String, Object> conMap =  setConditon(command);
        List<QuerySaleStatePojo> datas = (List<QuerySaleStatePojo>) dao.sqlFind("querySaleStateReport", conMap, QuerySaleStatePojo.class);
        //处理首次末次时间及位置
        List<QuerySaleStatePojo> list = setLocation(datas);
        return list;
    }

    @Override
    public String checkTimeValid(QuerySaleStateCommand command) {
        String result = "";
        if (StringUtil.isNotEmpty(command.getBeginSaleDate()) && StringUtil.isNotEmpty(command.getEndSaleDate())) {
            long beginRemovalTime = DateUtil.strTimeChangeLong(command.getBeginSaleDate() + DateUtil.start);
            long endRemovalTime = DateUtil.strTimeChangeLong(command.getEndSaleDate() + DateUtil.end);
            if (beginRemovalTime > endRemovalTime) {
                result = "销售开始时间大于结束时间";
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

        if (StringUtil.isNotEmpty(command.getBeginRegisterTime()) && StringUtil.isNotEmpty(command.getEndRegisterTime())) {

            long beginRegisterTime = DateUtil.strTimeChangeLong(command.getBeginRegisterTime() + DateUtil.start);
            long endRegisterTime = DateUtil.strTimeChangeLong(command.getEndRegisterTime() + DateUtil.end);
            if (beginRegisterTime > endRegisterTime) {
                result = "注册开始时间大于结束时间";
                return result;
            }
        }
        return result;
    }

    /**
     * 查询条件组装
     * @param command
     * @return
     */
    public Map<String, Object> setConditon(QuerySaleStateCommand command){
        //组装条件
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("accountId",new BigInteger(command.getUserInfor().getUserId().toString()));//用户ID
        conMap.put("accountType", command.getUserInfor().getType());
        if (StringUtil.isNotEmpty(command.getChassisNum())) {//底盘号
            conMap.put("chassisNum", command.getChassisNum());
        }
        if (StringUtil.isNotEmpty(command.getCarOwner())) {//所属客户
            conMap.put("carOwner", command.getCarOwner());
        }
        if (StringUtil.isNotEmpty(command.getCarType())&&!command.getCarType().equals(QueryConstants.CONDITION_ALL)) {//车辆型号
            conMap.put("carType", command.getCarType());
        }
        if (StringUtil.isNotEmpty(command.getBeginSaleDate())) {//销售时间-开始
            conMap.put("beginSaleDate", DateUtil.strTimeChangeLong(command.getBeginSaleDate() + DateUtil.start));
        }
        if (StringUtil.isNotEmpty(command.getEndSaleDate())) {//销售时间-结束
            conMap.put("endSaleDate", DateUtil.strTimeChangeLong(command.getEndSaleDate() + DateUtil.end));
        }
        if (StringUtil.isNotEmpty(command.getCarCph())) {//车牌号
            conMap.put("carCph", command.getCarCph());
        }
        if (StringUtil.isNotEmpty(command.getTeamName())) {//经销商
            conMap.put("teamName", command.getTeamName());
        }
        if (StringUtil.isNotEmpty(command.getBeginRegisterTime())) {//注册时间-开始
            conMap.put("beginRegisterTime", DateUtil.strTimeChangeLong(command.getBeginRegisterTime() + DateUtil.start));
        }
        if (StringUtil.isNotEmpty(command.getEndRegisterTime())) {//注册时间-结束
            conMap.put("endRegisterTime", DateUtil.strTimeChangeLong(command.getEndRegisterTime() + DateUtil.end));
        }
        if (StringUtil.isNotEmpty(command.getTerminalFKCode())) {//防拆盒
            conMap.put("terminalFKCode", command.getTerminalFKCode());
        }
        if (StringUtil.isNotEmpty(command.getEngType())&&!command.getEngType().equals(QueryConstants.CONDITION_ALL)) {//发动机类型
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
     * @param list
     * @return
     */
    public List<QuerySaleStatePojo> setLocation(List<QuerySaleStatePojo> list){
        Map<String,LCLocationData.LocationData> lastGpsMap = gpsCache.getAllLastGpsMap();
        for (QuerySaleStatePojo statePojo : list) {
            //销售时间
            if(null!=statePojo.getSaleDate()){
                statePojo.setSaleDateStr(DateUtil.timeStr(statePojo.getSaleDate().longValue()));
            }
            //末次注册时间
            if(null!=statePojo.getRegisterTime()){
                statePojo.setRegisterTimeStr(DateUtil.timeStr(statePojo.getRegisterTime().longValue()));
            }
            BigInteger commId = statePojo.getFkCommunicationId();
            if (StringUtil.isEmpty(commId)) {
                commId = statePojo.getBdCommunicationId();
            }
            if(null!=statePojo.getNettingLat()&&null!=statePojo.getNettingLog()&&statePojo.getNettingLat().intValue()!=0&&statePojo.getNettingLog().intValue()!=0){
                statePojo.setFirstLocation(NumberFormatUtil.getLatitudeOrLongitude(statePojo.getNettingLog().intValue())+";"+NumberFormatUtil.getLatitudeOrLongitude(statePojo.getNettingLat().intValue()));
                if(null!=statePojo.getNettingTime()){
                    statePojo.setFirstTime(DateUtil.timeStr(statePojo.getNettingTime().longValue()));
                }
            }else{
                statePojo.setNettingLat(new BigInteger("0"));
                statePojo.setNettingLog(new BigInteger("0"));
                statePojo.setFirstLocation(CloudConstants.LOCATION_DEFAULT);
            }
            LCLocationData.LocationData gpsdata = lastGpsMap.get(String.valueOf(commId));
            if(null!=gpsdata){
                statePojo.setLastTime(DateUtil.timeStr(gpsdata.getGpsDate()));
                statePojo.setElat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                statePojo.setElog(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
                statePojo.setLastLocation(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()) + ";" + NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
            } else {
                statePojo.setElat(0);
                statePojo.setElog(0);
                statePojo.setLastLocation(CloudConstants.LOCATION_DEFAULT);
            }
        }
        return list;
    }
}
