package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportCarCountCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.general.QueryCarCountDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryCarCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 导出全车次
 *
 * @wenya
 * @create 2017-04-05 15:54
 **/
@Component
public class ExportCarCountHandler extends ValidateTokenAndReSetAbstracHandler<ExportCarCountCommand , HttpCommandResultWithData> {
    @Autowired
    private IQueryCarCountService carCountService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportCarCountHandler(){super(ExportCarCountCommand.class, HttpCommandResultWithData.class);}

    protected ExportCarCountHandler(Class<ExportCarCountCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportCarCountCommand command) {
        HttpCommandResultWithData datas = carCountService.exportCarCount(command);
        List<QueryCarCountDto> queryCarCountDto = (List<QueryCarCountDto>) datas.getData();

        return reportService.downLoad(queryCarCountDto, command, ReportConfigConstants.carCountReport, reportProperty);
    }
}
