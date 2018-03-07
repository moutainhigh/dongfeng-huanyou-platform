package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarTypeCommand;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryCarTypeHanlder extends ValidateTokenAndReSetAbstracHandler<QueryCarTypeCommand, HttpCommandResultWithData>
{

	@Resource
	private ICarService carService;
	
	public QueryCarTypeHanlder() {
		super(QueryCarTypeCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryCarTypeHanlder(Class<QueryCarTypeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryCarTypeCommand command) {
		
		HttpCommandResultWithData result = null;
		try {
			Integer carType = carService.queryCarType(command);
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.OK.code());
			result.setData(null != carType ? carType.toString() : "");
		} catch (BaseServiceException e) {
			logger.error("QueryCarTypeHanlder ====> error : ", e);
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage(e.getExceptionMessage());
		}
		return result;
	}
}
