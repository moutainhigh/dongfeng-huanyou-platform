package com.navinfo.opentsp.dongfeng.openapi.core.handler.app;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ReportInfoCommand;
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
public class F9ReportInfoHandler extends NotValidateTokenAbstractHandler<F9ReportInfoCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(F9ReportInfoHandler.class);

	@Autowired
	private IF9ScanBusinessService scanBusinessService;

	public F9ReportInfoHandler() {
		super(F9ReportInfoCommand.class, HttpCommandResultWithData.class);
	}

	protected F9ReportInfoHandler(Class<F9ReportInfoCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(F9ReportInfoCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = scanBusinessService.reportInfo(command);
		} catch (Exception e) {
			logger.error("#F9ReportInfoHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
