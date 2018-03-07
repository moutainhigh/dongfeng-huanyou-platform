package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QueryTerminalHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryTerminalHandler() {
		super(QueryTerminalCommand.class , HttpCommandResultWithData.class);
	}
	protected QueryTerminalHandler(Class<QueryTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryTerminalCommand command) {

		HttpCommandResultWithData result = null;
		
//		if (null == command) {
//			result = new HttpCommandResultWithData();
//			result.setResultCode(ReturnCode.SERVER_ERROR.code());
//			result.setMessage("dealer`s id must can not  be null .");
//			return result;
//		}
		
		if (null == command.getDealerIndto() || null == command.getDealerIndto().getId()) {
			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage("dealer`s id must can not  be null .");
			return result;
		}

		PagingInfo<TerminalIndto> page = carService.sqlPageTerminal(command);
		result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setMessage(ReturnCode.OK.message());
		result.setData(page);
		return result;
	}
	

}
