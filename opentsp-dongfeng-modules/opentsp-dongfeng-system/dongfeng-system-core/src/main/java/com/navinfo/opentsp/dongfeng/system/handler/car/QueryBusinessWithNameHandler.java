package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryBusinessWithRelevanceCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BusinessIndto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryBusinessWithNameHandler extends ValidateTokenAndReSetAbstracHandler<QueryBusinessWithRelevanceCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryBusinessWithNameHandler() {
		super(QueryBusinessWithRelevanceCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryBusinessWithNameHandler(Class<QueryBusinessWithRelevanceCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryBusinessWithRelevanceCommand command) {

		PagingInfo<BusinessIndto> page = carService.queryBusinessWithName(command);
		
		command.setPage_size("5");
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(page);
		
		return result;
	}

	
}
