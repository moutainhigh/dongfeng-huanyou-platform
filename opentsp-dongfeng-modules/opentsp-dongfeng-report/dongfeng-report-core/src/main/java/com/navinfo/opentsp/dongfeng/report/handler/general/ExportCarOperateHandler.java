package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportCarOperateCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryCarOperatePojo;
import com.navinfo.opentsp.dongfeng.report.service.general.ICarOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 运营车况导出
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/27
 */
@Component
public class ExportCarOperateHandler extends ValidateTokenAndReSetAbstracHandler<ExportCarOperateCommand, HttpCommandResultWithData> {
    @Autowired
    private ICarOperateService carOperateService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public ExportCarOperateHandler() {
        super(ExportCarOperateCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportCarOperateHandler(Class<ExportCarOperateCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportCarOperateCommand command) {
        HttpCommandResultWithData rs = carOperateService.queryCarOperate(command);

        List<QueryCarOperatePojo> list = null;
        if(rs.getData() instanceof PagingInfo){
            PagingInfo pagingInfo = (PagingInfo)rs.getData();
            list = pagingInfo.getList();
        }else{
            list = (List<QueryCarOperatePojo>)rs.getData();
        }

        //导出数据
        return reportService.downLoad(list, command, ReportConfigConstants.carOperateReport, reportProperty);
    }
}
