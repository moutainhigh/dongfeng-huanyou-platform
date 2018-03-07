package com.navinfo.opentsp.dongfeng.common.command;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

public class CheckServiceHealthCommand extends BaseCommand<HttpCommandResultWithData> {

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "QueryHealthCommand{}";
	}
}