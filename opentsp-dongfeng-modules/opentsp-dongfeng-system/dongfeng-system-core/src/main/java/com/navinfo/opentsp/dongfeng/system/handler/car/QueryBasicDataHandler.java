package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryBasicDataCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BasicDataIndto;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class QueryBasicDataHandler extends ValidateTokenAndReSetAbstracHandler<QueryBasicDataCommand, HttpCommandResultWithData> {

	@Resource
	private ICarService carService;
	
	public QueryBasicDataHandler() {
		super(QueryBasicDataCommand.class , HttpCommandResultWithData.class);
	}
	protected QueryBasicDataHandler(Class<QueryBasicDataCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(QueryBasicDataCommand command) {
		
		HttpCommandResultWithData result = null;
		
//		if (null == command) {
//			result = new HttpCommandResultWithData();
//			result.setResultCode(ReturnCode.SERVER_ERROR.code());
//			result.setMessage("QueryBasicDataCommand must can not be null .");
//			return result;
//		}
//		
		if (null == command.getServerVersion()) {

			result = new HttpCommandResultWithData();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage("SystemVersion must can not be null .");
			return result;
		}
		Map<String , List<BasicDataIndto>> map = carService.queryBasicData(command);
		
		result = new HttpCommandResultWithData();
		result.setResultCode(ReturnCode.OK.code());
		result.setData(map);
		
		return result;
	}

}
