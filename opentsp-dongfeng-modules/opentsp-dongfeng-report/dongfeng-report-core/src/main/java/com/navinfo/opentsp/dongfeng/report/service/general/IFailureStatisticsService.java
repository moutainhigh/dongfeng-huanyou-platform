package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.report.commands.general.QueryFailureStatisticsCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.FailureStatisticsDto;

import java.util.List;

/**
 * 故障分类分析
 *
 * @Author zhangtiantong@aerozhonghuan.com
 * @Date 2018/1/31
 */
public interface IFailureStatisticsService {

    /**
     * 根据条件查询故障情况百分比
     *
     * @return
     */
    public List<FailureStatisticsDto> queryFailureStatisticsPercentByEngineType(QueryFailureStatisticsCommand command);
}
