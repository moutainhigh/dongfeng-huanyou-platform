package com.navinfo.opentsp.dongfeng.report.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.station.IgnoreStationOverTimeAlarmsCommand;
import com.navinfo.opentsp.dongfeng.report.commands.station.QueryStationOverTimeAlarmsCommand;

/**
 * 查询服务站超时报警信息
 * @author wt
 * @date 2017-03-27
 * @modify
 * @version 1.0
 */
public interface IStationOverTimeAlarmsService {
    /**
     * Query Station OverTime Alarms
     * @param command
     * @return
     */
    HttpCommandResultWithData queryStationOverTimeAlarms(final QueryStationOverTimeAlarmsCommand command, final boolean isQueryAll);

    /**
     * Ignore Station OverTime Alarms
     * @param command
     * @return
     */
    HttpCommandResultWithData ignoreStationOverTimeAlarms(final IgnoreStationOverTimeAlarmsCommand command);
}
