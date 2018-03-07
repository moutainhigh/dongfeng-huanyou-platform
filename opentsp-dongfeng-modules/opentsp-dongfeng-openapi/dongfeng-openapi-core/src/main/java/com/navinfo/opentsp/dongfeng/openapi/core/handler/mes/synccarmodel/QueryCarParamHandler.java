package com.navinfo.opentsp.dongfeng.openapi.core.handler.mes.synccarmodel;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.param.GetCarParamCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarDataBusinessService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class QueryCarParamHandler
		extends NotValidateTokenAbstractHandler<GetCarParamCommand, HttpCommandResultWithData> {
	private static Log logger = LogFactory.getLog(QueryCarParamHandler.class);
	@Autowired
	private ICarDataBusinessService carDataBusinessService;

	public QueryCarParamHandler() {
		super(GetCarParamCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarParamHandler(Class<GetCarParamCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(GetCarParamCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = carDataBusinessService.queryCarParam(command);
		} catch (Exception e) {
			logger.error("#QueryCarParamHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
