package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryFailureStatisticsCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.FailureStatisticsDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IFailureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangtianttong on 2018/1/31/031.
 *
 * @author ztt
 * @version 1.0
 * @date 2017-01-31
 * @modify
 * @copyright Navinfo
 */

@Component
public class FailureStatisticsHandler extends ValidateTokenAndReSetAbstracHandler<QueryFailureStatisticsCommand, HttpCommandResultWithData> {

    @Autowired
    private IFailureStatisticsService failureStatisticsService;

    public FailureStatisticsHandler() {
        super(QueryFailureStatisticsCommand.class, HttpCommandResultWithData.class);
    }

    protected FailureStatisticsHandler(Class<QueryFailureStatisticsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryFailureStatisticsCommand command) {

        HttpCommandResultWithData resultWithData = new HttpCommandResultWithData();

        List<FailureStatisticsDto> result = this.failureStatisticsService.queryFailureStatisticsPercentByEngineType(command);

        resultWithData.setData(result);

        return resultWithData;
    }
}