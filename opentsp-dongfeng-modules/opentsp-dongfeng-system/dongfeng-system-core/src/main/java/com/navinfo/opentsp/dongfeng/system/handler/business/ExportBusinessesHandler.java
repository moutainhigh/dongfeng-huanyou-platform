package com.navinfo.opentsp.dongfeng.system.handler.business;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.system.commands.business.ExportBusinessesCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.BusinessOutdto;
import com.navinfo.opentsp.dongfeng.system.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.system.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class ExportBusinessesHandler extends ValidateTokenAndReSetAbstracHandler<ExportBusinessesCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportBusinessesHandler.class);

    @Autowired
    private IBusinessService businessService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private PropertyConfig reportProperty;

    public ExportBusinessesHandler() {
        super(ExportBusinessesCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportBusinessesHandler(Class<ExportBusinessesCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }
    @Override
    protected HttpCommandResultWithData businessHandle(ExportBusinessesCommand command) {
        HttpCommandResultWithData rs = businessService.queryBusinesses(command,true);
        List<BusinessOutdto> list = null;
        if(rs.getData() instanceof PagingInfo){
            PagingInfo pagingInfo = (PagingInfo)rs.getData();
            list = pagingInfo.getList();
        }else{
            list = (List<BusinessOutdto>)rs.getData();
        }
        //导出数据
        return reportService.downLoad(list, command, ReportConfigConstants.businessesConfig, reportProperty);
    }
}