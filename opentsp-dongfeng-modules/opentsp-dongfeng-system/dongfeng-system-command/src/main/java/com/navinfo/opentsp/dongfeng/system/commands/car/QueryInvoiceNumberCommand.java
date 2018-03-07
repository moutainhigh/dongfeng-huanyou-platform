package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

import java.math.BigInteger;

public class QueryInvoiceNumberCommand extends BaseCommand<HttpCommandResultWithData>{

	private BigInteger carId;
	
	private String invoiceNum;
	
	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public BigInteger getCarId() {
		return carId;
	}

	public void setCarId(BigInteger carId) {
		this.carId = carId;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

}
