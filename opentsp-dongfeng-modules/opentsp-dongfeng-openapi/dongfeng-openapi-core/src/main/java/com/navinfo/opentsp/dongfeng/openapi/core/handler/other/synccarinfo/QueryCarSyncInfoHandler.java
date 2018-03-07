package com.navinfo.opentsp.dongfeng.openapi.core.handler.other.synccarinfo;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.day.CarSyncInfoByTimeCommand;
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
public class QueryCarSyncInfoHandler
		extends NotValidateTokenAbstractHandler<CarSyncInfoByTimeCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(QueryCarSyncInfoHandler.class);
	
	@Autowired
	private ICarDataBusinessService carDataBusinessService;

	public QueryCarSyncInfoHandler() {
		super(CarSyncInfoByTimeCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryCarSyncInfoHandler(Class<CarSyncInfoByTimeCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(CarSyncInfoByTimeCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = carDataBusinessService.selectCarSyncInfoByNum(command);
		} catch (Exception e) {
			logger.error("#QueryCarSyncInfoHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
