package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryNsoodleReportCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.INsoodleReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 未售车辆出经销商库异常报表查询
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class QueryNsoodleReportHandler extends ValidateTokenAndReSetAbstracHandler<QueryNsoodleReportCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(QueryNsoodleReportHandler.class);

	@Autowired
	private INsoodleReportService reportService;

	public QueryNsoodleReportHandler() {
		super(QueryNsoodleReportCommand.class, HttpCommandResultWithData.class);
	}

	public QueryNsoodleReportHandler(Class<QueryNsoodleReportCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryNsoodleReportCommand command) {
		return reportService.queryReportData(command, false);
	}

}
