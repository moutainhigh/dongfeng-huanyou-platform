package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarTotalByTypeCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarTotalThatHaveAAKTimeCommand;

@SuppressWarnings("rawtypes")
public interface ICarTotalByBusinessService {

	/**
	 * 根据AAK时间获取车辆总数
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData queryCarTotalThatHaveAAKTime(QueryCarTotalThatHaveAAKTimeCommand command);

	/**
	 * 根据车辆类型获取车辆总数
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData queryCarTotalByType(QueryCarTotalByTypeCommand command);
}
