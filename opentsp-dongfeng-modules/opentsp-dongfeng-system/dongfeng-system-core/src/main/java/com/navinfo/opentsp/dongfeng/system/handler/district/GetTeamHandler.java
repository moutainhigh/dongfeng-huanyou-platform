package com.navinfo.opentsp.dongfeng.system.handler.district;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.district.GetTeamCommand;
import com.navinfo.opentsp.dongfeng.system.service.IDistrictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/13.
 * 分组查询
 */
@Component
public class GetTeamHandler extends ValidateTokenAndReSetAbstracHandler<GetTeamCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(GetTeamCommand.class);

    @Autowired
    private IDistrictService districtService;

    public GetTeamHandler() {
        super(GetTeamCommand.class, HttpCommandResultWithData.class);
    }

    protected GetTeamHandler(Class<GetTeamCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(GetTeamCommand command) {
        HttpCommandResultWithData result =  districtService.getTeam(command);
        return result;
    }
}
