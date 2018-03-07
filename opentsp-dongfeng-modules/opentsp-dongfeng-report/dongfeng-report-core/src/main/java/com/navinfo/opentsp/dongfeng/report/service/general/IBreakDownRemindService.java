package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryBreakDownRemindCommand;

public interface IBreakDownRemindService {
	/**
	 * 故障详细列表查询
	 * 
	 * @param command
	 * @param b
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public HttpCommandResultWithData queryBreakDownRemind(QueryBreakDownRemindCommand command, boolean b);
}
