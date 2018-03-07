package com.navinfo.opentsp.dongfeng.openapi.core.handler.app;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ScanResultCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IF9ScanBusinessService;
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
public class F9ScanResultHandler extends NotValidateTokenAbstractHandler<F9ScanResultCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(F9ScanResultHandler.class);

	@Autowired
	private IF9ScanBusinessService scanBusinessService;

	public F9ScanResultHandler() {
		super(F9ScanResultCommand.class, HttpCommandResultWithData.class);
	}

	protected F9ScanResultHandler(Class<F9ScanResultCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(F9ScanResultCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = scanBusinessService.scanResult(command);
		} catch (Exception e) {
			logger.error("#F9ScanResultHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
