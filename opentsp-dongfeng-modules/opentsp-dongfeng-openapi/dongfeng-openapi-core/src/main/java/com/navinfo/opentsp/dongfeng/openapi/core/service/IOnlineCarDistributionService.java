package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarOnlineInfosCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryOnlineCarDistributionCommand;

@SuppressWarnings("rawtypes")
public interface IOnlineCarDistributionService {

	/**
	 * 1.1.3 在线车辆分布排名 top5
	 * 
	 * @param command
	 * @return
	 */
	
	HttpCommandResultWithData onlineCarDistribution(QueryOnlineCarDistributionCommand command);

	/**
	 * 1.1.4 车辆24小时在线情况
	 * 
	 * @param command
	 * @return
	 */
	HttpCommandResultWithData queryCarOnlineInfos(QueryCarOnlineInfosCommand command);
}
