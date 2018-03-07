package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryFaultSummaryCommand;

/**
 * 故障汇总报表
 * 
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/27
 */
public interface IFaultSummaryService {
	/**
	 * 故障汇总报表
	 *
	 * @param command
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HttpCommandResultWithData queryFaultSummary(QueryFaultSummaryCommand command, boolean isExport);
}
