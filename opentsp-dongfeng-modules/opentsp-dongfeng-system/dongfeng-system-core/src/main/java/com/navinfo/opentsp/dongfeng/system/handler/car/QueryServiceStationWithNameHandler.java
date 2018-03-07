package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryServiceStationCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.ServiceStationOutdto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryServiceStationWithNameHandler extends ValidateTokenAndReSetAbstracHandler<QueryServiceStationCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryServiceStationWithNameHandler(){
		super(QueryServiceStationCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryServiceStationWithNameHandler(Class<QueryServiceStationCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryServiceStationCommand command) {

		command.setPage_size("5");
		PagingInfo<ServiceStationOutdto> page = carService.queryServiceStationWithName(command);
		
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(page);
		
		return result;
	}

}
