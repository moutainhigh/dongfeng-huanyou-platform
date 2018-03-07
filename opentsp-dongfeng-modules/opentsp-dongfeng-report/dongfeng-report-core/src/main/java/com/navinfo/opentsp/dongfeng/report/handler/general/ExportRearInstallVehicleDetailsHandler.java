package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportRearInstallVehicleDetailsCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryRearInstallVehicleDetailsDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryRearInstallVehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 后装车辆详情信息报表
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportRearInstallVehicleDetailsHandler extends ValidateTokenAndReSetAbstracHandler<ExportRearInstallVehicleDetailsCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryRearInstallVehicleInfoService service;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportRearInstallVehicleDetailsHandler() {
        super(ExportRearInstallVehicleDetailsCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportRearInstallVehicleDetailsHandler(Class<ExportRearInstallVehicleDetailsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportRearInstallVehicleDetailsCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo<QueryRearInstallVehicleDetailsDto> datas = (PagingInfo<QueryRearInstallVehicleDetailsDto>) service.queryVehicleDetails(command, isQueryAll).getData();
        return reportService.downLoad(datas.getList(), command, ReportConfigConstants.rearInstallVehicleReport, reportProperty);
    }
}
