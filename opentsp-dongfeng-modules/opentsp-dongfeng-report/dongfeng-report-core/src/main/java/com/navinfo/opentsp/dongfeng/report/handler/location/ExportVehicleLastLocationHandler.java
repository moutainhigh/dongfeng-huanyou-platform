package com.navinfo.opentsp.dongfeng.report.handler.location;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.location.ExportLastLocationCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.service.location.ILastLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
@Component
public class ExportVehicleLastLocationHandler extends ValidateTokenAndReSetAbstracHandler<ExportLastLocationCommand, HttpCommandResultWithData> {

    @Autowired
    private ILastLocationService service;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportVehicleLastLocationHandler() {
        super(ExportLastLocationCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportVehicleLastLocationHandler(Class<ExportLastLocationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportLastLocationCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        HttpCommandResultWithData response = service.query(command, isQueryAll);
        Map<String, Object> datas = (Map<String, Object>) response.getData();
        PagingInfo vehicleLocations = (PagingInfo) datas.get("datas");
        if (datas != null) {
            datas.clear();
        }
        return reportService.downLoad(vehicleLocations.getList(), command, ReportConfigConstants.lastLocationConfig, reportProperty);
    }
}
