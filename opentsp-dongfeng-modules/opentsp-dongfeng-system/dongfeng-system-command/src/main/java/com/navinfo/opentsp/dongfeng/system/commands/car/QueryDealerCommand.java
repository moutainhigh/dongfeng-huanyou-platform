package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.DealerIndto;

/**
 * 经销商查询Command
 * @author Administrator
 *
 */
public class QueryDealerCommand extends BaseCommand<HttpCommandResultWithData>{

	@Converter(target="dealerBean")
	private String dealer;
	
	private DealerIndto dealerBean;
	
	private String fruzzy;
	
	private Integer govCodeCity;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public DealerIndto getDealerBean() {
		return dealerBean;
	}

	public String getFruzzy() {
		return fruzzy;
	}

	public void setFruzzy(String fruzzy) {
		this.fruzzy = fruzzy;
	}

	public Integer getGovCodeCity() {
		return govCodeCity;
	}

	public void setGovCodeCity(Integer govCodeCity) {
		this.govCodeCity = govCodeCity;
	}
}
