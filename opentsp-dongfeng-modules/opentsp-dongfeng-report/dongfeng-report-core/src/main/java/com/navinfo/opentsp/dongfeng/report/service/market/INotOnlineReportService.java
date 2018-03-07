package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryNotOnlineReportCommand;

public interface INotOnlineReportService {
	@SuppressWarnings("rawtypes")
	public HttpCommandResultWithData queryReportData(QueryNotOnlineReportCommand command, boolean isExport);
}
