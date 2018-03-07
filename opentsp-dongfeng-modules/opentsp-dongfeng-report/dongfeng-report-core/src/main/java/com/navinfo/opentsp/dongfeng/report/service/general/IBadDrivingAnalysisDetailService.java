package com.navinfo.opentsp.dongfeng.report.service.general;


import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingAnalysisDetailCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingChartDto;

/**
 * Created by ZHANGTIANTONG on 2018/3/5/005.
 */
public interface IBadDrivingAnalysisDetailService {

    /**
     * 获取不良驾驶行为单条信息
     *
     * @param badDrivingAnalysisDetailCommand
     * @return BadDrivingAnalysisDto
     */
    public BadDrivingChartDto getBadDrivingInfo(BadDrivingAnalysisDetailCommand badDrivingAnalysisDetailCommand);
}