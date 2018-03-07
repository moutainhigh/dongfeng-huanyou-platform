package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryNsoodleReportCommand;

public interface INsoodleReportService {

	@SuppressWarnings("rawtypes")
	public HttpCommandResultWithData queryReportData(QueryNsoodleReportCommand command, boolean isExport);
}
