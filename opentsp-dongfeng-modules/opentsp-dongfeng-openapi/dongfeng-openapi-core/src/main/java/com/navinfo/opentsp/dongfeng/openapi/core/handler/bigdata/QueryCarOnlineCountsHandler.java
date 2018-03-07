package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarOnlineCountsCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarOnlineCountsService;
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
public class QueryCarOnlineCountsHandler
		extends ValidateUsingUserDefinedMethodAbstractHandler<QueryCarOnlineCountsCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryCarOnlineCountsHandler.class);

	@Autowired
	private ICarOnlineCountsService service;

	public QueryCarOnlineCountsHandler() {
		super(QueryCarOnlineCountsCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarOnlineCountsHandler(Class<QueryCarOnlineCountsCommand> commandType,
                                          Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData openApiHandle(QueryCarOnlineCountsCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = service.queryCarOnlineCounts(command);
		} catch (Exception e) {
			logger.error("#QueryCarOnlineInfosHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}


}
