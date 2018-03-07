package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.DealerIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;

import java.math.BigInteger;


public class QueryTerminalCommand extends BaseCommand<HttpCommandResultWithData>{

	@Converter(target="dealerBean")
	private String dealer;
	
	private DealerIndto dealerBean;
	
	@Converter(target="terminalBea")
	private String terminal;
	
	private TerminalIndto terminalBea;
	
	private String fruzzy;
	
	private BigInteger teamId;
	
	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public TerminalIndto getTerminalIndto() {
		return terminalBea;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public DealerIndto getDealerIndto() {
		return dealerBean;
	}
	public String getFruzzy() {
		return fruzzy;
	}
	public void setFruzzy(String fruzzy) {
		this.fruzzy = fruzzy;
	}
	public BigInteger getTeamId() {
		return teamId;
	}
	public void setTeamId(BigInteger teamId) {
		this.teamId = teamId;
	}
	
}
