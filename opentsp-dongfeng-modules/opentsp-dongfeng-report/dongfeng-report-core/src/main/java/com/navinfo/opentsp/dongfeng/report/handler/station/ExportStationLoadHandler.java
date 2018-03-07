package com.navinfo.opentsp.dongfeng.report.handler.station;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.station.ExportStationLoadCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.station.QueryStationLoadDto;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationLoadService;
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
public class ExportStationLoadHandler extends ValidateTokenAndReSetAbstracHandler<ExportStationLoadCommand, HttpCommandResultWithData> {

    @Autowired
    private IStationLoadService service;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportStationLoadHandler() {
        super(ExportStationLoadCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportStationLoadHandler(Class<ExportStationLoadCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportStationLoadCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo<QueryStationLoadDto> datas = (PagingInfo<QueryStationLoadDto>) service.queryStationLoadInfos(command, isQueryAll).getData();
        return reportService.downLoad(datas.getList(), command, ReportConfigConstants.stationLoadConfig, reportProperty);
    }
}
