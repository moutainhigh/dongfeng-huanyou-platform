package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryDealerCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.DealerOutdto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class QueryDealerHandler extends ValidateTokenAndReSetAbstracHandler<QueryDealerCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryDealerHandler() {
		super(QueryDealerCommand.class , HttpCommandResultWithData.class);
	}
	
	protected QueryDealerHandler(Class<QueryDealerCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryDealerCommand command) {
		
		HttpCommandResultWithData result = null;
		
		//获取用户信息,暂时空缺.
//		if (null == command) {
//			result = new HttpCommandResultWithData();
//
//			result.setResultCode(ReturnCode.SERVER_ERROR.code());
//			result.setMessage("QueryDealerCommand must can not be null .");
//			
//			return result;
//		}

		PagingInfo<DealerOutdto> page = carService.sqlPageDealer(command, command.getPage_size());
		
		result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setMessage(ReturnCode.OK.message());
		result.setData(page);
		return result;
	}

}
