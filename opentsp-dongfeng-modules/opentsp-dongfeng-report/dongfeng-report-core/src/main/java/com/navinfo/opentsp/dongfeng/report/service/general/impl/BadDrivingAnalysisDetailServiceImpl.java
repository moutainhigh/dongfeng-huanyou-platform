package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.impl.RestTemplateServiceImpl;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingAnalysisDetailCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingCountCommand;
import com.navinfo.opentsp.dongfeng.report.constant.BadDrivingConstant;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingAnalysisDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingChartDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingCountDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingResultDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IBadDrivingAnalysisDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

/**
 * Created by ZHANGTIANTONG on 2018/3/5/005.
 */
@Service
public class BadDrivingAnalysisDetailServiceImpl extends BaseService implements IBadDrivingAnalysisDetailService {

    @Autowired
    private RestTemplateServiceImpl restTemplate;

    @Value("${sync.getBadDrivingCounts.url}")
    private String badDrivingUrl;


    /**
     * 获取不良驾驶行为单条信息
     *
     * @param badDrivingAnalysisDetailCommand
     * @return BadDrivingAnalysisDto
     */
    @Override
    public BadDrivingChartDto getBadDrivingInfo(BadDrivingAnalysisDetailCommand badDrivingAnalysisDetailCommand) {

        BadDrivingChartDto result = new BadDrivingChartDto();

        Map<String, Object> params = this.getQueryParams(badDrivingAnalysisDetailCommand);

        BadDrivingAnalysisDto badDrivingAnalysisDto = (BadDrivingAnalysisDto) dao.sqlFindObject("queryCarBasicInfo", params, BadDrivingAnalysisDto.class);

        result.setVin(badDrivingAnalysisDto.getStructureNum());

        BadDrivingCountCommand param = new BadDrivingCountCommand();

        List<String> vinList = new ArrayList<String>();

        vinList.add(badDrivingAnalysisDto.getStructureNum());

        param.setVinList(vinList);

        param.setDateStr(badDrivingAnalysisDetailCommand.getDateStr());
        try {

            /**
             * 调用接口获取车辆异常驾驶行为分析统计次数
             */
            ResponseEntity<BadDrivingResultDto> response = this.restTemplate.postForEntity(this.badDrivingUrl, param, BadDrivingResultDto.class);

            if (response.getStatusCode() == HttpStatus.OK) {

                List<BadDrivingCountDto> data = response.getBody().getData();
                for (BadDrivingCountDto countTemp : data) {
                    if (countTemp.getVin().equals(badDrivingAnalysisDto.getStructureNum())) {
                        this.badDrivingAnalysisDtoConverter(result, countTemp);
                    } else {
                        this.badDrivingAnalysisDtoConverter(result, null);
                    }
                }
            } else {
                this.badDrivingAnalysisDtoConverter(result, null);
            }
        } catch (HttpClientErrorException e) {
            this.badDrivingAnalysisDtoConverter(result, null);
        }
        return result;
    }

    /**
     * 拼接查询条件
     *
     * @param badDrivingAnalysisDetailCommand
     * @return
     */
    private Map<String, Object> getQueryParams(BadDrivingAnalysisDetailCommand badDrivingAnalysisDetailCommand) {

        //查询车辆基础信息
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("accountId", badDrivingAnalysisDetailCommand.getUserInfor().getUserId());
        params.put("accountType", badDrivingAnalysisDetailCommand.getUserInfor().getType());

        if (!StringUtil.isEmpty(badDrivingAnalysisDetailCommand.getChassisNum())) {
            params.put("chassisNum", badDrivingAnalysisDetailCommand.getChassisNum());
        }
        return params;
    }

    /**
     * 异常行为驾驶分析转换(饼图)
     *
     * @param badDrivingChartDto
     * @param badDrivingCountDto
     */
    private void badDrivingAnalysisDtoConverter(BadDrivingChartDto badDrivingChartDto, BadDrivingCountDto badDrivingCountDto) {

        List<String> nameList = new ArrayList<String>();
        List<Integer> countList = new ArrayList<Integer>();

        nameList.add(BadDrivingConstant.getAccelerate());
        nameList.add(BadDrivingConstant.getDecelerate());
        nameList.add(BadDrivingConstant.getSharpTurn());
        nameList.add(BadDrivingConstant.getLongIdling());
        nameList.add(BadDrivingConstant.getHighGearStart());
        nameList.add(BadDrivingConstant.getLowGearHighSpeed());
        nameList.add(BadDrivingConstant.getColdingStart());
        nameList.add(BadDrivingConstant.getFastSpeed());
        nameList.add(BadDrivingConstant.getFullAccelerator());
        nameList.add(BadDrivingConstant.getLargeAccelerator());
        nameList.add(BadDrivingConstant.getNightDriving());

        if (null == badDrivingCountDto) {
            for (String temp : nameList) {
                countList.add(0);
            }
        } else {
            countList.add(badDrivingCountDto.getAccelerateCount());
            countList.add(badDrivingCountDto.getDecelerateCount());
            countList.add(badDrivingCountDto.getSharpTurnCount());
            countList.add(badDrivingCountDto.getLongIdlingCount());
            countList.add(badDrivingCountDto.getHighGearStartCount());
            countList.add(badDrivingCountDto.getLowGearHighSpeedCount());
            countList.add(badDrivingCountDto.getColdingStartCount());
            countList.add(badDrivingCountDto.getFastSpeedCount());
            countList.add(badDrivingCountDto.getFullAcceleratorCount());
            countList.add(badDrivingCountDto.getLargeAcceleratorCount());
            countList.add(badDrivingCountDto.getNightDrivingCount());
        }

        String[] name = nameList.toArray(new String[nameList.size()]);
        Integer[] count = countList.toArray(new Integer[countList.size()]);

        badDrivingChartDto.setName(name);
        badDrivingChartDto.setCount(count);
    }
}