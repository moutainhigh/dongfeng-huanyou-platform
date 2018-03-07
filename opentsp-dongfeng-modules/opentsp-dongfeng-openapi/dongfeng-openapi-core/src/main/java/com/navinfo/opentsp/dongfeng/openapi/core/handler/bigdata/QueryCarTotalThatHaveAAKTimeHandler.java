package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarTotalThatHaveAAKTimeCommand;
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
public class QueryCarTotalThatHaveAAKTimeHandler
		extends ValidateUsingUserDefinedMethodAbstractHandler<QueryCarTotalThatHaveAAKTimeCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryCarTotalThatHaveAAKTimeHandler.class);
	
	@Autowired
	private ICarTotalByBusinessService byBusinessService;

	public QueryCarTotalThatHaveAAKTimeHandler() {
		super(QueryCarTotalThatHaveAAKTimeCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarTotalThatHaveAAKTimeHandler(Class<QueryCarTotalThatHaveAAKTimeCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData openApiHandle(QueryCarTotalThatHaveAAKTimeCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = byBusinessService.queryCarTotalThatHaveAAKTime(command);
		} catch (Exception e) {
			logger.error("#QueryCarTotalThatHaveAAKTimeHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}


}
