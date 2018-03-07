package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.*;

import java.math.BigInteger;

public class UpdateCarCommand extends BaseCommand<CommonResult>{

	@Converter(target="carBean")
	private String car;
	
	private CarIndto carBean;
	
	@Converter(target="carDetailBean")
	private String detail;
	
	private CarDetaildto carDetailBean;
	
	@Converter(target="terminalBean")
	private String terminal;
	
	private TerminalIndto terminalBean;
	
	@Converter(target="controllerBean")
	private String controller;
	
	private ControllerIndto controllerBean;

	/**
	 * 经销商对象
	 */
	@Converter(target="dealerBean")
	private String dealer;
	
	private DealerIndto dealerBean;
	
	@Converter(target="businessBean")
	private String business;
	
	private BusinessIndto businessBean;
	
	@Converter(target="saleBean")
	private String sale;
	
	private SaleIndto saleBean;
	
	/**
	 * 服务站对象
	 */
	@Converter(target="stationBean")
	private String station;
	
	private ServiceStationIndto stationBean;
	
	/**
	 * 与车辆绑定的终端ID
	 */
	private BigInteger terminalOrgId;
	
	@Override
	public Class<? extends CommonResult> getResultType() {
		return CommonResult.class;
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

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public CarIndto getCarIndto() {
		return carBean;
	}

	public TerminalIndto getTerminalIndto() {
		return terminalBean;
	}

	public ControllerIndto getControllerIndto() {
		return controllerBean;
	}

	public DealerIndto getDealerIndto() {
		return dealerBean;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public BusinessIndto getBusinessIndto() {
		return businessBean;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public SaleIndto getSaleIndto() {
		return saleBean;
	}


	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public CarDetaildto getCarDetailBean() {
		return carDetailBean;
	}

	public ServiceStationIndto getStationBean() {
		return stationBean;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public BigInteger getTerminalOrgId() {
		return terminalOrgId;
	}

	public void setTerminalOrgId(BigInteger terminalOrgId) {
		this.terminalOrgId = terminalOrgId;
	}
	
}
