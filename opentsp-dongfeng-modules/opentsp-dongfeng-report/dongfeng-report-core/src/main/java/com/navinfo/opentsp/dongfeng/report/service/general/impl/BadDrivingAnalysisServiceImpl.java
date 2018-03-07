package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.impl.RestTemplateServiceImpl;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingAnalysisCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingCountCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingAnalysisDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingCountDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingResultDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IBadDrivingAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;


/**
 * Created by ZHANGTIANTONG on 2018/3/1/001.
 */
@Service
public class BadDrivingAnalysisServiceImpl extends BaseService implements IBadDrivingAnalysisService {

    @Autowired
    private RestTemplateServiceImpl restTemplate;

    @Value("${sync.getBadDrivingCounts.url}")
    private String badDrivingUrl;

    /**
     * 获取不良驾驶列表(分页)
     *
     * @param badDrivingAnalysisCommand
     * @return BadDrivingAnalysisDto
     */
    @Override
    public PagingInfo<BadDrivingAnalysisDto> getBadDrivingInfos(BadDrivingAnalysisCommand badDrivingAnalysisCommand) {

        Map<String, Object> params = getQueryParams(badDrivingAnalysisCommand);


        int pageIndex = new Integer(badDrivingAnalysisCommand.getPage_number());

        int pageSize = new Integer(badDrivingAnalysisCommand.getPage_size());

        //查询登入用户能访问到的车辆
        PagingInfo<BadDrivingAnalysisDto> carBaseInfo = dao.sqlPagelFind("queryCarBasicInfo", params, pageIndex, pageSize, BadDrivingAnalysisDto.class);


        //获取车辆VIN码
        List<String> vidList = new ArrayList<String>();
        for (BadDrivingAnalysisDto temp : carBaseInfo.getList()) {
            temp.setBadDrivingDate(badDrivingAnalysisCommand.getDateStr());
            vidList.add(temp.getStructureNum());
        }

        BadDrivingCountCommand param = new BadDrivingCountCommand();
        param.setDateStr(badDrivingAnalysisCommand.getDateStr());
        param.setVinList(vidList);

        try {
            //调用接口获取车辆异常驾驶行为分析统计次数
            ResponseEntity<BadDrivingResultDto> response = this.restTemplate.postForEntity(this.badDrivingUrl, param, BadDrivingResultDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                BadDrivingResultDto badDrivingResultDto = response.getBody();
                List<BadDrivingCountDto> data = badDrivingResultDto.getData();
                for (BadDrivingCountDto countTemp : data) {
                    for (BadDrivingAnalysisDto analysisDto : carBaseInfo.getList()) {
                        if (countTemp.getVin().equals(analysisDto.getStructureNum())) {
                            this.badDrivingAnalysisDtoConverter(analysisDto, countTemp, badDrivingAnalysisCommand.getDateStr());
                        } else {
                            this.badDrivingAnalysisDtoConverter(analysisDto, null, badDrivingAnalysisCommand.getDateStr());
                        }
                    }
                }
            } else {
                for (BadDrivingAnalysisDto analysisDto : carBaseInfo.getList()) {
                    this.badDrivingAnalysisDtoConverter(analysisDto, null, badDrivingAnalysisCommand.getDateStr());
                }
            }

        } catch (HttpClientErrorException e) {
            for (BadDrivingAnalysisDto analysisDto : carBaseInfo.getList()) {
                this.badDrivingAnalysisDtoConverter(analysisDto, null, badDrivingAnalysisCommand.getDateStr());
            }
        }

        return carBaseInfo;
    }


    /**
     * 查询全部车辆的驾驶行为分析异常
     *
     * @param command
     * @return
     */
    @Override
    public List<BadDrivingAnalysisDto> getAllBadDrivingInfos(BadDrivingAnalysisCommand command) {

        Map<String, Object> params = getQueryParams(command);

        List<BadDrivingAnalysisDto> carBaseInfo = dao.sqlFind("queryCarBasicInfo", params, BadDrivingAnalysisDto.class);

        BadDrivingCountCommand param = new BadDrivingCountCommand();
        param.setDateStr(command.getDateStr());
        param.setIsAll(true);

        try {
            //调用接口获取车辆异常驾驶行为分析统计次数
            ResponseEntity<BadDrivingResultDto> response = this.restTemplate.postForEntity(this.badDrivingUrl, param, BadDrivingResultDto.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                BadDrivingResultDto badDrivingResultDto = response.getBody();
                List<BadDrivingCountDto> data = badDrivingResultDto.getData();
                for (BadDrivingCountDto countTemp : data) {
                    for (BadDrivingAnalysisDto analysisDto : carBaseInfo) {
                        if (countTemp.getVin().equals(analysisDto.getStructureNum())) {
                            this.badDrivingAnalysisDtoConverter(analysisDto, countTemp, command.getDateStr());
                        } else {
                            this.badDrivingAnalysisDtoConverter(analysisDto, null, command.getDateStr());
                        }
                    }
                }
            } else {
                for (BadDrivingAnalysisDto analysisDto : carBaseInfo) {
                    this.badDrivingAnalysisDtoConverter(analysisDto, null, command.getDateStr());
                }
            }
        } catch (HttpClientErrorException e) {
            for (BadDrivingAnalysisDto analysisDto : carBaseInfo) {
                this.badDrivingAnalysisDtoConverter(analysisDto, null, command.getDateStr());
            }
        }
        return carBaseInfo;
    }

    /**
     * 拼接查询条件
     *
     * @param badDrivingAnalysisCommand
     * @return
     */
    private Map<String, Object> getQueryParams(BadDrivingAnalysisCommand badDrivingAnalysisCommand) {

        //查询车辆基础信息
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("accountId", badDrivingAnalysisCommand.getUserInfor().getUserId());
        params.put("accountType", badDrivingAnalysisCommand.getUserInfor().getType());

        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getChassisNum())) {
            params.put("chassisNum", badDrivingAnalysisCommand.getChassisNum());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getCarOwner())) {
            params.put("carOwner", badDrivingAnalysisCommand.getCarOwner());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getCarType())) {
            params.put("carType", badDrivingAnalysisCommand.getCarType());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getPlateNum())) {
            params.put("plateNum", badDrivingAnalysisCommand.getPlateNum());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getEngineType())) {
            params.put("engineType", badDrivingAnalysisCommand.getEngineType());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getCompanyName())) {
            params.put("companyName", badDrivingAnalysisCommand.getCompanyName());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getCarTerId())) {
            params.put("carTerId", badDrivingAnalysisCommand.getCarTerId());
        }
        if (!StringUtil.isEmpty(badDrivingAnalysisCommand.getTerId())) {
            params.put("terId", badDrivingAnalysisCommand.getTerId());
        }
        return params;
    }

    /**
     * 异常行为驾驶分析转换
     *
     * @param badDrivingAnalysisDto
     * @param badDrivingCountDto
     */
    private void badDrivingAnalysisDtoConverter(BadDrivingAnalysisDto badDrivingAnalysisDto, BadDrivingCountDto badDrivingCountDto, String dateStr) {

        if (null != badDrivingCountDto) {
            badDrivingAnalysisDto.setAccelerateCount(badDrivingCountDto.getAccelerateCount());
            badDrivingAnalysisDto.setDecelerateCount(badDrivingCountDto.getDecelerateCount());
            badDrivingAnalysisDto.setSharpTurnCount(badDrivingCountDto.getSharpTurnCount());
            badDrivingAnalysisDto.setLongIdlingCount(badDrivingCountDto.getLongIdlingCount());
            badDrivingAnalysisDto.setHighGearStartCount(badDrivingCountDto.getHighGearStartCount());
            badDrivingAnalysisDto.setLowGearHighSpeedCount(badDrivingCountDto.getLowGearHighSpeedCount());
            badDrivingAnalysisDto.setColdingStartCount(badDrivingCountDto.getColdingStartCount());
            badDrivingAnalysisDto.setFastSpeedCount(badDrivingCountDto.getFastSpeedCount());
            badDrivingAnalysisDto.setFullAcceleratorCount(badDrivingCountDto.getFullAcceleratorCount());
            badDrivingAnalysisDto.setLargeAcceleratorCount(badDrivingCountDto.getLargeAcceleratorCount());
            badDrivingAnalysisDto.setNightDrivingCount(badDrivingCountDto.getNightDrivingCount());
            badDrivingAnalysisDto.setBadDrivingDate(dateStr);
        }
    }
}