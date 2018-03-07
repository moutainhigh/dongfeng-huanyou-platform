package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarWithRelevanceCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.CarBaseInfoDto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryCarWithChassisHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarWithRelevanceCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryCarWithChassisHandler() {
		super(QueryCarWithRelevanceCommand.class , HttpCommandResultWithData.class);
	}
	protected QueryCarWithChassisHandler(Class<QueryCarWithRelevanceCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryCarWithRelevanceCommand command) {

		command.setPage_size("5");
		PagingInfo<CarBaseInfoDto> page = carService.queryCarWithChassis(command);
		
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(page);
		
		return result;
	}

}
