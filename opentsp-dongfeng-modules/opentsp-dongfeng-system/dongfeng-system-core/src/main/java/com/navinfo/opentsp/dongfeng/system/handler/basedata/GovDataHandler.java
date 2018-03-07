package com.navinfo.opentsp.dongfeng.system.handler.basedata;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IBaseAreaService;
import com.navinfo.opentsp.dongfeng.system.commands.basedata.GovDataCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 省市编码列表
 *
 * @wenya
 * @create 2017-04-26 15:29
 **/
@Component
public class GovDataHandler extends ValidateTokenAndReSetAbstracHandler<GovDataCommand, HttpCommandResultWithData> {
    @Autowired
    private IBaseAreaService baseAreaService;

    public GovDataHandler() {
        super(GovDataCommand.class, HttpCommandResultWithData.class);
    }

    protected GovDataHandler(Class<GovDataCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(GovDataCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result.setData(baseAreaService.queryAreaByParentCode(command.getParentCode()));
        return result;
    }
}
