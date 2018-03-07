package com.navinfo.opentsp.dongfeng.report.handler.disklibrary;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.ExportDisklibraryCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.service.disklibrary.IQueryDisklibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class ExportDisklibraryHandler extends ValidateTokenAndReSetAbstracHandler<ExportDisklibraryCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportDisklibraryHandler.class);
    @Autowired
    private PropertyConfig reportProperty;
    @Autowired
    private IReportService reportService;
    @Autowired
    IQueryDisklibraryService iQueryDisklibraryService;

    public ExportDisklibraryHandler() {
        super(ExportDisklibraryCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportDisklibraryHandler(Class<ExportDisklibraryCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportDisklibraryCommand command) {
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo pagingInfo = iQueryDisklibraryService.queryDisklibrary(command, !isQueryAll);
        return reportService.downLoad(pagingInfo.getList(), command, ReportConfigConstants.disklibraryConfig, reportProperty);
    }
}