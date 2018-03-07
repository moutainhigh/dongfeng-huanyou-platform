package com.navinfo.opentsp.dongfeng.report.handler.oil;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.oil.ExportOilCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.service.oil.IOilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.EXPORT_STATISTIC_OIL_ERROR;


/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-01
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportOilHandler extends ValidateTokenAndReSetAbstracHandler<ExportOilCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportOilHandler.class);

    public ExportOilHandler() {
        super(ExportOilCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportOilHandler(Class<ExportOilCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    @Autowired
    private IOilService oilService;

    @Override
    protected HttpCommandResultWithData businessHandle(ExportOilCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            //获取导出标志
            boolean isPaging = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_CURRENT_FLAG);
            //查询数据
            PagingInfo pagingInfo = oilService.queryOil(command, isPaging);
            //导出数据
            result = reportService.downLoad(pagingInfo.getList(), command, ReportConfigConstants.oilReport, reportProperty);
        } catch (Exception e) {
            logger.error(EXPORT_STATISTIC_OIL_ERROR.message() , e);
            result.fillResult(EXPORT_STATISTIC_OIL_ERROR);
        }
        return result;
    }
}