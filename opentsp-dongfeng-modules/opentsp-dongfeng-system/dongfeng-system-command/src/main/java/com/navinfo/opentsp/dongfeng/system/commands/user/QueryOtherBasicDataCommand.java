package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

public class QueryOtherBasicDataCommand extends BaseCommand<HttpCommandResultWithData> {

    private String type;
    
    private Integer dataCode;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDataCode() {
		return dataCode;
	}

	public void setDataCode(Integer dataCode) {
		this.dataCode = dataCode;
	}

}
