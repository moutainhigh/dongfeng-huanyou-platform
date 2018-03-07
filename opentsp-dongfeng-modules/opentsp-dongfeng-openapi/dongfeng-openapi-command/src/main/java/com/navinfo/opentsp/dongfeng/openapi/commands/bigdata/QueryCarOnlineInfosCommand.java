package com.navinfo.opentsp.dongfeng.openapi.commands.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;

/**
 * 
 * @author xltianc.zh
 *
 */
public class QueryCarOnlineInfosCommand extends BaseOpenApiCommand {

	@Override
	public String toString() {
		return "QueryCarOnlineInfosCommand{}";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}
}
