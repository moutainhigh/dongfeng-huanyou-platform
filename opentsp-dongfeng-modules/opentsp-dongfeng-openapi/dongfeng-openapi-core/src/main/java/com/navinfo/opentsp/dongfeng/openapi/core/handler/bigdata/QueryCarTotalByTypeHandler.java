package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarTotalByTypeCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarTotalByBusinessService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author xltianc.zh
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class QueryCarTotalByTypeHandler
		extends ValidateUsingUserDefinedMethodAbstractHandler<QueryCarTotalByTypeCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryCarTotalByTypeHandler.class);
	
	@Autowired
	private ICarTotalByBusinessService byBusinessService;

	public QueryCarTotalByTypeHandler() {
		super(QueryCarTotalByTypeCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarTotalByTypeHandler(Class<QueryCarTotalByTypeCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData openApiHandle(QueryCarTotalByTypeCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = byBusinessService.queryCarTotalByType(command);
		} catch (Exception e) {
			logger.error("#QueryCarTotalByTypeHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}


}
