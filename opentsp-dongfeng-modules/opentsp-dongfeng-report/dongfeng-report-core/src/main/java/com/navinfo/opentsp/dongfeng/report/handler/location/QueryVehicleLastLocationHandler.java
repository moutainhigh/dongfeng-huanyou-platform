package com.navinfo.opentsp.dongfeng.report.handler.location;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.location.QueryLastLocationCommand;
import com.navinfo.opentsp.dongfeng.report.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.report.dto.location.VehicleLastLocationDto;
import com.navinfo.opentsp.dongfeng.report.service.location.ILastLocationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
@Component
public class QueryVehicleLastLocationHandler extends ValidateTokenAndReSetAbstracHandler<QueryLastLocationCommand, HttpCommandResultWithData> {

    private static final String FIRST_PAGE_NO_INDEX = "0";

    @Autowired
    private ILastLocationService service;

    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    public QueryVehicleLastLocationHandler() {
        super(QueryLastLocationCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryVehicleLastLocationHandler(Class<QueryLastLocationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryLastLocationCommand command) {
        HttpCommandResultWithData response = service.query(command, false);
        Map<String, Object> datas = (Map<String, Object>) response.getData();
        List<VehicleLastLocationDto> errors = (List<VehicleLastLocationDto>) datas.get("errors");
        String url = "";
        // In the home page, if the vin is not empty and the error is not empty, then Generate error report
        if (command.getPage_number().equals(FIRST_PAGE_NO_INDEX) && StringUtil.isNotEmpty(command.getVin())
                && CollectionUtils.isNotEmpty(errors)) {
            url = reportService.downLoad(errors, command, ReportConfigConstants.errorLastLocationConfig, reportProperty).getData().toString();
        }
        datas.put("errors", url);
        response.setData(datas);
        return response;
    }
}
