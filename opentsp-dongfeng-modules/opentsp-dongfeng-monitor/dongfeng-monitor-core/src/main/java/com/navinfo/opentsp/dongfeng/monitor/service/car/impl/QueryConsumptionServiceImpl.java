package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.webservice.newstatisticsdata.LCCalculateMileageConsumptionRes;
import com.lc.core.protocol.webservice.newstatisticsdata.entity.LCMileageConsumptionRes;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryConsumptionCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryConsumptionConverter;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryConsumptionDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCommonCarPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryConsumptionPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 轨迹回放:时间段内总里程、时间段内总油耗service
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryConsumptionServiceImpl extends BaseService implements IQueryConsumptionService {
    /**
     * QueryConsumptionServiceImpl LOG
     */
    protected static final Logger logger = LoggerFactory.getLogger(QueryConsumptionServiceImpl.class);

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;
    /**
     * 轨迹回放查询轨迹点、时间段内总里程、时间段内总油耗
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryConsumption(QueryConsumptionCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            QueryConsumptionPojo  consumptionPojo = new QueryConsumptionPojo();
            String beginDate = command.getBeginDate();
            String endDate = command.getEndDate();
            String carId = command.getCarId();
            //根据车辆id获取车辆信息           ------start
            Map<String,Object> parm = new HashMap<>();
            parm.put("carId", Long.valueOf(carId));
            parm.put("accountId", command.getUserInfor().getUserId());
            parm.put("accountType", command.getUserInfor().getType());
            QueryCommonCarPojo queryCommonCarPojo = (QueryCommonCarPojo)dao.sqlFindObject("queryCommonCar", parm, QueryCommonCarPojo.class);
            long commId = 0;
            if(null!=queryCommonCarPojo&&null!=queryCommonCarPojo.getCommId()){
                commId = queryCommonCarPojo.getCommId().longValue();
            }else{
                result.setData(consumptionPojo);
                return result;
            }
            //根据车辆id获取车辆信息           ------end
            DataAnalysisWebService dataAnalysisWebService = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
            /**
             * 组装“里程能耗数据检索”接口参数
             */
            //发起rmi接口调用
            byte[] bytes = dataAnalysisWebService.calculateMileageConsumption(commId, DateUtil.strTimeChangeLong(beginDate), DateUtil.strTimeChangeLong(endDate));
            if(null!=bytes){
                //解析返回结果
                LCCalculateMileageConsumptionRes.CalculateMileageConsumptionRes calcu= LCCalculateMileageConsumptionRes.CalculateMileageConsumptionRes.parseFrom(bytes);
                if(null!=calcu){
                    LCMileageConsumptionRes.MileageConsumptionRes mc = calcu.getData();
                    //返回结果分值
                    if(null!=mc) {
                        //时间段内总油耗
                        consumptionPojo.setOilConsumption(mc.getOilConsumption());
                        //时间段内总里程（位置云返回时单位（米），用千米显示保留两位小数）
                        consumptionPojo.setMileage(NumberFormatUtil.getDoubleValueData((double) mc.getMileage() / 1000, 2));
                    }
                }
            }
            QueryConsumptionDto dto = QueryConsumptionConverter.consumptionConverter(consumptionPojo);
            result.setData(dto);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().getName() + "`s QueryConsumptionServiceImpl has error : " , e);
            result.fillResult(ReturnCode.SERVER_ERROR);
            result.setMessage("查询时间段内总里程、时间段内总油耗异常");
        }
        return result;
    }
}
