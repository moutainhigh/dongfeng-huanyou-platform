package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.monitor.MesMonitorCommand;

/**
 * 
 * @author xltianc.zh
 *
 */
public interface IMesMonitorService {
	/**
	 * 监控
	 * @param command
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	HttpCommandResultWithData monitor(MesMonitorCommand command);

}
