package com.navinfo.opentsp.dongfeng.openapi.commands.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;

public class QueryOnlineCarDistributionCommand extends BaseOpenApiCommand {

	@Override
	public String toString() {
		return "QueryOnlineCarDistributionCommand{}";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
