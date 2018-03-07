package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.CarParamCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.CarParamOutDto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryCarParamWithVechicleTypeCodeHandler extends ValidateTokenAndReSetAbstracHandler<CarParamCommand, HttpCommandResultWithData>{

	@Resource
	private ICarService carService;
	
	public QueryCarParamWithVechicleTypeCodeHandler() {
		super(CarParamCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryCarParamWithVechicleTypeCodeHandler(Class<CarParamCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(CarParamCommand command) {

		CarParamOutDto carModel = carService.queryCarParam(command);
		
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(carModel);
		
		return result;
	}

}
