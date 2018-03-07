package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.*;

public class QueryCarCommand extends BaseCommand<HttpCommandResultWithData>{

	@Converter(target="carBean")
	private String car;

	private QueryCarIndto carBean;
	
	@Converter(target="carDetailBean")
	private String detail;
	
	private QueryCarDetailIndto carDetailBean;

	@Converter(target="terminalBean")
	private String terminal;

	private TerminalIndto terminalBean;

	@Converter(target="controllerBean")
	private String controller;
	
	private TerminalIndto controllerBean;

	private TerminalIndto TerminalIndto;

	@Converter(target="dealerBean")
	private String dealer;

	private QueryDealerIndto dealerBean;

	@Converter(target="businessBean")
	private String business;

	private BusinessIndto businessBean;

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public TerminalIndto getTerminalBean() {
		return terminalBean;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public TerminalIndto getTerminalIndto() {
		return TerminalIndto;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public QueryDealerIndto getDealerBean() {
		return dealerBean;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public BusinessIndto getBusinessBean() {
		return businessBean;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public TerminalIndto getControllerBean() {
		return controllerBean;
	}

	public QueryCarIndto getCarBean() {
		return carBean;
	}

	public QueryCarDetailIndto getCarDetailBean() {
		return carDetailBean;
	}

	public void setCarBean(QueryCarIndto carBean) {
		this.carBean = carBean;
	}

	public void setCarDetailBean(QueryCarDetailIndto carDetailBean) {
		this.carDetailBean = carDetailBean;
	}

	public void setTerminalBean(TerminalIndto terminalBean) {
		this.terminalBean = terminalBean;
	}

	public void setControllerBean(TerminalIndto controllerBean) {
		this.controllerBean = controllerBean;
	}

	public void setTerminalIndto(TerminalIndto terminalIndto) {
		TerminalIndto = terminalIndto;
	}

	public void setDealerBean(QueryDealerIndto dealerBean) {
		this.dealerBean = dealerBean;
	}

	public void setBusinessBean(BusinessIndto businessBean) {
		this.businessBean = businessBean;
	}

}
