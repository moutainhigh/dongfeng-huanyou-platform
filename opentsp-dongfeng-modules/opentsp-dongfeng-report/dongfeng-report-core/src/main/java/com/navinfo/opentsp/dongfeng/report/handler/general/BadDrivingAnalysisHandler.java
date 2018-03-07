package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.report.commands.general.BadDrivingAnalysisCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.IBadDrivingAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 不良驾驶行为分析接口
 * Created by zhangtiantong on 2018/2/28/028.
 */
@Component
public class BadDrivingAnalysisHandler extends ValidateTokenAndReSetAbstracHandler<BadDrivingAnalysisCommand, HttpCommandResultWithData> {

    @Autowired
    private IBadDrivingAnalysisService badDrivingCharService;

    protected BadDrivingAnalysisHandler(Class<BadDrivingAnalysisCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    public BadDrivingAnalysisHandler() {
        super(BadDrivingAnalysisCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(BadDrivingAnalysisCommand command) {

        HttpCommandResultWithData response = new HttpCommandResultWithData();
        PagingInfo result = this.badDrivingCharService.getBadDrivingInfos(command);
        response.setData(result);
        response.setResultCode(ReturnCode.OK.code());
        response.setMessage(ReturnCode.OK.message());
        return response;
    }

}