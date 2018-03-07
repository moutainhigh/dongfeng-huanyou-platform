package com.navinfo.opentsp.dongfeng.report.handler.oil;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.oil.ExportOilCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.ExportTireCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.service.oil.IOilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.EXPORT_STATISTIC_OIL_ERROR;


/**
 * @author sunlin
 * @version 1.0
 * @date 2018-03-05
 */
@Component
public class ExportTireDetailHandler extends ValidateTokenAndReSetAbstracHandler<ExportTireCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportTireDetailHandler.class);

    public ExportTireDetailHandler() {
        super(ExportTireCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportTireDetailHandler(Class<ExportTireCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    @Autowired
    private IOilService oilService;

    @Override
    protected HttpCommandResultWithData businessHandle(ExportTireCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            //获取导出标志
            boolean isPaging = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_CURRENT_FLAG);
            //查询数据
            PagingInfo pagingInfo = oilService.queryTireInfo(command, isPaging);
            //导出数据
            result = reportService.downLoad(pagingInfo.getList(), command, ReportConfigConstants.tireReport, reportProperty);
        } catch (Exception e) {
            logger.error(EXPORT_STATISTIC_OIL_ERROR.message() , e);
            result.fillResult(EXPORT_STATISTIC_OIL_ERROR);
        }
        return result;
    }
}