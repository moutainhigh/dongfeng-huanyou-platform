package com.navinfo.opentsp.dongfeng.system.handler.basedata;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.system.commands.user.QueryOtherBasicDataCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicDataHandler extends ValidateTokenAndReSetAbstracHandler<QueryOtherBasicDataCommand, HttpCommandResultWithData> {

    @Autowired
    private IBaseDataService baseDataService;
    
	public BasicDataHandler() {
		super(QueryOtherBasicDataCommand.class , HttpCommandResultWithData.class);
	}
	
	protected BasicDataHandler(Class<QueryOtherBasicDataCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryOtherBasicDataCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setData(baseDataService.queryList(Integer.parseInt(command.getType())));
		return result;
	}

}
