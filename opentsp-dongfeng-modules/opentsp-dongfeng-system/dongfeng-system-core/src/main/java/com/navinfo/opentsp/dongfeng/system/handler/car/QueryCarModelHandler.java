package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarModelCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.CarModelOutDto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryCarModelHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarModelCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryCarModelHandler() {
		super(QueryCarModelCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryCarModelHandler(Class<QueryCarModelCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryCarModelCommand command) {

		CarModelOutDto carModel = carService.queryCarModel(command);
		
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(carModel);
		
		return result;
	}

}
