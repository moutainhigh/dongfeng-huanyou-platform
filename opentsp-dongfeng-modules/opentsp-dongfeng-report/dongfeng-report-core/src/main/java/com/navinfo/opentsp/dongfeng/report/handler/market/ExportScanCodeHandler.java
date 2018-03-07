package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.market.ExportScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.market.QueryScanCodeDto;
import com.navinfo.opentsp.dongfeng.report.service.market.IScanCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车辆扫码导出
 *
 * @author wt
 * @version 1.0
 * @date 2017-03-31
 * @modify
 */
@Component
public class ExportScanCodeHandler extends ValidateTokenAndReSetAbstracHandler<ExportScanCodeCommand, HttpCommandResultWithData> {

    @Autowired
    private IScanCodeService service;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportScanCodeHandler() {
        super(ExportScanCodeCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportScanCodeHandler(Class<ExportScanCodeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportScanCodeCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo<QueryScanCodeDto> datas = (PagingInfo<QueryScanCodeDto>) service.queryVehicleScanCodeInfos(command, isQueryAll).getData();
        return reportService.downLoad(datas.getList(), command, ReportConfigConstants.scanCodeConfig, reportProperty);
    }
}
