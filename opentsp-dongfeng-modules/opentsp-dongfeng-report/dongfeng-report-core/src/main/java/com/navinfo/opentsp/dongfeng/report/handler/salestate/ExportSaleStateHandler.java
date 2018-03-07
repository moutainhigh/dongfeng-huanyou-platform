package com.navinfo.opentsp.dongfeng.report.handler.salestate;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.salestate.ExportSaleStateCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.pojo.salestate.QuerySaleStatePojo;
import com.navinfo.opentsp.dongfeng.report.service.salestate.IQuerySaleStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-04-01
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class ExportSaleStateHandler extends ValidateTokenAndReSetAbstracHandler<ExportSaleStateCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportSaleStateHandler.class);

    @Autowired
    private IQuerySaleStateService iQuerySaleStateService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportSaleStateHandler() {
        super(ExportSaleStateCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportSaleStateHandler(Class<ExportSaleStateCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportSaleStateCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String check = iQuerySaleStateService.checkTimeValid(command);
        if (StringUtil.isNotEmpty(check)) {
            result.setResultCode(ReturnCode.SERVER_ERROR.code());
            result.setMessage(check);
            return result;
        }
        List<QuerySaleStatePojo> saleStates = new ArrayList<QuerySaleStatePojo>();
        switch (command.getDownloadFlag()) {
            case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
                saleStates = iQuerySaleStateService.querySaleStateForExport(command);
                break;
            }
            case ReportConfigConstants.EXPORT_ALL_FLAG: {
                saleStates = iQuerySaleStateService.queryAllSaleStateForExport(command);
                break;
            }
            default:
                // do nothing
        }

        return reportService.downLoad(saleStates, command, ReportConfigConstants.saleStateReport, reportProperty);
    }
}