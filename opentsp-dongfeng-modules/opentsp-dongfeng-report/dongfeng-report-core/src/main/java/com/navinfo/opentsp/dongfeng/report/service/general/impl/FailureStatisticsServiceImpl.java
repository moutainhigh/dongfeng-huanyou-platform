package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.webservice.statisticsdata.LCFaultCodeRecords;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCFaultCode;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryFailureStatisticsCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.CarTerminalDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.FailureStatisticsDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IFailureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangtiantong on 2018/1/31/031.
 */
@Service
public class FailureStatisticsServiceImpl extends BaseService implements IFailureStatisticsService {

    @Autowired
    private IBaseDataService baseDataService;

    @Autowired
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    /**
     * 字典表发动机类型编号
     */
    private final static int engineType = 40;

    @Override
    public List<FailureStatisticsDto> queryFailureStatisticsPercentByEngineType(QueryFailureStatisticsCommand command) {


        //查询车辆基础信息
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());

        //获取查询时间段
        long beginDate = setTimeValue(command.getQueryTime().substring(0, 10) + " 00:00:00");
        long endDate = setTimeValue(command.getQueryTime().substring(13, 23) + " 23:59:59");
        List<FailureStatisticsDto> result = new ArrayList<FailureStatisticsDto>();
        try {
            //获取所要查询分类的发动机类型
            List<BaseData> engineTypeList = baseDataService.queryList(engineType);

            //发动机故障计数初始化
            Map<String, Integer> engineTypeCount = new HashMap<String, Integer>();
            for (BaseData baseData : engineTypeList) {
                engineTypeCount.put(baseData.getCode(), 0);
            }

            //获取所有车辆通讯号、发动机类型和通讯号
            List<CarTerminalDto> cars = dao.sqlFind("queryAllCar", params, CarTerminalDto.class);

            List<Long> communicationIds = new ArrayList<Long>();

            for (CarTerminalDto car : cars) {
                communicationIds.add(car.getCommunicationId().longValue());
            }

            DataAnalysisWebService service = service = cloudDataRmiClientConfiguration.getDataAnalysisWebService();

            // 条件筛选：（SPN： FMI： 故障描述：）
            CommonParameterSerializer parameter = new CommonParameterSerializer();
            parameter.setMultipage(true);
            parameter.setSort(false);
            int spn = (command.getSpn() == null || Integer.parseInt(command.getSpn()) < 0) ? -1
                    : Integer.parseInt(command.getSpn().trim());
            int fmi = (command.getFmi() == null || Integer.parseInt(command.getFmi()) < 0) ? -1
                    : Integer.parseInt(command.getFmi().trim());
            byte[] data = service.getFaultCodeRecords(communicationIds, spn, fmi, beginDate,
                    endDate, parameter);
            logger.info("FaultSummaryServiceImpl:cloudDataLength:" + (data == null ? "null" : data.length));
            if (null != data) {
                LCFaultCodeRecords.FaultCodeRecords builder = LCFaultCodeRecords.FaultCodeRecords.parseFrom(data);
                List<LCFaultCode.FaultCode> list = builder.getDataListList();
                Map<String, String> sfMap = new HashMap<String, String>();
                if (list != null && !list.isEmpty()) {
                    for (LCFaultCode.FaultCode vo : list) {
                        for (CarTerminalDto car : cars) {
                            if (car.getCommunicationId().longValue() == vo.getTerminalId()) {
                                engineTypeCount.put(car.getEngineCode(), engineTypeCount.get(car.getEngineCode()) + 1);
                            }
                        }
                    }
                    double sum = 0.0;
                    for (BaseData engineType : engineTypeList) {
//                        int value = new Random().nextInt(5);
//                        sum += value;
                        sum += engineTypeCount.get(engineType.getCode()) / cars.size();
                        FailureStatisticsDto failureStatisticsDto = new FailureStatisticsDto();
                        failureStatisticsDto.setEngineTypeCode(engineType.getCode());
                        failureStatisticsDto.setPercent(engineTypeCount.get(engineType.getCode()) / cars.size());
                        failureStatisticsDto.setEngineTypeName(engineType.getValue());
                        result.add(failureStatisticsDto);
                    }
                    FailureStatisticsDto remainder = new FailureStatisticsDto();
                    remainder.setEngineTypeName("无故障");
                    remainder.setPercent(100 - sum);
                    result.add(remainder);
                } else {
                    FailureStatisticsDto remainder = new FailureStatisticsDto();
                    remainder.setEngineTypeName("无故障");
                    remainder.setPercent(100);
                    result.add(remainder);
                }
            } else {
                FailureStatisticsDto remainder = new FailureStatisticsDto();
                remainder.setEngineTypeName("无故障");
                remainder.setPercent(100);
                result.add(remainder);
            }
        } catch (InvalidProtocolBufferException e) {
            logger.info("轨迹数据获取或转换异常", e);
        } catch (Exception e) {
            logger.info("车辆获取或转换异常", e);
        }
        return result;

    }

    /**
     * 根据时间字符串获取对应的时间戳
     *
     * @param value
     * @return
     */
    private Long setTimeValue(String value) {// 秒级别
        Long date = 0L;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(value).getTime() / 1000;
        } catch (ParseException e) {
            logger.error("异常", e);
        }
        return date;
    }
}