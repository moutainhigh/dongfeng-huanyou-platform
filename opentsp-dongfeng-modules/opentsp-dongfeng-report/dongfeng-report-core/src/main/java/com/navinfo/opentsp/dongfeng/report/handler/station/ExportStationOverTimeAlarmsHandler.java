package com.navinfo.opentsp.dongfeng.report.handler.station;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.station.ExportStationOverTimeAlarmsCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.station.QueryStationOverTimeAlarmsDto;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationOverTimeAlarmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-03-31
 * @modify
 */
@Component
public class ExportStationOverTimeAlarmsHandler extends ValidateTokenAndReSetAbstracHandler<ExportStationOverTimeAlarmsCommand, HttpCommandResultWithData> {

    @Autowired
    private IStationOverTimeAlarmsService service;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportStationOverTimeAlarmsHandler() {
        super(ExportStationOverTimeAlarmsCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportStationOverTimeAlarmsHandler(Class<ExportStationOverTimeAlarmsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportStationOverTimeAlarmsCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo<QueryStationOverTimeAlarmsDto> datas = (PagingInfo<QueryStationOverTimeAlarmsDto>) service.queryStationOverTimeAlarms(command, isQueryAll).getData();
        return reportService.downLoad(datas.getList(), command, ReportConfigConstants.stationOverTimeAlarmsConfig, reportProperty);
    }
}
