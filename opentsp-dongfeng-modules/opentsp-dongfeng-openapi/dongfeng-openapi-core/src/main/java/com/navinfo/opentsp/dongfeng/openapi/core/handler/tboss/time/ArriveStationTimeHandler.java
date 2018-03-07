package com.navinfo.opentsp.dongfeng.openapi.core.handler.tboss.time;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.time.ArriveStationTimeCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ISecondForAppService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description 进站等待查询
 * @author xltianc.zh 
 */
@Component
@SuppressWarnings("rawtypes")
public class ArriveStationTimeHandler extends NotValidateTokenAbstractHandler<ArriveStationTimeCommand, HttpCommandResultWithData> {
	
	private static Log logger = LogFactory.getLog(ArriveStationTimeHandler.class);
	
	@Autowired
	private ISecondForAppService secondForAppService;

	public ArriveStationTimeHandler() {
		super(ArriveStationTimeCommand.class, HttpCommandResultWithData.class);
	}

	protected ArriveStationTimeHandler(Class<ArriveStationTimeCommand> commandType,
			Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}
	
	@Override
	protected HttpCommandResultWithData businessHandle(ArriveStationTimeCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		try {
			result = secondForAppService.selectSecondInfoByParam(command);
		} catch (Exception e) {
			logger.error("#ArriveStationTimeHandler#businessHandle#error..." , e);
			result.fillResult(ReturnCode.SERVER_ERROR);
			
		}
		return result;
	}

}
