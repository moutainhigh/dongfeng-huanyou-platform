package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryInvoiceNumberCommand;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class QueryInvoiceNumberHandler extends ValidateTokenAndReSetAbstracHandler<QueryInvoiceNumberCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryInvoiceNumberHandler() {
		super(QueryInvoiceNumberCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryInvoiceNumberHandler(Class<QueryInvoiceNumberCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryInvoiceNumberCommand command) {
		
		HttpCommandResultWithData result = null;
		
		if (null == command) {
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage("QueryInvoiceNumberCommand can not be null .");
			return result;
		}
		
		if (StringUtils.isEmpty(command.getInvoiceNum())) {

			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage("InvoiceNumber can not be null .");
			return result;
		}
		
		boolean hasInvoiceNum = carService.hasInvoiceNumber(command);
		
		if (hasInvoiceNum) {
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("发票号已存在.");
			return result;
		} else {
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("发票号可以使用.");
			return result;
		}
	}

}
