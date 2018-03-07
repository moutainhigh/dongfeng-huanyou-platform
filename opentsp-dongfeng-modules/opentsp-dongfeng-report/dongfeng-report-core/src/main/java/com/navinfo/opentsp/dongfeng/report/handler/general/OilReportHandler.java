package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryStealOilCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.StealOilDataDto;
import com.navinfo.opentsp.dongfeng.report.service.general.IOilingStealOilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangtianttong on 2018/2/27/031.
 * 异常加油减油点分析
 *
 * @author ztt
 * @version 1.0
 * @date 2018-02-27
 * @modify
 * @copyright Navinfo
 */
@Component
public class OilReportHandler extends ValidateTokenAndReSetAbstracHandler<QueryStealOilCommand, HttpCommandResultWithData> {

    @Autowired
    private IOilingStealOilService oilingStealOilService;

    public OilReportHandler() {
        super(QueryStealOilCommand.class, HttpCommandResultWithData.class);
    }

    protected OilReportHandler(Class<QueryStealOilCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStealOilCommand command) {

        HttpCommandResultWithData response = new HttpCommandResultWithData();

        StealOilDataDto result = oilingStealOilService.getStealOilData(command);

        response.setData(result);

        return response;
    }
}