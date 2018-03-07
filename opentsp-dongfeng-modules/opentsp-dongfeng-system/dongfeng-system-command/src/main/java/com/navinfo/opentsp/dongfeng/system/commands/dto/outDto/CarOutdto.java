package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

public class CarOutdto {
	
	//车辆相关
	private VechicleOutdto car;
	
	private CarDetailOutdto detail;
	
	//终端一体机
	private TerminalOutdto terminal;
	
	//FK控制器
	private TerminalOutdto controller;
	
	//经销商
	private DealerOutdto dealer;
	
	//所属用户
	private BusinessOutdto business;
	
	//销售
	private SaleOutdto sale;

	public CarDetailOutdto getDetail() {
		return detail;
	}

	public void setDetail(CarDetailOutdto detail) {
		this.detail = detail;
	}

	public VechicleOutdto getCar() {
		return car;
	}

	public void setCar(VechicleOutdto car) {
		this.car = car;
	}

	public TerminalOutdto getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalOutdto terminal) {
		this.terminal = terminal;
	}

	public TerminalOutdto getController() {
		return controller;
	}

	public void setController(TerminalOutdto controller) {
		this.controller = controller;
	}

	public DealerOutdto getDealer() {
		return dealer;
	}

	public void setDealer(DealerOutdto dealer) {
		this.dealer = dealer;
	}

	public BusinessOutdto getBusiness() {
		return business;
	}

	public void setBusiness(BusinessOutdto business) {
		this.business = business;
	}

	public SaleOutdto getSale() {
		return sale;
	}

	public void setSale(SaleOutdto sale) {
		this.sale = sale;
	}
}
