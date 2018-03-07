package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryOnlineCarDistributionCommand;
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
public class QueryOnlineCarDistributionHandler extends
		ValidateUsingUserDefinedMethodAbstractHandler<QueryOnlineCarDistributionCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryOnlineCarDistributionHandler.class);

	@Autowired
	private IOnlineCarDistributionService service;

	public QueryOnlineCarDistributionHandler() {
		super(QueryOnlineCarDistributionCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryOnlineCarDistributionHandler(Class<QueryOnlineCarDistributionCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData openApiHandle(QueryOnlineCarDistributionCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = service.onlineCarDistribution(command);
		} catch (Exception e) {
			logger.error("#QueryOnlineCarDistributionHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
