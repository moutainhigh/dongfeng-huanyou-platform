package com.navinfo.opentsp.dongfeng.openapi.core.handler.mes.monitor;

import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.monitor.MesMonitorCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IMesMonitorService;
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
public class MesMonitorHandler extends NotValidateTokenAbstractHandler<MesMonitorCommand, HttpCommandResultWithData> {

	private static Log logger = LogFactory.getLog(MesMonitorHandler.class);

	@Autowired
	private IMesMonitorService mesMonitorService;

	public MesMonitorHandler() {
		super(MesMonitorCommand.class, HttpCommandResultWithData.class);
	}

	protected MesMonitorHandler(Class<MesMonitorCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	@Override
	protected HttpCommandResultWithData businessHandle(MesMonitorCommand command) {
		HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
		try {
			resultMap = mesMonitorService.monitor(command);
		} catch (Exception e) {
			logger.error("#MesMonitorHandler#businessHandle#...", e);
			resultMap.fillResult(ReturnCode.SERVER_ERROR);
		}
		return resultMap;
	}

}
