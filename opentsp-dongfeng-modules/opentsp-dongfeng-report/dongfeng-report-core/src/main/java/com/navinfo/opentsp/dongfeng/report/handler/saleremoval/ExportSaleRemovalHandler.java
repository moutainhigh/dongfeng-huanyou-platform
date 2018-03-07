package com.navinfo.opentsp.dongfeng.report.handler.saleremoval;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.saleremoval.ExportSaleRemovalCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.pojo.saleremoval.QuerySaleRemovalPojo;
import com.navinfo.opentsp.dongfeng.report.service.saleremoval.IQuerySaleRemovalService;
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
public class ExportSaleRemovalHandler extends ValidateTokenAndReSetAbstracHandler<ExportSaleRemovalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportSaleRemovalHandler.class);

    @Autowired
    private IQuerySaleRemovalService iQuerySaleRemovalService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportSaleRemovalHandler() {
        super(ExportSaleRemovalCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportSaleRemovalHandler(Class<ExportSaleRemovalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportSaleRemovalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String check = iQuerySaleRemovalService.checkTimeValid(command);
        if (StringUtil.isNotEmpty(check)) {
            result.setResultCode(ReturnCode.SERVER_ERROR.code());
            result.setMessage(check);
            return result;
        }

        List<QuerySaleRemovalPojo> saleStates = new ArrayList<QuerySaleRemovalPojo>();
        switch (command.getDownloadFlag()) {
            case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
                saleStates = iQuerySaleRemovalService.querySaleRemovalForExport(command);
                break;
            }
            case ReportConfigConstants.EXPORT_ALL_FLAG: {
                saleStates = iQuerySaleRemovalService.queryAllSaleRemovalForExport(command);
                break;
            }
            default:
                // do nothing
        }

        return reportService.downLoad(saleStates, command, ReportConfigConstants.saleRemovalReport, reportProperty);
    }
}