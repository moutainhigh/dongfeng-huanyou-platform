package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryConsumptionCommand;
import org.springframework.stereotype.Service;

/**
 * 轨迹回放:时间段内总里程、时间段内总油耗service
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Service
public interface IQueryConsumptionService {
    /**
     * 轨迹回放:时间段内总里程、时间段内总油耗
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryConsumption(QueryConsumptionCommand command);
}