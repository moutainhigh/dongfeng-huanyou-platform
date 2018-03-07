package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryRSerpartAppointmentCommand;

public interface IRSerpartAppointmentService {

	@SuppressWarnings("rawtypes")
	public HttpCommandResultWithData queryReportData(QueryRSerpartAppointmentCommand command, boolean isExport);
}
