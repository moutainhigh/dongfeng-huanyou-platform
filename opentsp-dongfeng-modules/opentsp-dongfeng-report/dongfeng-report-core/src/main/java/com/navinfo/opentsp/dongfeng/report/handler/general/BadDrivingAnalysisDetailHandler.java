package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingAnalysisDetailCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingAnalysisDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.BadDrivingChartDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IBadDrivingAnalysisDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 不良驾驶行为分析详情接口
 * Created by zhangtiantong on 2018/2/28/028.
 */
@Component
public class BadDrivingAnalysisDetailHandler extends ValidateTokenAndReSetAbstracHandler<BadDrivingAnalysisDetailCommand, HttpCommandResultWithData> {


    @Autowired
    private IBadDrivingAnalysisDetailService badDrivingAnalysisDetailService;

    public BadDrivingAnalysisDetailHandler() {
        super(BadDrivingAnalysisDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected BadDrivingAnalysisDetailHandler(Class<BadDrivingAnalysisDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(BadDrivingAnalysisDetailCommand command) {

        HttpCommandResultWithData response = new HttpCommandResultWithData();
        BadDrivingChartDto result = this.badDrivingAnalysisDetailService.getBadDrivingInfo(command);
        response.setData(result);
        response.setResultCode(ReturnCode.OK.code());
        response.setMessage(ReturnCode.OK.message());
        return response;
    }
}