package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ReportInfoCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ScanResultCommand;

/**
 * 
 * @author xltianc.zh
 *
 */
@SuppressWarnings("rawtypes")
public interface IF9ScanBusinessService {
	
	/**
	 * F9扫码结果页
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData scanResult(F9ScanResultCommand command) throws Exception;

	
	/**
	 * F9最新上报信息
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData reportInfo(F9ReportInfoCommand command) throws Exception;

}
