package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRestTemplateService;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryHistoryVehicleCountsCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryVehicleCountsCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IVehicleCountsService;
import com.navinfo.opentsp.dongfeng.openapi.dto.bigdata.VehicleCountDto;
import com.navinfo.opentsp.dongfeng.openapi.dto.bigdata.VehicleHistoryCountDto;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/29
 * @modify
 * @copyright
 */
@Service
public class VehicleCountsServiceImpl extends BaseService implements IVehicleCountsService {

    /**
     *生产信息
     *  历史累计生产=120万+16886（17年生产的非F9车辆数）+17年生产的F9车辆数。
     *  当年累计生产=    16886（17年生产的非F9车辆数）+17年生产的F9车辆数。
     *  注：“17年生产的F9车辆数”不要直接取大屏右上角“总车辆数”数值，因为“总车辆数”包括16年生产的部分车辆和17年之前生产的后装车辆。
     */
    @Value("${bigdata.car.baseCounts}")
    private String baseCounts;
    @Value("${bigdata.car.productCounts}")
    private String productCounts;

    protected static final Logger logger = LoggerFactory.getLogger(VehicleCountsServiceImpl.class);
    @Autowired
    private IRestTemplateService restTemplateService;

    @Value("${cloud.mileages.url}")
    private String cloudUrl;

    @Override
    public HttpCommandResultWithData queryCounts(QueryVehicleCountsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        VehicleCountDto dto = new VehicleCountDto();
        List<BigInteger> counts = (List<BigInteger>) dao.sqlFindField("queryCounts", new HashMap<>(16));
        if (CollectionUtils.isNotEmpty(counts)) {
            dto.setCounts(counts.get(0).longValue());
        }
        ResponseEntity<String> response = restTemplateService.getForEntity(cloudUrl, String.class);
        try {
            Map bodys = JsonUtil.toMap(response.getBody());
            if (NumberUtils.toInt(bodys.get("resultCode").toString()) == HttpStatus.OK.value()) {
                JSONObject jsonObject = JSONObject.fromObject(bodys.get("data"));
                dto.setOnLineCounts(NumberUtils.toLong(jsonObject.get("onlineNum").toString()));
                dto.setLocations(NumberUtils.toLong(jsonObject.get("packageNum").toString()));
                dto.setMileages(NumberUtils.toLong(jsonObject.get("mileage").toString()));
            }
        } catch (IOException e) {
            logger.error("query totalMileageAndPackage from cloud is error", e);
        }
        result.setData(dto);
        return result;
    }

    @Override
    public HttpCommandResultWithData queryHistoryCounts(QueryHistoryVehicleCountsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        VehicleHistoryCountDto dto = new VehicleHistoryCountDto();
        List<BigInteger> counts = (List<BigInteger>) dao.sqlFindField("queryYearCounts", new HashMap<>(16));
        if (CollectionUtils.isNotEmpty(counts)) {
            dto.setCounts((counts.get(0).longValue())+NumberUtils.toLong(baseCounts)+NumberUtils.toLong(productCounts));
        }

        List<BigInteger> yearCounts = (List<BigInteger>) dao.sqlFindField("queryYearCounts", new HashMap<>(16));
        if (CollectionUtils.isNotEmpty(yearCounts)) {
            dto.setYearCounts(yearCounts.get(0).longValue());
        }

        List<BigInteger> monthCounts = (List<BigInteger>) dao.sqlFindField("queryMonthCounts", new HashMap<>(16));
        if (CollectionUtils.isNotEmpty(monthCounts)) {
            dto.setMonthCounts(monthCounts.get(0).longValue());
        }

        List<BigInteger> dayOffLineCounts = (List<BigInteger>) dao.sqlFindField("queryDayOffLineCounts", new HashMap<>(16));
        if (CollectionUtils.isNotEmpty(dayOffLineCounts)) {
            dto.setDayOffLineCounts(dayOffLineCounts.get(0).longValue());
        }

        List<BigInteger> dayStorageCounts = (List<BigInteger>) dao.sqlFindField("queryDayStorageCounts", new HashMap<>(16));
        if (CollectionUtils.isNotEmpty(dayStorageCounts)) {
            dto.setDayStorageCounts(dayStorageCounts.get(0).longValue());
        }
        result.setData(dto);
        return result;
    }
}
