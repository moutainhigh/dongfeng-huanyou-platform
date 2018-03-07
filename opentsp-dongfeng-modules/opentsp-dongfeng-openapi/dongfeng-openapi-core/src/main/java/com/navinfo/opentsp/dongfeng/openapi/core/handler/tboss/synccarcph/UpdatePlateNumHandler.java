package com.navinfo.opentsp.dongfeng.openapi.core.handler.tboss.synccarcph;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.car.UpdatePlateNumCommand;
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
public class UpdatePlateNumHandler extends NotValidateTokenAbstractHandler<UpdatePlateNumCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(UpdatePlateNumHandler.class);

	@Autowired
	private ICarDataBusinessService carDataBusinessService;

	public UpdatePlateNumHandler() {
		super(UpdatePlateNumCommand.class, HttpCommandResultWithData.class);
	}

	protected UpdatePlateNumHandler(Class<UpdatePlateNumCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(UpdatePlateNumCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = carDataBusinessService.updatePlateNum(command);
		} catch (Exception e) {
			logger.error("#UpdatePlateNumHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
