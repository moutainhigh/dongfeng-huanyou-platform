package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

public class QueryTerminalWithRelevanceCommand extends BaseCommand<HttpCommandResultWithData> {

	private Long dealerId;
	/**
	 * 终端ID或SIM
	 */
	private String fruzzy;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public String getFruzzy() {
		return fruzzy;
	}
	public void setFruzzy(String fruzzy) {
		this.fruzzy = fruzzy;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

}
