package com.navinfo.opentsp.dongfeng.openapi.core.handler.tboss.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.station.StationPositionReportCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IPositionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务站站位置上报接口
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class StationPositionReportHandler extends ValidateUsingUserDefinedMethodAbstractHandler<StationPositionReportCommand, HttpCommandResultWithData> {
    public StationPositionReportHandler() {
        super(StationPositionReportCommand.class, HttpCommandResultWithData.class);
    }

    protected StationPositionReportHandler(Class<StationPositionReportCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IPositionReportService positionReportService;

    @Override
    protected HttpCommandResultWithData openApiHandle(StationPositionReportCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = positionReportService.stationLocationReport(command.getStationPositionReportInfo());
        } catch (Exception e) {
            logger.error("stationLocationReport", e);
            result.fillResult(ReturnCode.SERVER_ERROR);
        }
        return result;
    }
}
