package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarPolymerizeCommand;

/**
 * 
 * @author xltianc-zh
 *
 */
public interface IQueryCarPolymerizeService {
	
	
	/**
	 * 获取搜索车辆的定位信息
	 * @param command
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HttpCommandResultWithData queryCarPolymerize(QueryCarPolymerizeCommand command);

}
