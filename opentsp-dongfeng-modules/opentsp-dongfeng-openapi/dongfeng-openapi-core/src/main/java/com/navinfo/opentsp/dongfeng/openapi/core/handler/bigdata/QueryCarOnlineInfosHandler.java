package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarOnlineInfosCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IOnlineCarDistributionService;
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
public class QueryCarOnlineInfosHandler
		extends ValidateUsingUserDefinedMethodAbstractHandler<QueryCarOnlineInfosCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryCarOnlineInfosHandler.class);
	
	@Autowired
	private IOnlineCarDistributionService service;

	public QueryCarOnlineInfosHandler() {
		super(QueryCarOnlineInfosCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarOnlineInfosHandler(Class<QueryCarOnlineInfosCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData openApiHandle(QueryCarOnlineInfosCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = service.queryCarOnlineInfos(command);
		} catch (Exception e) {
			logger.error("#QueryCarOnlineInfosHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}


}
