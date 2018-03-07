package com.navinfo.opentsp.dongfeng.report.handler.online;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.online.ExportOnlineCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.service.online.IOnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.EXPORT_STATISTIC_ONLINE_ERROR;


/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportOnlineHandler extends ValidateTokenAndReSetAbstracHandler<ExportOnlineCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportOnlineHandler.class);

    public ExportOnlineHandler() {
        super(ExportOnlineCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportOnlineHandler(Class<ExportOnlineCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    @Autowired
    private IOnlineService onlineService;

    @Override
    protected HttpCommandResultWithData businessHandle(ExportOnlineCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            //获取导出标志
            boolean isPaging = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_CURRENT_FLAG);
            //查询数据
            PagingInfo pagingInfo = onlineService.queryOnline(command, isPaging);
            //导出数据
            result = reportService.downLoad(pagingInfo.getList(), command, ReportConfigConstants.onlineReport, reportProperty);
        } catch (Exception e) {
            logger.error(EXPORT_STATISTIC_ONLINE_ERROR.message() , e);
            result.fillResult(EXPORT_STATISTIC_ONLINE_ERROR);
        }
        return result;
    }
}