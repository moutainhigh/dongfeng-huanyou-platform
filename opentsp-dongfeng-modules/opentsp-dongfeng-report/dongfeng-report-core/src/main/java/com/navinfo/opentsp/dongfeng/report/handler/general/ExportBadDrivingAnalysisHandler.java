package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportBadDrivingAnalysisCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingAnalysisDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IBadDrivingAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ZHANGTIANTONG on 2018/3/5/005.
 */
@Component
public class ExportBadDrivingAnalysisHandler extends ValidateTokenAndReSetAbstracHandler<ExportBadDrivingAnalysisCommand, HttpCommandResultWithData> {


    @Autowired
    private IBadDrivingAnalysisService badDrivingCharService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportBadDrivingAnalysisHandler() {
        super(ExportBadDrivingAnalysisCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportBadDrivingAnalysisHandler(Class<ExportBadDrivingAnalysisCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportBadDrivingAnalysisCommand command) {

        HttpCommandResultWithData response = new HttpCommandResultWithData();

        List<BadDrivingAnalysisDto> exportList;

        //如果导出全部
        if (command.getDownloadFlag() == 1) {

            exportList = this.badDrivingCharService.getAllBadDrivingInfos(command);

        } else {//导出当页
            exportList = this.badDrivingCharService.getBadDrivingInfos(command).getList();
        }
        return reportService.downLoad(exportList, command, ReportConfigConstants.badDrivingAnalysisReportConfig, reportProperty);
    }
}