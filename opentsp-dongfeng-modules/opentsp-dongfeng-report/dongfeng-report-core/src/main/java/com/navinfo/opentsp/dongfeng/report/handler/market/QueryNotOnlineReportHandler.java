package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryNotOnlineReportCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.INotOnlineReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车辆在库未上线报表查询
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class QueryNotOnlineReportHandler extends ValidateTokenAndReSetAbstracHandler<QueryNotOnlineReportCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(QueryNotOnlineReportHandler.class);

	@Autowired
	private INotOnlineReportService reportService;

	public QueryNotOnlineReportHandler() {
		super(QueryNotOnlineReportCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryNotOnlineReportHandler(Class<QueryNotOnlineReportCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryNotOnlineReportCommand command) {
		return reportService.queryReportData(command, false);
	}

}
