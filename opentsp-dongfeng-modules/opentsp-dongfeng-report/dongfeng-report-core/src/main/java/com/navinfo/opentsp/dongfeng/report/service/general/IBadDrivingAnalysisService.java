package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingAnalysisCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportBadDrivingAnalysisCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingAnalysisDto;

import java.util.List;

/**
 * 不良驾驶行为分析
 * Created by ZHANGTIANTONG on 2018/3/1/001.
 */
public interface IBadDrivingAnalysisService {

    /**
     * 获取不良驾驶行为列表
     *
     * @param badDrivingAnalysisCommand
     * @return BadDrivingAnalysisDto
     */
    public PagingInfo<BadDrivingAnalysisDto> getBadDrivingInfos(BadDrivingAnalysisCommand badDrivingAnalysisCommand);


    /**
     * 查询全部车辆的驾驶行为分析异常
     *
     * @param command
     * @return BadDrivingAnalysisDto
     */
    public List<BadDrivingAnalysisDto> getAllBadDrivingInfos(BadDrivingAnalysisCommand command);

}
