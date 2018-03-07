package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryRSerpartAppointmentCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.IRSerpartAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务站服务配件预约查询
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class QueryRSerpartAppointmentHandler
		extends ValidateTokenAndReSetAbstracHandler<QueryRSerpartAppointmentCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(QueryNsoodleReportHandler.class);

	@Autowired
	private IRSerpartAppointmentService reportService;

	public QueryRSerpartAppointmentHandler() {
		super(QueryRSerpartAppointmentCommand.class, HttpCommandResultWithData.class);
	}

	public QueryRSerpartAppointmentHandler(Class<QueryRSerpartAppointmentCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryRSerpartAppointmentCommand command) {
		return reportService.queryReportData(command, false);
	}

}
