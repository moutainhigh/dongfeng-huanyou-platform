package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryDealerWithRelevanceCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.DealerOutdto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryDealerWithNameHandler extends ValidateTokenAndReSetAbstracHandler<QueryDealerWithRelevanceCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryDealerWithNameHandler() {
		super(QueryDealerWithRelevanceCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryDealerWithNameHandler(Class<QueryDealerWithRelevanceCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryDealerWithRelevanceCommand command) {

		command.setPage_size("5");
		PagingInfo<DealerOutdto> page = carService.queryDealerWithName(command);
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(page);
		return result;
	}

}
