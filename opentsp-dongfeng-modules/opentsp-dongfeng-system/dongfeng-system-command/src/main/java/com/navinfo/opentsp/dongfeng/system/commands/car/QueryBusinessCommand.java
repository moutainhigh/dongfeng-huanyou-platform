package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BusinessIndto;

public class QueryBusinessCommand extends BaseCommand<HttpCommandResultWithData>{

	@Converter(target="busniessBean")
	private String business;
	
	private BusinessIndto busniessBean;
	
	private String fruzzy;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public BusinessIndto getBusniessBean() {
		return busniessBean;
	}

	public String getFruzzy() {
		return fruzzy;
	}

	public void setFruzzy(String fruzzy) {
		this.fruzzy = fruzzy;
	}
	
}
