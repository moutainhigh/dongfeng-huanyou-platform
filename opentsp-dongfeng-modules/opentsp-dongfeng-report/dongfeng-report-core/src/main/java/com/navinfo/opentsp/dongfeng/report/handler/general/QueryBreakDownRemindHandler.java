package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryBreakDownRemindCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.IBreakDownRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 故障汇总报表
 * 
 */
@SuppressWarnings("rawtypes")
@Component
public class QueryBreakDownRemindHandler
		extends ValidateTokenAndReSetAbstracHandler<QueryBreakDownRemindCommand, HttpCommandResultWithData> {

	@Autowired
	private IBreakDownRemindService service;

	public QueryBreakDownRemindHandler() {
		super(QueryBreakDownRemindCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryBreakDownRemindHandler(Class<QueryBreakDownRemindCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryBreakDownRemindCommand command) {
		return service.queryBreakDownRemind(command, false);
	}
}
