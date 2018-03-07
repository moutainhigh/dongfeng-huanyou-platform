package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.ExportTimelyDataCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryTracesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-10-19
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class ExportTimelyDataHandler extends ValidateTokenAndReSetAbstracHandler<ExportTimelyDataCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryTracesService iQueryTraceService;

    protected static final Logger logger = LoggerFactory.getLogger(ExportTracesHandler.class);

    public ExportTimelyDataHandler() {
        super(ExportTimelyDataCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportTimelyDataHandler(Class<ExportTimelyDataCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportTimelyDataCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result = iQueryTraceService.queryTimelyDataList(command);
        return result;
    }
}