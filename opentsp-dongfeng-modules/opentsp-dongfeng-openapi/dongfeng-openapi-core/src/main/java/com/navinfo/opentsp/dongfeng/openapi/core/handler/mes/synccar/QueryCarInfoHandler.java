package com.navinfo.opentsp.dongfeng.openapi.core.handler.mes.synccar;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.day.GetCarInfoCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarDataBusinessService;
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
public class QueryCarInfoHandler extends NotValidateTokenAbstractHandler<GetCarInfoCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryCarInfoHandler.class);

	@Autowired
	private ICarDataBusinessService carDataBusinessService;

	public QueryCarInfoHandler() {
		super(GetCarInfoCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarInfoHandler(Class<GetCarInfoCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(GetCarInfoCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = carDataBusinessService.queryAddOrUpdateCarInfo(command);
		} catch (Exception e) {
			logger.error("#QueryCarInfoHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
