package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.time.ArriveStationTimeCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.time.StayTimeInAeraCommand;

@SuppressWarnings("rawtypes")
public interface ISecondForAppService {

	/**
	 * 进站等待查询
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData selectSecondInfoByParam(ArriveStationTimeCommand command);

	
	/**
	 * 经销商网点位置上报接口
	 * 
	 * @param command
	 * @return
	 */
	public HttpCommandResultWithData selectStayTimeInAera(StayTimeInAeraCommand command);

}
