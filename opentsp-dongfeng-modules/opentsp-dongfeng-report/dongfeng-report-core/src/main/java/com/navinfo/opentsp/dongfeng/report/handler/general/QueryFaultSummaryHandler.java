package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryFaultSummaryCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.IFaultSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 故障汇总报表
 * 
 */
@SuppressWarnings("rawtypes")
@Component
public class QueryFaultSummaryHandler extends ValidateTokenAndReSetAbstracHandler<QueryFaultSummaryCommand, HttpCommandResultWithData> {

	@Autowired
	private IFaultSummaryService faultSummaryService;

	public QueryFaultSummaryHandler() {
		super(QueryFaultSummaryCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryFaultSummaryHandler(Class<QueryFaultSummaryCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryFaultSummaryCommand command) {
		return faultSummaryService.queryFaultSummary(command, false);
	}
}
