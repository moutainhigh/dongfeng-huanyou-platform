package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.*;

public class DownloadCarCommand extends BaseCommand<HttpCommandResultWithData>{

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
	private String cotroller;
	
	private TerminalIndto controllerBean;

	private TerminalIndto TerminalIndto;

	@Converter(target="dealerBean")
	private String dealer;

	private QueryDealerIndto dealerBean;

	@Converter(target="businessBean")
	private String business;

	private BusinessIndto businessBean;
	
	
	private int downloadFlag;
	
	//邮件地址
	private String email;
	
	//邮件主题
	private String emailSubject;
	//邮件内容
	private String emailContent;
	
	//excel sheet页名称
	private String sheetName;
	
	private Integer totalNumber;
	
	private String metadata;
	
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

	public String getCotroller() {
		return cotroller;
	}

	public void setCotroller(String cotroller) {
		this.cotroller = cotroller;
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

	public int getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(int downloadFlag) {
		this.downloadFlag = downloadFlag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
}
