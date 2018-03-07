package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

public class QueryDealerWithRelevanceCommand extends BaseCommand<HttpCommandResultWithData> {

	/**
	 * 经销商名称
	 */
	private String name;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}