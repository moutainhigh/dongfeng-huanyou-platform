package com.navinfo.opentsp.dongfeng.report.handler.product;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.product.ExportLSCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.product.QueryLSDto;
import com.navinfo.opentsp.dongfeng.report.service.product.IQueryLSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @wenya
 * @create 2017-04-05 14:56
 **/
@Component
public class ExportLSHandler extends ValidateTokenAndReSetAbstracHandler<ExportLSCommand , HttpCommandResultWithData> {
    @Autowired
    private IQueryLSService queryLSService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public  ExportLSHandler(){
        super(ExportLSCommand.class,HttpCommandResultWithData.class);
    }

    protected ExportLSHandler(Class<ExportLSCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportLSCommand command) {
        List<QueryLSDto> QueryLSDtos = new ArrayList<QueryLSDto>();
            switch (command.getDownloadFlag()) {
                case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
                    PagingInfo<QueryLSDto> datas = getPageList(command, false);
                    QueryLSDtos = datas.getList();
                    break;
                }
                case ReportConfigConstants.EXPORT_ALL_FLAG: {
                    PagingInfo<QueryLSDto> datas = getPageList(command, true);
                    QueryLSDtos = datas.getList();
                    break;
                }
                default:
            }

        HttpCommandResultWithData result = reportService.downLoad(QueryLSDtos, command, ReportConfigConstants.lsReport, reportProperty);
        return result;
    }
    private PagingInfo<QueryLSDto> getPageList(ExportLSCommand command, boolean isExport) {
        HttpCommandResultWithData queryResult = queryLSService.queryLs(command, isExport);
        if (queryResult == null) {
            queryResult = new HttpCommandResultWithData();
            PagingInfo dataList = new PagingInfo();
            dataList.setList(Collections.emptyList());
            dataList.setTotal(0L);
            queryResult.setData(dataList);
        }
        PagingInfo<QueryLSDto> info = (PagingInfo<QueryLSDto>) queryResult.getData();
        if (info == null) {
            info = new PagingInfo();
            info.setList(new ArrayList<QueryLSDto>());
            info.setTotal(0L);
            queryResult.setData(info);
        }
        return info;
    }
}
