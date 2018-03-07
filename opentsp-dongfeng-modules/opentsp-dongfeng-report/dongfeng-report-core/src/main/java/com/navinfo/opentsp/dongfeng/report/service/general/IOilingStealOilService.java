package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.report.commands.general.QueryStealOilCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.StealOilDataDto;

import java.util.List;

/**
 * Created by zhangtiantong on 2018/2/27.
 * 统计加油减油异常点
 */
public interface IOilingStealOilService {


    /**
     * 统计加油减油异常点
     */
    public String getOilingStealOil(List<Long> lists, long startDate, long endDate, long oilType) ;

    /**
     * 获取异常加油减油情况
     *
     * @param command
     * @return
     */
    public StealOilDataDto getStealOilData(QueryStealOilCommand command);
}
