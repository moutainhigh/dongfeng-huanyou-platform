package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryTerminalWithRelevanceCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryTerminalWithFruzzyHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalWithRelevanceCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryTerminalWithFruzzyHandler(){
		super(QueryTerminalWithRelevanceCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryTerminalWithFruzzyHandler(Class<QueryTerminalWithRelevanceCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryTerminalWithRelevanceCommand command) {

		command.setPage_size("5");
		PagingInfo<TerminalIndto> page = carService.queryTerminalWithIdOrSim(command);
		
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(page);
		
		return result;
	}

}
